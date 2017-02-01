package com.memorycat.app.txtreader.book;

/**
 * Created by xie on 2017/2/1.
 */

public interface Reading {
    boolean nextPage();
    boolean previousPage();
    String getReadingContent();
    void setPageSize(int size);
    int getPageSize();

}
