package com.example.lomovidaudplayer;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PlaybackFragment extends Fragment {

    private Context context;
    private ImageButton cancelButton;
    private PlaybackFragmentListener callback;
    private PlayerView playerView;
    private Player player;

    private String mediaTitle;
    private String mediaLocation;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    public interface PlaybackFragmentListener {
        void onPlaybackCancelClicked();
    }

    public void setPlaybackFragmentListener(PlaybackFragmentListener callback) {
        this.callback = callback;
    }

    public static PlaybackFragment newInstance(String title, String location) {
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

        this.mediaTitle = getArguments().getString("MEDIA_TITLE");
        this.mediaLocation = getArguments().getString("MEDIA_LOCATION");

        initPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playback_fragment, parent, false);

        cancelButton = (ImageButton) rootView.findViewById(R.id.playback_fragment_cancel_button);
        cancelButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close_icon));

        playerView = (PlayerView) rootView.findViewById(R.id.playback_fragment);
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

    private void initPlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(context, new DefaultRenderersFactory(context), new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }


        Uri uri = Uri.parse(mediaLocation);
        MediaSource mediaSource = buildMediaSource(uri);
        // TODO: Why .prepare() cannot be found?
//        player.prepare(mediaSource, true, false);


    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("lomotif-vidaud-player")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
