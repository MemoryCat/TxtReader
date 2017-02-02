package com.memorycat.app.txtreader.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.file.SelectFileActivity;
import com.memorycat.app.txtreader.reading.Reading2Activity;

public class BookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final int REQUEST_CODE_ADDBOOK = 10001;
    private static final String TAG = "BookActivity";
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
        this.setBookList();
        this.booksListView.setOnItemClickListener(this);
    }

    private void setBookList() {
        bookCursorAdapter = new BookCursorAdapter(super.getLayoutInflater(), this, this.bookSQLHelper.getAllBookCursor());
        this.booksListView.setAdapter(bookCursorAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.bookCursorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (requestCode) {
            case REQUEST_CODE_ADDBOOK:
                this.setBookList();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ab_btn_addLocalBook:
//                FileDialogFragment fileDialogFragment = new FileDialogFragment();
//                fileDialogFragment.show(this.getFragmentManager(), "fileDialogFragment");
                Intent intent = new Intent(this, SelectFileActivity.class);
                super.startActivityForResult(intent, REQUEST_CODE_ADDBOOK);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
        Book book = (Book) view.getTag();
        Log.d(TAG, "onItemClick: " + book);
//        book.setBookContent(FileUtil.loadFileToString(new File(book.getFilePath())));
        Intent intent = new Intent(this, Reading2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
