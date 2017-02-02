package com.memorycat.app.txtreader.speaker;

import java.util.EventListener;

/**
 * 播放完一段文字后的listener
 *
 * Created by xie on 2017/2/2.
 */

public interface TextPlayCompletedListener extends EventListener {

    void afterPlay(TextSpeakerEvent textSpeakerEvent);

}

