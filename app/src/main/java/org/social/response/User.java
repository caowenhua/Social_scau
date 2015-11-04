package org.social.response;

/**
 * Created by caowenhua on 2015/11/3.
 */
public class User {
    private int userId;
    private boolean canShare;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isCanShare() {
        return canShare;
    }

    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }
}
