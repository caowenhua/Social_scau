package org.social.response;

import java.util.List;

/**
 * Created by caowenhua on 2015/11/3.
 */
public class AdminListResponse extends BaseResponse{
    private List<User> user;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
