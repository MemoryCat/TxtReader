package com.memorycat.app.txtreader.file;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.book.BookActivity;
import com.memorycat.app.txtreader.book.BookSQLHelper;

import java.io.File;
import java.util.Date;

public class SelectFileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "SelectFileActivity";
    private ListView listView;
    private Button buttonHome;
    private Button buttonBack;
    private File currentFile = null;
    private TextView currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);


        buttonHome = (Button) super.findViewById(R.id.asf_btnGoHome);
        buttonBack = (Button) super.findViewById(R.id.asf_btnGoBack);
        currentPath = (TextView) super.findViewById(R.id.asf_currentPath);

        buttonHome.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        changeCurrnetFile(Environment.getExternalStorageDirectory());
        this.listView = (ListView) super.findViewById(R.id.asf_listViewFileList);
        this.listView.setAdapter(new FileAdapter(this, this.currentFile));
        this.listView.setOnItemClickListener(this);

        super.setTitle("请选择txt文件添加到书库：");
        ActionBar actionBar = super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void changeCurrnetFile(File file) {
        this.currentFile = file;
        this.currentPath.setText(file.getAbsolutePath());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick() called with: view = [" + view + "]");
        if (view.getId() == R.id.asf_btnGoHome) {

            changeCurrnetFile(Environment.getExternalStorageDirectory());
            this.listView.setAdapter(new FileAdapter(this, this.currentFile));
        } else if (view.getId() == R.id.asf_btnGoBack) {
            File parentFile = this.currentFile.getParentFile();
            if (parentFile != null) {
                changeCurrnetFile(parentFile);
                this.listView.setAdapter(new FileAdapter(this, this.currentFile));
            } else {
                Toast.makeText(this, "已经是最顶层目录了，没有上一级目录了", Toast.LENGTH_SHORT).show();
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

            Book book = new Book();
            book.setFilePath(file.getAbsolutePath());
            book.setAddDate(new Date());
            book.setBookName(file.getName());
            book.setLastReadDate(book.getAddDate());
//            book.setBookContent(FileUtil.loadFileToString(file));
            book.setBookContent("");
            BookSQLHelper bookSQLHelper = new BookSQLHelper(this);
            bookSQLHelper.add(book);

            Toast.makeText(this, "已添加到书架：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            String s = FileUtil.loadFileToString(file);
            Log.d(TAG, "onItemClick: " + s);
            super.setResult(BookActivity.REQUEST_CODE_ADDBOOK);
            super.finish();

        } else {

            changeCurrnetFile(file);
            this.listView.setAdapter(new FileAdapter(this, this.currentFile));
        }
    }
}
