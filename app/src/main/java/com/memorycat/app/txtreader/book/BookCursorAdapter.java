package com.memorycat.app.txtreader.book;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;

import com.memorycat.app.txtreader.R;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by xie on 2017/1/28.
 */

public class BookCursorAdapter extends CursorAdapter {


    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LayoutInflater layoutInflater;

    public BookCursorAdapter(LayoutInflater layoutInflater, Context context, Cursor c) {
        super(context, c, true);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.fragment_book_item, parent, false);
        Book book = BookSQLHelper.getBookFromCursor(cursor);
        view.setTag(book);
        setBookItemView(view, book);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setBookItemView(view, BookSQLHelper.getBookFromCursor(cursor));
    }


    private void setBookItemView(View view, Book book) {
        TextView textViewBookName = (TextView) view.findViewById(R.id.fragmentbook_textViewBookName);
        textViewBookName.setText(book.getBookName());
        TextView textViewLastReadDate = (TextView) view.findViewById(R.id.fragmentbook_textViewLastReadDate);
        textViewLastReadDate.setText("最后阅读:" + simpleDateFormat.format(book.getLastReadDate()));
        TextView textViewPosition = (TextView) view.findViewById(R.id.fragmentbook_textViewPosition);
        File file = new File(book.getFilePath());
        long position = 0L;
        try {
            position = (book.getPositionPointer() / file.length() * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewPosition.setText("大小:"+file.length()+"   进度:" + position + "%");
    }
}
