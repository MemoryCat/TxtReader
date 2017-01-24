package com.memorycat.app.txtreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.memorycat.app.txtreader.speaker.SpeakerFactory;
import com.memorycat.app.txtreader.speaker.TextSpeaker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private TextSpeaker textSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.ma_btn1);
        this.btn1.setOnClickListener(this);
        this.textSpeaker = SpeakerFactory.newInstance(SpeakerFactory.SpeakerType.XUNFEI_YUJI, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ma_btn1:
                this.textSpeaker.play("你好吗?");
                break;
        }
    }
}
