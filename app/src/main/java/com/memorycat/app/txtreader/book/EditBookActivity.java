package com.memorycat.app.txtreader.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.file.FileUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditBookActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText bookName;
    private EditText author;
    private EditText readProgress;
    private EditText addDate;
    private EditText lastReadDate;
    private EditText filePath;
    private Book book;
    private Button btnSave;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private TextView fileLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        super.setTitle("修改书本信息");
        ActionBar actionBar = super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bookName = (EditText) findViewById(R.id.aeb_edittext_bookName);
        author = (EditText) findViewById(R.id.aeb_edittext_author);
        readProgress = (EditText) findViewById(R.id.aeb_edittext_readProgress);
        filePath = (EditText) findViewById(R.id.aeb_edittext_filePath);
        addDate = (EditText) findViewById(R.id.aeb_edittext_addDate);
        lastReadDate = (EditText) findViewById(R.id.aeb_edittext_lastReadDate);
        btnSave = (Button) findViewById(R.id.aeb_btn_save);

        fileLength = (TextView) findViewById(R.id.aeb_textview_fileLength);

        Intent intent = super.getIntent();
        this.book = (Book) intent.getSerializableExtra("book");

        this.bookName.setText(this.book.getBookName());
        this.author.setText(this.book.getAuthor());
        this.readProgress.setText("" + this.book.getPositionPointer());
        this.filePath.setText(this.book.getFilePath());
        this.addDate.setText(simpleDateFormat.format(this.book.getAddDate()));
        this.lastReadDate.setText(simpleDateFormat.format(this.book.getLastReadDate()));

        this.fileLength.setText(""+FileUtil.loadFileToString(new File(this.book.getFilePath())).length());
        this.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aeb_btn_save:
//             this.bookName.setText(this.book.getBookName());
//             this.author.setText(this.book.getAuthor());
//             this.readProgress.setText(""+this.book.getPositionPointer());
//             this.filePath.setText(this.book.getFilePath());
//             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//             this.addDate.setText(simpleDateFormat.format(this.book.getAddDate()));
//             this.lastReadDate.setText(simpleDateFormat.format(this.book.getLastReadDate()));
                this.book.setBookName(this.bookName.getText().toString());
                this.book.setAuthor(this.author.getText().toString());
                File file = new File(this.filePath.getText().toString());
                if (file.exists() == false) {
                    Toast.makeText(this, "文件不存在:" + this.book.getFilePath(), Toast.LENGTH_SHORT).show();
                    return;
                }
                this.book.setFilePath(this.filePath.getText().toString());
                int maxLength = FileUtil.loadFileToString(file).length();
                this.book.setPositionPointer(Integer.parseInt(this.readProgress.getText().toString()) > maxLength ?
                        maxLength : Integer.parseInt(this.readProgress.getText().toString()));


                try {
                    this.book.setAddDate(simpleDateFormat.parse(this.addDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    this.book.setLastReadDate(simpleDateFormat.parse(this.lastReadDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                BookSQLHelper bookSQLHelper = new BookSQLHelper(this);
                bookSQLHelper.updateBook(this.book);
                super.setResult(BookActivity.REQUEST_CODE_ADDBOOK);
                super.finish();
                break;
        }
    }
}
