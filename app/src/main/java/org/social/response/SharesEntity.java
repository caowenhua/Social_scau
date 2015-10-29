package org.social.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/25.
 */
public class SharesEntity implements Serializable{
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
    private List<String> imgList;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isLike() {
        return isLike;
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

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
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

    public List<String> getImgList() {
        return imgList;
    }
}
