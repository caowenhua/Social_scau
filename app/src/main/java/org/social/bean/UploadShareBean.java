package org.social.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/25.
 */
public class UploadShareBean implements Serializable {
    private String content;
    private List<String> imgs;
    private boolean isShared;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
