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
    private BookSQLHelper bookSQLHelper;

    public ReadingBook(Book book,BookSQLHelper bookSQLHelper) {
        this.book=book;
        if(book==null){
            throw new NullPointerException("必须传入Book且能为null");
        }
        this.bookSQLHelper=bookSQLHelper;


        int positionPointer = this.book.getPositionPointer();
        int endIndex=positionPointer+pageSize>this.book.getBookContent().length()?this.book.getBookContent().length():positionPointer+this.pageSize;
        if(positionPointer<this.book.getBookContent().length()){
            this.readingContent = this.book.getBookContent().substring(positionPointer,endIndex);
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
        int endIndex=newPositionPointer+pageSize>this.book.getBookContent().length()?this.book.getBookContent().length():newPositionPointer+this.pageSize;
        this.setReadingContent(this.book.getBookContent().substring(newPositionPointer, endIndex));
        //保存阅读进度指针
        if(this.bookSQLHelper!=null){
            this.bookSQLHelper.updateReadBookProgress(this.book.getId(),newPositionPointer);
        }

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
        int endIndex=newPositionPointer<=0?0:newPositionPointer+this.pageSize;
        this.setReadingContent(this.book.getBookContent().substring(newPositionPointer,endIndex));
        //保存阅读进度指针
        if(this.bookSQLHelper!=null){
            this.bookSQLHelper.updateReadBookProgress(this.book.getId(),newPositionPointer);
        }
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
