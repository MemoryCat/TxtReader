package com.memorycat.app.txtreader.book;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.reading.ReadingActivity;

public class BookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView booksListView;
    private BookCursorAdapter bookCursorAdapter;
    private BookSQLHelper bookSQLHelper;
    private Button btnAddLocalBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        this.booksListView = (ListView) findViewById(R.id.ab_lv_books);
        this.btnAddLocalBook = (Button) findViewById(R.id.ab_btn_addLocalBook);
        this.btnAddLocalBook.setOnClickListener(this);

        this.bookSQLHelper = new BookSQLHelper(this);
        Cursor cursor = bookSQLHelper.getReadableDatabase()
                .rawQuery("select * from " + BookSQLHelper.TABLE_NAME, null);
        if (cursor != null) {
            bookCursorAdapter = new BookCursorAdapter(super.getLayoutInflater(), this, cursor);
            this.booksListView.setAdapter(bookCursorAdapter);
        }

        this.booksListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ab_btn_addLocalBook:
                FileDialogFragment fileDialogFragment = new FileDialogFragment();
                fileDialogFragment.show(this.getFragmentManager(), "fileDialogFragment");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Book book = (Book) view.getTag();
//        book.setBookContent(FileUtil.loadFileToString(new File(book.getFilePath())));
        Intent intent = new Intent(this, ReadingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
