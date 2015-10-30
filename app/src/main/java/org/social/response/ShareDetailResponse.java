package org.social.response;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class ShareDetailResponse extends BaseResponse {

    /**
     * share : {"shareId":"123456","userId":"00001","userName":"Tom","avatar":"http://XXX/XXXX/XXX.jpg","shareTime":"1234567890","content":"哇哈哈哈哈啊哈哈哈哈哈","imgList":["url1","url2"],"likeCount":"20","commentCount":"20","isLike":true,"isCollect":true,"comments":[{"commentId":"123456","commentTime":"1234567890","userName":"caowenhua","avatar":"urlXX","comment":"wahahah"},{"commentId":"123457","commentTime":"1234567895","userName":"caowenhua","avatar":"urlXX","comment":"wahahah123"}]}
     */

    private ShareEntity share;

    public void setShare(ShareEntity share) {
        this.share = share;
    }

    public ShareEntity getShare() {
        return share;
    }

    public static class ShareEntity {
        /**
         * shareId : 123456
         * userId : 00001
         * userName : Tom
         * avatar : http://XXX/XXXX/XXX.jpg
         * shareTime : 1234567890
         * content : 哇哈哈哈哈啊哈哈哈哈哈
         * imgList : ["url1","url2"]
         * likeCount : 20
         * commentCount : 20
         * isLike : true
         * isCollect : true
         * comments : [{"commentId":"123456","commentTime":"1234567890","userName":"caowenhua","avatar":"urlXX","comment":"wahahah"},{"commentId":"123457","commentTime":"1234567895","userName":"caowenhua","avatar":"urlXX","comment":"wahahah123"}]
         */

        private int shareId;
        private int userId;
        private String userName;
        private String nickname;
        private String avatar;
        private String shareTime;
        private String content;
        private int likeCount;
        private int commentCount;
        private boolean isLike;
        private boolean isCollect;
        private List<String> imgList;
        private List<CommentsEntity> comments;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isLike() {
            return isLike;
        }

        public boolean isCollect() {
            return isCollect;
        }

        public void setShareId(int shareId) {
            this.shareId = shareId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public void setIsCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public void setComments(List<CommentsEntity> comments) {
            this.comments = comments;
        }

        public int getShareId() {
            return shareId;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getShareTime() {
            return shareTime;
        }

        public String getContent() {
            return content;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public boolean getIsLike() {
            return isLike;
        }

        public boolean getIsCollect() {
            return isCollect;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public List<CommentsEntity> getComments() {
            return comments;
        }

        public static class CommentsEntity {
            /**
             * commentId : 123456
             * commentTime : 1234567890
             * userName : caowenhua
             * avatar : urlXX
             * comment : wahahah
             */

            private int userId;
            private int commentId;
            private String commentTime;
            private String userName;
            private String avatar;
            private String comment;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getCommentId() {
                return commentId;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public String getUserName() {
                return userName;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getComment() {
                return comment;
            }
        }
    }
}
