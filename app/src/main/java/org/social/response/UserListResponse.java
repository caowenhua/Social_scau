package org.social.response;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class UserListResponse extends BaseResponse {

    /**
     * userList : [{"userId":12345,"userName":"caowenhua","isShare":"1"},{"userId":"12345","userName":"caowenhua","isShare":"1"}]
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
         * isShare : 1
         */

        private int userId;
        private String userName;
        private String nickname;
        private boolean isShare;
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

        public boolean isShare() {
            return isShare;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setIsShare(boolean isShare) {
            this.isShare = isShare;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public boolean getIsShare() {
            return isShare;
        }
    }
}
