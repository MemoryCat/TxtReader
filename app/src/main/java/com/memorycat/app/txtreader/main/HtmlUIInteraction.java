package com.memorycat.app.txtreader.main;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

/**
 * Created by xie on 2017/1/24.
 */

public class HtmlUIInteraction {

    private Activity activity;
    private TextSpeaker textSpeaker;

    public HtmlUIInteraction(Activity activity, TextSpeaker textSpeaker) {
        this.activity = activity;
        this.textSpeaker = textSpeaker;
    }

    @JavascriptInterface
    public void addLocalBook(){
        FileDialogFragment fileDialogFragment = new FileDialogFragment();
        fileDialogFragment.show(this.activity.getFragmentManager(),"fileDialogFragment");
    }
    @JavascriptInterface
    public void playText(String text){
        this.textSpeaker.play(text);
    }
}
