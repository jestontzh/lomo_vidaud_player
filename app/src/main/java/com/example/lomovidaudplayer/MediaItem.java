package com.example.lomovidaudplayer;

import android.graphics.Bitmap;

public class MediaItem {

    private String mediaTitle;
    private String mediaArtist;
    private String mediaLocation;
    // 1: Video, 2: Audio
    private int mediaType;
    private String mediaAudThumbnail;
    private Bitmap mediaVidThumbnail;

    public MediaItem(String mediaTitle, String mediaArtist, String mediaLocation, int mediaType, String mediaAudThumbnail, Bitmap mediaVidThumnail) {
        this.mediaTitle = mediaTitle;
        this.mediaArtist = mediaArtist;
        this.mediaLocation = mediaLocation;
        this.mediaType = mediaType;
        this.mediaAudThumbnail = mediaAudThumbnail;
        this.mediaVidThumbnail = mediaVidThumnail;
    }

    public String getMediaTitle() {
        return this.mediaTitle;
    }

    public String getMediaArtist() {
        return this.mediaArtist;
    }

    public String getMediaLocation() {
        return this.mediaLocation;
    }

    public int getMediaType() {
        return this.mediaType;
    }

    public String getMediaAudThumbnail() {
        return this.mediaAudThumbnail;
    }

    public Bitmap getMediaVidThumbnail() {
        return this.mediaVidThumbnail;
    }
}
