package com.example.lomovidaudplayer;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.net.Uri;

public class MediaItem {

    private Uri uri;
    private long mediaId;
    private String mediaTitle;
    private String mediaArtist;
    private String mediaLocation;
    // 1: Video, 2: Audio
    private int mediaType;
    private String mediaAudThumbnail;
    private Bitmap mediaVidThumbnail;

    final private String UNKNOWN = "<unknown>";

    public MediaItem(Uri uri, long mediaId, String mediaTitle, String mediaArtist, String mediaLocation, int mediaType, String mediaAudThumbnail, Bitmap mediaVidThumnail) {
        this.uri = uri;
        this.mediaId = mediaId;
        this.mediaTitle = mediaTitle;
        this.mediaArtist = mediaArtist;
        this.mediaLocation = mediaLocation;
        this.mediaType = mediaType;
        this.mediaAudThumbnail = mediaAudThumbnail;
        this.mediaVidThumbnail = mediaVidThumnail;
    }

    public long getMediaId() {
        return this.mediaId;
    }

    public String getMediaTitle() {
        return this.mediaTitle;
    }

    public String getMediaArtist() {
        if (this.mediaArtist == null) {
            return UNKNOWN;
        } else {
            return this.mediaArtist;
        }
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

    public Uri getMediaUri() {
        Uri mediaUri = ContentUris.withAppendedId(uri, this.mediaId);
        return mediaUri;
    }
}
