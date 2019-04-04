package com.example.lomovidaudplayer;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PlaybackFragment extends Fragment {

    private Context context;
    private ImageButton cancelButton;
    private PlaybackFragmentListener callback;

    public interface PlaybackFragmentListener {
        void onPlaybackCancelClicked();
    }

    public void setPlaybackFragmentListener(PlaybackFragmentListener callback) {
        this.callback = callback;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        // TODO: setup exoplayer here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playback_fragment, parent, false);

        cancelButton = (ImageButton) rootView.findViewById(R.id.playback_fragment_cancel_button);
        cancelButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close_icon));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO: Setup handles to view objects here

        cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPlaybackCancelClicked();
            }
        });
    }
}
