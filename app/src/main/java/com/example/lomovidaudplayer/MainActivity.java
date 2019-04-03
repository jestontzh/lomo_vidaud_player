package com.example.lomovidaudplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    final private String TAG = "MainActivity";
    private ArrayList<MediaItem> mediaItemList;
    private ListView listView;
    private MediaArrayAdapter mediaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        listView = (ListView) findViewById(R.id.media_list);

        // Check READ_EXTERNAL_STORAGE permission
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
        } else {
            Log.i(TAG, "READ_EXTERNAL_STORAGE permission already granted");
        }

        // Scan device for audio and video files. Stored in ArrayList<MediaItem>
        mediaItemList = new ArrayList<MediaItem>();
        scanMedia();

        // Attaches ArrayAdapter to the ListView to populate it with content from ArrayList<MediaItem>
        mediaArrayAdapter = new MediaArrayAdapter(this, mediaItemList);
        listView.setAdapter(mediaArrayAdapter);

        // OnClick behaviour for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem clickedItem = (MediaItem) parent.getItemAtPosition(position);

                Log.i(TAG, String.format("Playing: %s | %s", clickedItem.getMediaTitle(), clickedItem.getMediaLocation()));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case READ_EXTERNAL_STORAGE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "READ_EXTERNAL_STORAGE Permission granted");
                } else {
                    Log.i(TAG, "READ_EXTERNAL_STORAGE Permission not granted. Unable to continue");
                }
                return;
            }
        }
    }

    private void scanMedia() {
        ContentResolver contentResolver = getContentResolver();
        Uri audUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri audUriArt = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor audCursor = contentResolver.query(audUri, null, null, null, null);
        Cursor audArtCursor = contentResolver.query(audUriArt, null, null, null, null);

        if (audCursor != null && audCursor.moveToFirst()) {
            int audTitle = audCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int audArtist = audCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int audData = audCursor.getColumnIndex(MediaStore.Audio.Media.DATA); // Location of file
            int audArt = audArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

            do {
                String currentAudTitle = audCursor.getString(audTitle);
                String currentAudArtist = audCursor.getString(audArtist);
                String currentAudData = audCursor.getString(audData);
                String currentAudArt = audCursor.getString(audArt);

                Log.i(TAG, String.format("Creating audio entry for: %s | %s | %s | %s", currentAudTitle, currentAudArtist, currentAudData, currentAudArt));
                MediaItem currentItem = new MediaItem(currentAudTitle, currentAudArtist, currentAudData, 2, currentAudArt, null);
                mediaItemList.add(currentItem);

            } while(audCursor.moveToNext());
        }

        Uri vidUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor vidCursor = contentResolver.query(vidUri, null, null, null, null);

        if (vidCursor != null && vidCursor.moveToFirst()) {
            int vidTitle = vidCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            int vidArtist = vidCursor.getColumnIndex(MediaStore.Video.Media.ARTIST);
            int vidData = vidCursor.getColumnIndex(MediaStore.Video.Media.DATA);


            do {
                String currentVidTitle = vidCursor.getString(vidTitle);
                String currentVidArtist = vidCursor.getString(vidArtist);
                String currentVidData = vidCursor.getString(vidData);
                Bitmap currentThumb = ThumbnailUtils.createVideoThumbnail(currentVidData, MediaStore.Images.Thumbnails.MINI_KIND);

                Log.i(TAG, String.format("Creating video entry for: %s | %s | %s", currentVidTitle, currentVidArtist, currentVidData));
                MediaItem currentItem = new MediaItem(currentVidTitle, currentVidArtist, currentVidData, 1, null, currentThumb);
                mediaItemList.add(currentItem);

            } while(vidCursor.moveToNext());
        }
    }

}

