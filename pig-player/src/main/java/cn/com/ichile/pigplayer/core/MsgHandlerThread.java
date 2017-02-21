package cn.com.ichile.pigplayer.core;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.com.ichile.pigplayer.core.messages.Msg;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 * <p>
 * process a message queue
 */

public class MsgHandlerThread {
    private static final String TAG = "MsgHandlerThread";

    //// TODO: 2017/2/16
    private Queue<Msg> mPlayerMsgQueue = new ConcurrentLinkedQueue<>();

    private final PlayerMsgQueueLock mQueueLock = new PlayerMsgQueueLock();

    private AtomicBoolean mTerminated = new AtomicBoolean(false); // TODO: use it

    private Executor mQueueProcessingThread = Executors.newSingleThreadScheduledExecutor();

    private Msg mLastMsg;

    public MsgHandlerThread() {
        mQueueProcessingThread.execute(new Runnable() {
            @Override
            public void run() {
                Logger.v(TAG, "start worker thread");

                do {
                    mQueueLock.lock(TAG);

                    if (mPlayerMsgQueue.isEmpty()) {
                        try {
                            Logger.v(TAG, "queue is empty, wait for new messages");
                            mQueueLock.wait(TAG);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new RuntimeException("InterruptedException");
                        }
                    }

                    mLastMsg = mPlayerMsgQueue.poll();
                    mLastMsg.polledFromQueue();
                    Logger.v(TAG, "poll mLastMessage " + mLastMsg);

                    mQueueLock.unlock(TAG);

                    mLastMsg.runMessage();

                    mQueueLock.lock(TAG);

                    mLastMsg.messageFinished();

                    mQueueLock.unlock(TAG);

                }
                while (!mTerminated.get());
            }
        });
    }

    public void addSingleMsg(Msg msg) {
        mQueueLock.lock(TAG);
        mPlayerMsgQueue.add(msg);
        mQueueLock.notify(TAG);
        mQueueLock.unlock(TAG);
    }

    public void addMsgList(List<? extends Msg> msgList) {
        mQueueLock.lock(TAG);
        mPlayerMsgQueue.addAll(msgList);
        mQueueLock.notify(TAG);
        mQueueLock.unlock(TAG);
    }

    public void pauseQueueProcessing(String outer) {
        mQueueLock.lock(outer);
    }

    public void resumeQueueProcessing(String outer) {
        mQueueLock.unlock(outer);
    }

    public void clearAllPendingMsg(String outer) {
        if (mQueueLock.isLocked(outer)) {
            mPlayerMsgQueue.clear();
        } else {
            throw new RuntimeException("you need holding a lock first");
        }
    }

    public void terminate() {
        mTerminated.set(true);
    }

}
