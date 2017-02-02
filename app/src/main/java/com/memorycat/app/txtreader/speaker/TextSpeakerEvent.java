package com.memorycat.app.txtreader.speaker;

import java.util.EventObject;

/**
 * Created by xie on 2017/2/2.
 */

public class TextSpeakerEvent extends EventObject {
    private String playingText;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TextSpeakerEvent(Object source,String playingText) {
        super(source);
        this.playingText=playingText;
    }
}
