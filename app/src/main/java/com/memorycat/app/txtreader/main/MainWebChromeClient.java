package com.memorycat.app.txtreader.main;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

/**
 * Created by xie on 2017/1/24.
 */

public class MainWebChromeClient extends WebChromeClient {

    private static final String TAG = "MainWebChromeClient";


    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG, "onConsoleMessage: " + consoleMessage.message().toString());
        return super.onConsoleMessage(consoleMessage);
    }
}
