package com.memorycat.app.txtreader.reading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

public class ReadingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private TextSpeaker textSpeaker;
    private WebView webView;
    private HtmlUIInteraction htmlUIInteraction;
    private MainWebChromeClient mainWebChromeClient;

    public ReadingActivity(){
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

        Intent intent = super.getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        this.htmlUIInteraction = new HtmlUIInteraction(book,this,this.textSpeaker);


        setContentView(this.webView);
        this.webView.loadUrl("file:///android_asset/main/index.html");
        this.webView.addJavascriptInterface(this.htmlUIInteraction,"aui");
        this.webView.evaluateJavascript("1+2", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(ReadingActivity.this, "acjs:"+value, Toast.LENGTH_SHORT).show();
            }
        });
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
