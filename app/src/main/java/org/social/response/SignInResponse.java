package org.social.response;

/**
 * Created by Administrator on 2015/10/17.
 */
public class SignInResponse extends BaseResponse {

    /**
     * userId : 123456
     */

    private int userId;
    private String nickname;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
