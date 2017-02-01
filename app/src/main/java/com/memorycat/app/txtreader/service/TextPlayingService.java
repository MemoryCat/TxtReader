package com.memorycat.app.txtreader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.memorycat.app.txtreader.book.ReadingBook;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

public class TextPlayingService extends Service implements Runnable {

    public static final String INTENT_EXTRA_READING_CONTENT = "intent_extra_readingContent";
    private final IBinder mBinder = new TextPlayingServiceBinder();
    private ReadingBook readingBook;
    private TextSpeaker textSpeaker;
    private boolean playingText = false;

    public void init(ReadingBook readingBook, TextSpeaker textSpeaker) {
        this.readingBook = readingBook;
        this.textSpeaker = textSpeaker;
    }

    @Override
    public void run() {
        for (playingText = true; playingText == true; ) {
            String readingContent = this.readingBook.getReadingContent();
            if(playingText == true) {
                Intent intent = new Intent();//创建Intent对象
                intent.setAction(this.getClass().getName());
                intent.putExtra(INTENT_EXTRA_READING_CONTENT, readingContent);
                super.sendBroadcast(intent);//发送广播
                this.textSpeaker.play(readingContent);
            }else{
                break;
            }

            if(playingText == true){
                this.readingBook.nextPage();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startPlay() {
        new Thread(this).start();
    }

    public void stopPlay() {
        playingText = false;
        this.textSpeaker.stop();
    }

    public class TextPlayingServiceBinder extends Binder {
        public TextPlayingService getService() {
            return TextPlayingService.this;
        }
    }
}
