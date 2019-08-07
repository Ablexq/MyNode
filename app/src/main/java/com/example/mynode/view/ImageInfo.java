package com.example.mynode.view;

public class ImageInfo {
    public int width;
    public int height;
    public String imageUrl;

    public ImageInfo(int width, int height, String imageUrl) {
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
