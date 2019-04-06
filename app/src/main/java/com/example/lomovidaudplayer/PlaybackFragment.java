package com.example.lomovidaudplayer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.PlayerView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PlaybackFragment extends Fragment {

    final static private String TAG = "PlaybackFragment";
    private Context context;
    private ImageButton cancelButton;
    private PlaybackFragmentListener callback;
    private PlayerView playerView;

    public interface PlaybackFragmentListener {
        void onPlaybackCancelClicked();
        void onCreateViewInitExoPlayer(PlayerView playerView);
    }

    public void setPlaybackFragmentListener(PlaybackFragmentListener callback) {
        this.callback = callback;
    }

    public static PlaybackFragment newInstance(String title, String location) {
        Log.i(TAG, String.format("New PlaybackFragment instance started with %s | %s", title, location));

        PlaybackFragment playbackFragment = new PlaybackFragment();
        Bundle args = new Bundle();
        args.putString("MEDIA_TITLE", title);
        args.putString("MEDIA_LOCATION", location);
        playbackFragment.setArguments(args);

        return playbackFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playback_fragment, parent, false);

        playerView = (PlayerView) rootView.findViewById(R.id.playback_fragment);

        cancelButton = (ImageButton) rootView.findViewById(R.id.playback_fragment_cancel_button);
        cancelButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close_icon));

        // notify MainActivity to init ExoPlayer
        callback.onCreateViewInitExoPlayer(playerView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPlaybackCancelClicked();
            }
        });
    }
}
