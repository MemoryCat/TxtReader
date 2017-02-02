package com.memorycat.app.txtreader.reading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.memorycat.app.txtreader.R;
import com.memorycat.app.txtreader.book.Book;
import com.memorycat.app.txtreader.file.FileDialogFragment;
import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

import java.io.File;
@Deprecated
public class ReadingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private TextSpeaker textSpeaker;
    private WebView webView;
    private HtmlUIInteraction htmlUIInteraction;
    private MainWebChromeClient mainWebChromeClient;

    public ReadingActivity() {
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
        this.htmlUIInteraction = new HtmlUIInteraction(this.webView,book, this, this.textSpeaker);
        this.webView.addJavascriptInterface(this.htmlUIInteraction,"aui");
        setContentView(this.webView);


        File file = new File(book.getFilePath());

        if (file.exists()) {
//            this.webView.loadData( "正在打开书本：" + book.getFilePath(),"text/html","utf-8");
            Toast.makeText(this, "正在打开书本：" + book.getFilePath(), Toast.LENGTH_SHORT).show();
            this.webView.loadUrl("file:///android_asset/main/index.html");
        } else {
            TextView textView = new TextView(this);
            textView.setText("文件不存在：" + book.getFilePath());
            super.setContentView(textView);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ma_btn1:
                this.textSpeaker.play("你好吗?");
                FileDialogFragment fileDialogFragment = new FileDialogFragment();
                fileDialogFragment.show(super.getFragmentManager(), "哈哈哈");
                break;
        }
    }
}
