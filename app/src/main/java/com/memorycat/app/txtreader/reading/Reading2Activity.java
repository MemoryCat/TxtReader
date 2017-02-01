package com.memorycat.app.txtreader.reading;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.book.ReadingBook;
import com.memorycat.app.txtreader.file.FileUtil;
import com.memorycat.app.txtreader.service.TextPlayingService;
import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

import java.io.File;

public class Reading2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Reading2Activity";
    private Book book;
    private TextView readingArea;
    private ReadingBook readingBook;
    private Button btnPlay;
    private TextSpeaker textSpeaker;
    private Button btnPreviousPage;
    private Button btnNextPage;
    private Button btnRefresh;
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
    private Button btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading2);

        Intent intent = super.getIntent();
        this.book = (Book) intent.getSerializableExtra("book");
        this.book.setBookContent(FileUtil.loadFileToString(new File(this.book.getFilePath())));
        this.readingBook = new ReadingBook(this.book);

        this.readingArea = (TextView) findViewById(R.id.ra2_tw_readingArea);
        this.readingArea.setText(this.readingBook.getReadingContent());

        this.textSpeaker = SpeakerFactory.newInstance(SpeakerFactory.SpeakerType.XUNFEI_YUJI, this);


        btnPlay = (Button) findViewById(R.id.a2_btn_play);
        btnPreviousPage = (Button) findViewById(R.id.a2_btn_previousPage);
        btnNextPage = (Button) findViewById(R.id.a2_btn_nextPage);
        btnRefresh = (Button) findViewById(R.id.a2_btn_refresh);
        btnStop = (Button) findViewById(R.id.a2_btn_stop);

        this.btnPlay.setOnClickListener(this);
        this.btnPreviousPage.setOnClickListener(this);
        this.btnNextPage.setOnClickListener(this);
        this.btnRefresh.setOnClickListener(this);
        this.btnStop.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        switch (v.getId()) {
            case R.id.a2_btn_play:
                Log.d(TAG, "onClick:start" + System.currentTimeMillis());
//                this.textSpeaker.play(this.readingBook.getReadingContent());
                this.textPlayingService.init(this.readingBook, this.textSpeaker);
                this.textPlayingService.startPlay();
                Log.d(TAG, "onClick:end" + System.currentTimeMillis());

                break;
            case R.id.a2_btn_previousPage:

                previousPage();
                break;
            case R.id.a2_btn_nextPage:
                nextPage();
                break;
            case R.id.a2_btn_refresh:
                this.readingArea.setText(this.readingBook.getReadingContent());
                break;
            case R.id.a2_btn_stop:
                this.textPlayingService.stopPlay();
                break;
        }
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
}
