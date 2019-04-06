package com.example.lomovidaudplayer;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class MediaArrayAdapter extends ArrayAdapter<MediaItem> {

    private Context context;
    private ArrayList<MediaItem> mediaList = new ArrayList<>();

    final private String EMPTY = "EMPTY";

    public MediaArrayAdapter(Context context, ArrayList<MediaItem> mediaList) {
        super(context, 0, mediaList);
        this.context = context;
        this.mediaList = mediaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.media_list_item, parent, false);
        }

        MediaItem currentItem = mediaList.get(position);
        ImageView image = (ImageView) listItem.findViewById(R.id.media_thumbnail);
        ImageView typeImage = (ImageView) listItem.findViewById(R.id.media_type);

        if (currentItem.getMediaType() == 1) { // is Video
            image.setImageBitmap(currentItem.getMediaVidThumbnail());
            typeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.video_icon));
        } else { // is Audio
            image.setImageURI(Uri.parse(currentItem.getMediaAudThumbnail()));
            if (image.getDrawable() == null) {
                image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_icon));
            }
            typeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.music_icon));
        }

        TextView title = (TextView) listItem.findViewById(R.id.media_title);
        title.setText(currentItem.getMediaTitle());

        TextView artist = (TextView) listItem.findViewById(R.id.media_artist);
        artist.setText(currentItem.getMediaArtist());

        return listItem;
    }


}
