package com.memorycat.app.txtreader.reading;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.book.BookSQLHelper;
import com.memorycat.app.txtreader.book.ReadingBook;
import com.memorycat.app.txtreader.file.FileUtil;
import com.memorycat.app.txtreader.service.TextPlayingService;
import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

import java.io.File;

public class Reading2Activity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "Reading2Activity";
    private Book book;
    private TextView readingArea;
    private ReadingBook readingBook;
    private TextSpeaker textSpeaker;
    //    private Button btnPlay;
//    private Button btnPreviousPage;
//    private Button btnNextPage;
//    private Button btnRefresh;
//    private Button btnStop;
    private TextPlayingService textPlayingService;
    private boolean mBound = false;
    private boolean manStopReading = false;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder iBinder) {
            TextPlayingService.TextPlayingServiceBinder binder = (TextPlayingService.TextPlayingServiceBinder) iBinder;
            textPlayingService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    //接收广播更新ui
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            if (Reading2Activity.this.readingArea != null) {
                String s = new String(intent.getStringExtra(TextPlayingService.INTENT_EXTRA_READING_CONTENT));
                Reading2Activity.this.readingArea.setText(s);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading2);

        Intent intent = super.getIntent();
        this.book = (Book) intent.getSerializableExtra("book");
        this.book.setBookContent(FileUtil.loadFileToString(new File(this.book.getFilePath())));
        this.readingBook = new ReadingBook(this.book, new BookSQLHelper(this));

        this.readingArea = (TextView) findViewById(R.id.ra2_tw_readingArea);
        this.readingArea.setText(this.readingBook.getReadingContent());

        this.textSpeaker = SpeakerFactory.newInstance(SpeakerFactory.SpeakerType.XUNFEI_YUJI, this);


//        btnPlay = (Button) findViewById(R.id.a2_btn_play);
//        btnPreviousPage = (Button) findViewById(R.id.a2_btn_previousPage);
//        btnNextPage = (Button) findViewById(R.id.a2_btn_nextPage);
//        btnRefresh = (Button) findViewById(R.id.a2_btn_refresh);
//        btnStop = (Button) findViewById(R.id.a2_btn_stop);

//        this.btnPlay.setOnClickListener(this);
//        this.btnPreviousPage.setOnClickListener(this);
//        this.btnNextPage.setOnClickListener(this);
//        this.btnRefresh.setOnClickListener(this);
//        this.btnStop.setOnClickListener(this);

        super.setTitle(this.book.getBookName());
        ActionBar actionBar = super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.readingArea.setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, TextPlayingService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TextPlayingService.class.getName());
        super.registerReceiver(this.broadcastReceiver, intentFilter);
    }


    private void startPlay() {
        this.textPlayingService.init(this.readingBook, this.textSpeaker);
        this.textPlayingService.startPlay();
    }

    private void stopPlay() {
        this.textPlayingService.stopPlay();
    }

    private void previousPage() {
        if (this.manStopReading == false && this.textSpeaker.isPalying()) {
            this.textPlayingService.stopPlay();
            this.readingBook.previousPage();
//            this.readingArea.setText(this.readingBook.getReadingContent());
            this.textPlayingService.startPlay();
        } else {
            this.readingBook.previousPage();
            this.readingArea.setText(this.readingBook.getReadingContent());
        }
    }

    private void nextPage() {
        if (this.manStopReading == false && this.textSpeaker.isPalying()) {
            this.textPlayingService.stopPlay();
            this.readingBook.nextPage();
//            this.readingArea.setText(this.readingBook.getReadingContent());
            this.textPlayingService.startPlay();
        } else {
            this.readingBook.nextPage();
            this.readingArea.setText(this.readingBook.getReadingContent());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");
        switch (v.getId()) {
            case R.id.ra2_tw_readingArea:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float height = v.getHeight();
                    float y = event.getY();
                    if (y / height >= 0.5) {
                        nextPage();
                    } else {
                        previousPage();
                    }
                }
                return true;

        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reading_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_reading_activity_start_play:
                startPlay();
                return true;
            case R.id.menu_item_reading_activity_stop_play:
                stopPlay();
                return true;

            case R.id.menu_item_reading_activity_reading_settings:
            case R.id.menu_item_reading_activity_playing_settings:
            case R.id.menu_item_reading_activity_help:
                Toast.makeText(this, "这个功能目前还未开发，马上就会更新该功能！\n\n敬请关注http://www.memorycat.com/app/txtreader", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
