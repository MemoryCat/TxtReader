package com.memorycat.app.txtreader.book;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xie on 2017/1/25.
 */

public class Book implements Serializable {
    private int id;
    private String bookName;
    private String bookContent;
    private String author;
    private Date addDate;
    private int positionPointer;//阅读指针，阅读到哪个位置了？
    private String filePath;
    private Date lastReadDate;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookContent='" + bookContent + '\'' +
                ", author='" + author + '\'' +
                ", addDate=" + addDate +
                ", positionPointer=" + positionPointer +
                ", filePath='" + filePath + '\'' +
                ", lastReadDate=" + lastReadDate +
                '}';
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getLastReadDate() {
        return lastReadDate;
    }

    public void setLastReadDate(Date lastReadDate) {
        this.lastReadDate = lastReadDate;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getPositionPointer() {
        return positionPointer;
    }

    public void setPositionPointer(int positionPointer) {
        this.positionPointer = positionPointer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (positionPointer != book.positionPointer) return false;
        if (bookName != null ? !bookName.equals(book.bookName) : book.bookName != null)
            return false;
        if (bookContent != null ? !bookContent.equals(book.bookContent) : book.bookContent != null)
            return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (addDate != null ? !addDate.equals(book.addDate) : book.addDate != null) return false;
        if (filePath != null ? !filePath.equals(book.filePath) : book.filePath != null)
            return false;
        return lastReadDate != null ? lastReadDate.equals(book.lastReadDate) : book.lastReadDate == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
        result = 31 * result + (bookContent != null ? bookContent.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + positionPointer;
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (lastReadDate != null ? lastReadDate.hashCode() : 0);
        return result;
    }
}
