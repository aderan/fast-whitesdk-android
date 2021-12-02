package io.agora.board.fast;

import android.database.Observable;

import com.herewhite.sdk.domain.BroadcastState;
import com.herewhite.sdk.domain.MemberState;
import com.herewhite.sdk.domain.RoomPhase;

import io.agora.board.fast.model.FastStyle;

/**
 * @author fenglibin
 */
class BoardStateObservable extends Observable<BoardStateObserver> {
    public void notifyMemberStateChanged(MemberState memberState) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onMemberStateChanged(memberState);
        }
    }

    public void notifyBroadcastStateChanged(BroadcastState broadcastState) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onBroadcastStateChanged(broadcastState);
        }
    }

    public void notifyRoomPhaseChanged(RoomPhase roomPhase) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onRoomPhaseChanged(roomPhase);
        }
    }

    public void notifyGlobalStyleChanged(FastStyle fastStyle) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onGlobalStyleChanged(fastStyle);
        }
    }
}
