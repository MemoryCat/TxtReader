package com.memorycat.app.txtreader.file;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;

import java.io.File;

/**
 * Created by xie on 2017/1/24.
 */

public class FileDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "FileDialogFragment";

    private ListView listView;
    private Button buttonHome;
    private Button buttonBack;
    private File currentFile = null;
    private EditText currentPath;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = super.getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_file, null);
        buttonHome = (Button) view.findViewById(R.id.fragmentfile_btnGoHome);
        buttonBack = (Button) view.findViewById(R.id.fragmentfile_btnGoBack);
        currentPath = (EditText) view.findViewById(R.id.fragmentfile_currentPath);

        buttonHome.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        changeCurrnetFile(Environment.getExternalStorageDirectory());
        this.listView = (ListView) view.findViewById(R.id.fragmentfile_listViewFileList);
        this.listView.setAdapter(new FileAdapter(super.getActivity(), this.currentFile));
        this.listView.setOnItemClickListener(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("请选择TXT文件");
        builder.setView(view);
        return builder.create();
    }

    private void changeCurrnetFile(File file) {
        this.currentFile = file;
        this.currentPath.setText(file.getAbsolutePath());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick() called with: view = [" + view + "]");
        if (view.getId() == R.id.fragmentfile_btnGoHome) {

            changeCurrnetFile(Environment.getExternalStorageDirectory());
            this.listView.setAdapter(new FileAdapter(super.getActivity(), this.currentFile));
        } else if (view.getId() == R.id.fragmentfile_btnGoBack) {
            File parentFile = this.currentFile.getParentFile();
            if (parentFile != null) {
                changeCurrnetFile(parentFile);
                this.listView.setAdapter(new FileAdapter(super.getActivity(), this.currentFile));
            } else {
                Toast.makeText(super.getActivity(), "已经是最顶层目录了，没有上一级目录了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClick() called with: adapterView = [" + adapterView + "], view = [" + view + "], i = [" + i + "], l = [" + l + "]");

        TextView textViewFileName = (TextView) view.findViewById(R.id.layoutFI_textViewFileName);
        TextView textViewFileSize = (TextView) view.findViewById(R.id.layoutFI_textViewFileSize);
        ImageView imageViewFileType = (ImageView) view.findViewById(R.id.layoutFI_imageViewFileType);


        File file = (File) textViewFileName.getTag();
        if ("file".equals(imageViewFileType.getTag())) {
//            BookshelfEntity bookshelfEntity = new BookshelfEntity();
//            bookshelfEntity.setFilePath(file.getAbsolutePath());
//            bookshelfEntity.setBookName(file.getName());
//            bookshelfEntity.setLastReadPosition(0L);
//
//            BookshelfSqliteHelper bookshelfSqliteHelper = new BookshelfSqliteHelper(super.getContext());
//            bookshelfSqliteHelper.add(bookshelfEntity);
//            bookshelfSqliteHelper.close();


            Toast.makeText(super.getActivity(), "已添加到书架：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            String s = FileUtil.loadFileToString(file);
            Log.d(TAG, "onItemClick: "+s);

        } else {

            changeCurrnetFile(file);
            this.listView.setAdapter(new FileAdapter(super.getActivity(), this.currentFile));
        }
    }
}
