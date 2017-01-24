package com.memorycat.app.txtreader.file;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.memorycat.app.txtreader.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xie on 2016/11/23.
 */

public class FileAdapter extends BaseAdapter {

    private final File[] files;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Context context;
    private File currentFile;

    public FileAdapter(Context context, File currentFile) {
        this.currentFile = currentFile;
        this.context = context;
        files = currentFile.listFiles();
    }

    @Override
    public int getCount() {
        return files == null ? 0 : files.length;
    }

    @Override
    public Object getItem(int i) {
        return files == null ? null : files[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_file_item, null);
            view.setTag(view);
        } else {
            view = (View) view.getTag();
        }

        TextView textViewFileName = (TextView) view.findViewById(R.id.layoutFI_textViewFileName);
        TextView textViewFileSize = (TextView) view.findViewById(R.id.layoutFI_textViewFileSize);
        ImageView imageViewFileType = (ImageView) view.findViewById(R.id.layoutFI_imageViewFileType);

        textViewFileName.setText(files[i].getName());
        textViewFileName.setTag(files[i]);

        if (!files[i].isDirectory()) {
            imageViewFileType.setImageResource(R.drawable.file);
            imageViewFileType.setTag("file");

            textViewFileSize.setText(getReadableFileSize(files[i]) + "  最后修改:" + simpleDateFormat.format(new Date(files[i].lastModified())));
        } else {
            imageViewFileType.setImageResource(R.drawable.directory);
            imageViewFileType.setTag("directory");
            String[] list = files[i].list();
            String subDirectoryFileSize = list == null ? "" : list.length + "项";
            textViewFileSize.setText(subDirectoryFileSize);
        }


        return view;
    }

    private String getReadableFileSize(File file) {
        long length = file.length();
        String ret = "" + length + "b";
        if (length > 1024) {
            ret = "" + (length / 1024) + "kb";
        }
        if (length > 1024000) {
            ret = "" + (length / 1024000) + "mb";
        }

        return ret;
    }
}
