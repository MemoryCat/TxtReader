package com.memorycat.app.txtreader.speaker;

/**
 * Created by xie on 2017/1/4.
 */

/**
 * 文字朗读接口
 */
public interface TextSpeaker {
    boolean init();

    /**
     * 异步播放，一调用完这个方法就立刻返回，并在后台播放。
     *
     * 如果要接收播放完的事件请添加
     * @see  TextSpeaker#addTextPlayCompletedListener
     * @param text
     */
    void play(String text);//播放文字

    boolean isPalying();//是否正在播放

    void stop();//停止播放

    void pause();//暂停播放

    void resume();//继续播放

    void addTextPlayCompletedListener(TextPlayCompletedListener textPlayCompletedListener );
    void removeTextPlayCompletedListener(TextPlayCompletedListener textPlayCompletedListener );
}
