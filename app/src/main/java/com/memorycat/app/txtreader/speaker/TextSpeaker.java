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
     * 同步堵塞播放，即播放完该方法才返回。
     * @param text
     */
    void play(String text);//播放文字

    boolean isPalying();//是否正在播放

    void stop();//停止播放

    void pause();//暂停播放

    void resume();//继续播放

}
