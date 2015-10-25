package org.social.bean;

import java.io.Serializable;

public class ImageBean implements Serializable{
    private String url;
    private int width;
    private int height;

    public ImageBean(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "image---->>url="+url+"width="+width+"height"+height;
    }
}
