package cn.com.ichile.pigplayer.core.messages;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 * <p>
 * a general interface of player messages
 */

public interface Msg {
    void runMessage();

    void polledFromQueue();

    void messageFinished();
}
