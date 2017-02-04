package com.memorycat.app.txtreader.book;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.file.SelectFileActivity;
import com.memorycat.app.txtreader.reading.Reading2Activity;

import java.io.File;

public class BookActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public static final int REQUEST_CODE_ADDBOOK = 10001;
    private static final String TAG = "BookActivity";
    private ListView booksListView;
    private BookCursorAdapter bookCursorAdapter;
    private BookSQLHelper bookSQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        this.booksListView = (ListView) findViewById(R.id.ab_lv_books);


        this.bookSQLHelper = new BookSQLHelper(this);
        this.setBookList();
        this.booksListView.setOnItemClickListener(this);
        this.booksListView.setOnItemLongClickListener(this);
    }

    private void setBookList() {
        bookCursorAdapter = new BookCursorAdapter(super.getLayoutInflater(), this, this.bookSQLHelper.getAllBookCursor());
        this.booksListView.setAdapter(bookCursorAdapter);

        if (this.booksListView.getAdapter().getCount() <= 0) {
            Toast.makeText(this, "书库还是空的，请在右上角菜单添加一本吧！", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (requestCode) {
            case REQUEST_CODE_ADDBOOK:
                break;
        }
        this.setBookList();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
        this.startReadingActivity(view);
    }

    /**
     * @param view listView的Item
     */
    private void startReadingActivity(View view) {
        Book book = (Book) view.getTag();
        Log.d(TAG, "onItemClick: " + book);
        File file = new File(book.getFilePath());
        if (file.exists() == false) {
            Toast.makeText(this, "无法阅读：文件不存在！" + book.getFilePath(), Toast.LENGTH_SHORT).show();
            return;
        }

//        book.setBookContent(FileUtil.loadFileToString(new File(book.getFilePath())));
        Intent intent = new Intent(this, Reading2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_add_local_book:
                Intent intent = new Intent(this, SelectFileActivity.class);
                super.startActivityForResult(intent, REQUEST_CODE_ADDBOOK);
                return true;
            case R.id.menu_item_add_net_book:
                Toast.makeText(this, "网络书库目前还未开发，马上就会更新该功能！\n\n敬请关注http://www.memorycat.com/app/txtreader", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
        final View _view = view;
        view.startActionMode(new android.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu_book_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                Log.d(TAG, "onActionItemClicked() called with: mode = [" + mode + "], item = [" + item + "]");
                final Book book = ((Book) _view.getTag());
                switch (item.getItemId()) {
                    case R.id.context_menu_item_read:
                        BookActivity.this.startReadingActivity(_view);
                        break;
                    case R.id.context_menu_item_delete:
                        new AlertDialog.Builder(BookActivity.this)
                                .setTitle("删除").setMessage("确定要从书库删除：" + book.getBookName())
                                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        BookActivity.this.bookSQLHelper.deleteBookById(book.getId());
                                        BookActivity.this.setBookList();
                                    }
                                }).setPositiveButton("否", null).create().show();
                        break;
                    case R.id.context_menu_item_edit:
                        Intent intent = new Intent(BookActivity.this, EditBookActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", book);
                        intent.putExtras(bundle);
                        BookActivity.super.startActivityForResult(intent, BookActivity.REQUEST_CODE_ADDBOOK);
                        break;
                }
                mode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {

            }
        });


        return true;
    }
}
