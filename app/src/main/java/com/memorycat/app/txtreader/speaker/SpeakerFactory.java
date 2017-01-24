package com.memorycat.app.txtreader.speaker;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import org.w3c.dom.Text;

/**
 * Created by xie on 2017/1/5.
 */

public class SpeakerFactory {


    public enum SpeakerType {
        BAIDU_LIZAIXIAN,//百度离在线语音合成
        XUNFEI_YUJI//科大讯飞语记
    }

    public static TextSpeaker newInstance(SpeakerType speakerType, Context context) {
        switch (speakerType) {
            case BAIDU_LIZAIXIAN:
                throw new UnsupportedOperationException("未实现百度语音合成的TextSpeaker");
            case XUNFEI_YUJI:
                return SpeakerFactory.newXunfeiyujiInstance(context);
            default:
        }
        return null;
    }

    /**
     * 使用科大讯飞语记插件
     *
     * @param context
     * @return
     */
    private static TextSpeaker newXunfeiyujiInstance(Context context) {
        return new com.memorycat.app.txtreader.speaker.xunfeiyuji.Speaker(context);
    }
}
