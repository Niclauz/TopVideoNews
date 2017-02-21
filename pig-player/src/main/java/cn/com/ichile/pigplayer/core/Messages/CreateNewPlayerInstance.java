package cn.com.ichile.pigplayer.core.messages;


import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage creates new MediaPlayer instance that will be used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class CreateNewPlayerInstance extends PlayerMessage {

    public CreateNewPlayerInstance(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.createNewPlayerInstance();
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.CREATING_PLAYER_INSTANCE;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.PLAYER_INSTANCE_CREATED;
    }
}
