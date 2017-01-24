package com.memorycat.app.txtreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.main.HtmlUIInteraction;
import com.memorycat.app.txtreader.main.MainWebChromeClient;
import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private TextSpeaker textSpeaker;
    private WebView webView;
    private HtmlUIInteraction htmlUIInteraction;
    private MainWebChromeClient mainWebChromeClient;

    public MainActivity(){
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.textSpeaker = SpeakerFactory.newInstance(SpeakerFactory.SpeakerType.XUNFEI_YUJI, this);
        this.webView = new WebView(this);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.mainWebChromeClient = new MainWebChromeClient();
        this.webView.setWebChromeClient(this.mainWebChromeClient);
        this.htmlUIInteraction = new HtmlUIInteraction(this,this.textSpeaker);


        setContentView(this.webView);
        this.webView.loadUrl("file:///android_asset/main/index.html");
        this.webView.addJavascriptInterface(this.htmlUIInteraction,"aui");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ma_btn1:
                this.textSpeaker.play("你好吗?");
                FileDialogFragment fileDialogFragment = new FileDialogFragment();
                fileDialogFragment.show(super.getFragmentManager(),"哈哈哈");
                break;
        }
    }
}
