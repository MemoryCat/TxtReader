package com.memorycat.app.txtreader.reading;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.book.BookSQLHelper;
import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.file.FileUtil;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

import java.io.File;
import java.util.List;

/**
 * Created by xie on 2017/1/24.
 */

public class HtmlUIInteraction {

    private Activity activity;
    private TextSpeaker textSpeaker;
    private Book book;

    public HtmlUIInteraction(Book book, Activity activity, TextSpeaker textSpeaker) {
        this.activity = activity;
        this.textSpeaker = textSpeaker;
        this.book = book;
    }

    @JavascriptInterface
    public String getBooks() {
        BookSQLHelper bookSQLHelper = new BookSQLHelper(activity);
        List<Book> books = bookSQLHelper.getAllBooks();
        try {
            return new ObjectMapper().writeValueAsString(books).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @JavascriptInterface
    public void addLocalBook() {
        FileDialogFragment fileDialogFragment = new FileDialogFragment();
        fileDialogFragment.show(this.activity.getFragmentManager(), "fileDialogFragment");
    }

    @JavascriptInterface
    public void playText(String text) {
        this.textSpeaker.play(text);
    }

    @JavascriptInterface
    public String getBookJson() {
        try {
            book.setBookContent(FileUtil.loadFileToString(new File(book.getFilePath())));
            return new ObjectMapper().writeValueAsString(this.book).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
