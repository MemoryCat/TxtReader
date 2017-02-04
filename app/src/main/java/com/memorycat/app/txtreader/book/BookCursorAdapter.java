package com.memorycat.app.txtreader.book;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.file.FileUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by xie on 2017/1/28.
 */

public class BookCursorAdapter extends CursorAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);

    }

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LayoutInflater layoutInflater;

    public BookCursorAdapter(LayoutInflater layoutInflater, Context context, Cursor c) {
        super(context, c, true);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.fragment_book_item, parent, false);
        setBookItemView(view,cursor);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setBookItemView(view,cursor);
    }


    private void setBookItemView(View view, Cursor cursor) {
        Book book = BookSQLHelper.getBookFromCursor(cursor);
        view.setTag(book);
        TextView textViewBookName = (TextView) view.findViewById(R.id.fragmentbook_textViewBookName);
        textViewBookName.setText(book.getBookName());
        TextView textViewLastReadDate = (TextView) view.findViewById(R.id.fragmentbook_textViewLastReadDate);
        textViewLastReadDate.setText("最后阅读:" + simpleDateFormat.format(book.getLastReadDate()));
        TextView textViewPosition = (TextView) view.findViewById(R.id.fragmentbook_textViewPosition);
        File file = new File(book.getFilePath());
        String s = FileUtil.loadFileToString(file);
        float position = 0;
        String p="0";
        try {

            position = ( (float)book.getPositionPointer() / s.length()) * 100;
            DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            p=decimalFormat.format(position);//format 返回的是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewPosition.setText("   进度:" + p+ "%   字数:"+book.getPositionPointer()+"/"+ s.length());
    }
}
