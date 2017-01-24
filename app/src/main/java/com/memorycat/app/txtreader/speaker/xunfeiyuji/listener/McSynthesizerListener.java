package com.memorycat.app.txtreader.speaker.xunfeiyuji.listener;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by xie on 2016/10/5.
 */

public class McSynthesizerListener implements SynthesizerListener {

    private Context context;

    public McSynthesizerListener(Context context) {
        this.context = context;
    }

    private void printMsg(String msg) {
//        Toast.makeText(context, "McSynthesizerListener->" + msg, Toast.LENGTH_SHORT).show();
        Log.i("memorycat_log_tag",msg);
    }

    @Override
    public void onSpeakBegin() {
        this.printMsg("onSpeakBegin");
    }

    @Override
    public void onBufferProgress(int progress, int beginPos, int endPos, String info) {

        this.printMsg("onBufferProgress");
    }

    @Override
    public void onSpeakPaused() {
        this.printMsg("onSpeakPaused");

    }

    @Override
    public void onSpeakResumed() {
        this.printMsg("onSpeakResumed");
    }

    @Override
    public void onSpeakProgress(int progress, int beginPos, int endPos) {

        this.printMsg("onSpeakProgress");
    }

    @Override
    public void onCompleted(SpeechError speechError) {

        this.printMsg("onCompleted");

    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        this.printMsg("onEvent");
    }
}
