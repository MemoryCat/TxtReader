package com.memorycat.app.txtreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.memorycat.app.txtreader.book.ReadingBook;
import com.memorycat.app.txtreader.speaker.TextPlayCompletedListener;
import com.memorycat.app.txtreader.speaker.TextSpeaker;
import com.memorycat.app.txtreader.speaker.TextSpeakerEvent;

public class TextPlayingService extends Service implements  TextPlayCompletedListener {
    private static final String TAG = "TextPlayingService";
    public static final String INTENT_EXTRA_READING_CONTENT = "intent_extra_readingContent";
    private final IBinder mBinder = new TextPlayingServiceBinder();
    private ReadingBook readingBook;
    private TextSpeaker textSpeaker;
    private boolean playingText = false;
    private boolean hasInit = false;

    public synchronized void init(ReadingBook readingBook, TextSpeaker textSpeaker) {
        if (this.hasInit == false) {
            this.hasInit = true;
            this.readingBook = readingBook;
            this.textSpeaker = textSpeaker;
            this.textSpeaker.addTextPlayCompletedListener(this);
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startPlay() {
        this.playingText=true;
        String readingContent = this.readingBook.getReadingContent();

        Intent intent = new Intent();//创建Intent对象
        intent.setAction(this.getClass().getName());
        intent.putExtra(INTENT_EXTRA_READING_CONTENT, readingContent);
        super.sendBroadcast(intent);//发送广播

        this.textSpeaker.play(readingContent);
    }

    public void stopPlay() {
        playingText = false;
        if(this.textSpeaker!=null){
            this.textSpeaker.stop();
        }
    }

    @Override
    public void afterPlay(TextSpeakerEvent textSpeakerEvent) {
        Log.d(TAG, "afterPlay() called with: textSpeakerEvent = [" + textSpeakerEvent + "]");
        if (playingText == true) {
            this.readingBook.nextPage();
            this.startPlay();
        }
    }

    public class TextPlayingServiceBinder extends Binder {
        public TextPlayingService getService() {
            return TextPlayingService.this;
        }
    }
}
