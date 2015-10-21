package org.social.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/17.
 */
public class UserInfoResponse extends BaseResponse {

    /**
     * user : {"userId":123456,"sex":1,"signature":"hahahhahahaha","phone":"13500000000","email":"27000000@qq.com","avatar":"url"}
     */

    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class UserEntity implements Serializable{
        /**
         * userId : 123456
         * sex : 1
         * signature : hahahhahahaha
         * phone : 13500000000
         * email : 27000000@qq.com
         * avatar : url
         */

        private int userId;
        private int sex;
        private String nickname;
        private String signature;
        private String phone;
        private String email;
        private String avatar;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getUserId() {
            return userId;
        }

        public int getSex() {
            return sex;
        }

        public String getSignature() {
            return signature;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
