package com.memorycat.app.txtreader.speaker.xunfeiyuji;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

import java.io.Serializable;


/**
 * Created by xie on 2016/10/5.
 */

public class Speaker implements TextSpeaker, InitListener,Serializable {
    private static final String TAG = "Speaker";
    public static final String SPEECHUTILITY_INIT_PARAMETER = "appid=57f3bcc6";
    protected Context context = null;
    private boolean hasInitialized = false;
    protected SpeechUtility speechUtility;
    protected SpeechSynthesizer speechSynthesizer;

    public Speaker(Context context) {
        this.context = context;
        if (this.context == null) {
            throw new IllegalArgumentException("必须传入Context参数且不能为空!");
        }
        if (false == this._init()) {
            throw new RuntimeException("初始化Speaker失败！");
        }
    }

    private boolean _init() {
        this.speechUtility = SpeechUtility.createUtility(this.context, SPEECHUTILITY_INIT_PARAMETER);
        //检查《语记》是否安装
        //如未安装，获取《语记》下载地址进行下载。安装完成后即可使用服务。
        if (this.speechUtility != null && !this.speechUtility.checkServiceInstalled()) {
            this.hasInitialized = false;
            return false;
        } else {
            this.hasInitialized = true;
            //初始化监听器,同听写初始化监听器，使用云端的情况下不需要监听即可使用，本地需要监听
            //1.创建SpeechSynthesizer对象
            this.speechSynthesizer = SpeechSynthesizer.createSynthesizer(this.context, this);
            //2.合成参数设置
            //设置引擎类型为本地
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            return true;
        }
    }

    @Override
    public void onInit(int i) {
        if (i == ErrorCode.SUCCESS) {
            this.hasInitialized = true;
        } else {
            this.hasInitialized = false;
        }
    }

    @Override
    public boolean init() {
        return this.hasInitialized;
    }

    @Override
    public void play(String text) {
        Log.d(TAG, "play() called with: text = [" + text + "]");
        if (text != null && text.length() > 0) {
            this.speechSynthesizer.startSpeaking(text, null);
        }
    }

    @Override
    public boolean isPalying() {
        return this.speechSynthesizer.isSpeaking();
    }

    @Override
    public void stop() {
        this.speechSynthesizer.stopSpeaking();
    }

    @Override
    public void pause() {
        this.speechSynthesizer.pauseSpeaking();
    }

    @Override
    public void resume() {
        this.speechSynthesizer.resumeSpeaking();
    }


}
