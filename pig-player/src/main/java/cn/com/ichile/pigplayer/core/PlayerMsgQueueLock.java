package cn.com.ichile.pigplayer.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 */

public class PlayerMsgQueueLock {
    private static final String TAG = "PlayerMsgQueueLock";

    //TODO
    private final ReentrantLock mQueueLock = new ReentrantLock();
    private final Condition mProcessQueueCondition = mQueueLock.newCondition();

    public void lock(String owner) {
        Logger.v(TAG, ">> lock, owner [" + owner + "]");
        mQueueLock.lock();
        Logger.v(TAG, "<< lock, owner [" + owner + "]");
    }

    public void unlock(String owner) {
        Logger.v(TAG, ">> unlock, owner [" + owner + "]");
        mQueueLock.unlock();
        Logger.v(TAG, "<< unlock, owner [" + owner + "]");
    }

    public boolean isLocked(String owner) {
        boolean isLocked = mQueueLock.isLocked();
        Logger.v(TAG, "isLocked, owner [" + owner + "]");
        return isLocked;
    }

    public void wait(String owner) throws InterruptedException {
        Logger.v(TAG, ">> wait, owner [" + owner + "]");
        mProcessQueueCondition.await();
        Logger.v(TAG, "<< wait, owner [" + owner + "]");
    }

    public void notify(String owner) {
        Logger.v(TAG, ">> notify, owner [" + owner + "]");
        mProcessQueueCondition.signal();
        Logger.v(TAG, "<< notify, owner [" + owner + "]");
    }

}
