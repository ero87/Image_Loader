package com.example.ero.homework_load_image.models;

public class Model {
    private String title;
    private String imageUrl;
    private boolean download;

    public Model(String title, String imageUrl, boolean download) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.download = download;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
