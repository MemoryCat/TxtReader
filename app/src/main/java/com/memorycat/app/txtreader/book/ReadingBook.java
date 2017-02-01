package com.memorycat.app.txtreader.book;

import java.io.Serializable;

/**
 * Created by xie on 2017/2/1.
 */

public class ReadingBook implements Serializable, Reading {
    private static final String TAG = "ReadingBook";
    private Long id;
    private Book book;
    private int pageSize = 100;
    private String readingContent=new String();

    public ReadingBook(Book book) {
        this.book=book;
        if(book==null){
            throw new NullPointerException("必须传入Book且能为null");
        }


        int positionPointer = this.book.getPositionPointer();
        if(positionPointer<this.book.getBookContent().length()){
            this.readingContent = this.book.getBookContent().substring(positionPointer, this.pageSize);
        }else{
            previousPage();
        }
    }

    @Override
    public boolean nextPage() {

        int contentLength = this.book.getBookContent().length();
        int positionPointer = this.book.getPositionPointer();
        int newPositionPointer = positionPointer + this.pageSize;
        if (newPositionPointer >= contentLength) {
            return false;
        }
        this.book.setPositionPointer(newPositionPointer);
//        Log.d(TAG, "nextPage: "+"abcdefghijkl".substring(2,4));
        this.setReadingContent(this.book.getBookContent().substring(newPositionPointer, newPositionPointer+this.pageSize));
        //TODO 保存阅读进度指针
        return true;
    }

    @Override
    public boolean previousPage() {
        int contentLength = this.book.getBookContent().length();
        int positionPointer = this.book.getPositionPointer();
        int newPositionPointer = positionPointer - this.pageSize;
        if (newPositionPointer < 0) {
            return false;
        }

        this.book.setPositionPointer(newPositionPointer);
        this.setReadingContent(this.book.getBookContent().substring(newPositionPointer, newPositionPointer+this.pageSize));
        //TODO 保存阅读进度指针
        return true;
    }

    @Override
    public String getReadingContent() {
        return this.readingContent;
    }

    public void setReadingContent(String readingContent) {
        this.readingContent = readingContent;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }


    //////////getter & setter

    @Override
    public void setPageSize(int size) {
        this.pageSize = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
