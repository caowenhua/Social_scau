package org.social.response;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class FollowListResponse extends BaseResponse{

    /**
     * userList : [{"userId":12345,"userName":"caowenhua","signature":"123456","isAttention":true},{"userId":12345,"userName":"caowenhua","signature":"123456","isAttention":true}]
     */

    private List<UserListEntity> userList;

    public void setUserList(List<UserListEntity> userList) {
        this.userList = userList;
    }

    public List<UserListEntity> getUserList() {
        return userList;
    }

    public static class UserListEntity {
        /**
         * userId : 12345
         * userName : caowenhua
         * signature : 123456
         * isAttention : true
         */

        private int userId;
        private String userName;
        private String nickname;
        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isAttention() {
            return isAttention;
        }

        public void setIsAttention(boolean isAttention) {
            this.isAttention = isAttention;
        }

        private String signature;
        private boolean isAttention;

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setisAttention(boolean isAttention) {
            this.isAttention = isAttention;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getSignature() {
            return signature;
        }

        public boolean getisAttention() {
            return isAttention;
        }
    }
}
