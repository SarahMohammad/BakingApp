package com.example.nano.bakingapp.fragments;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


public class RecipeInfoDetailFragment extends Fragment {

    PlayerView playerView;
    int currentWindow;
    long playbackPosition;
    SimpleExoPlayer player;
    boolean playWhenReady;
    public static final String SELECTED_POSITION = "selected position";
    public static final String PLAY_WHEN_READY = "play when ready";
    Steps step;
    String videoURL;
    TextView shortDescribtion , longDescribtion ;
    Button next , previous;
    ArrayList<Steps> stepsArrayList;
    int id;

    public RecipeInfoDetailFragment(){}


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(videoURL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(videoURL);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_info_detail, container, false);
        playerView = rootView.findViewById(R.id.playerView);
        longDescribtion = rootView.findViewById(R.id.tv_step_description);
        shortDescribtion = rootView.findViewById(R.id.tv_step_short_description);
        previous = rootView.findViewById(R.id.previous_btn);
        next = rootView.findViewById(R.id.next_btn);

        if(getArguments() != null) {
         step = getArguments().getParcelable(Constatns.STEP);
         stepsArrayList = getArguments().getParcelableArrayList(Constatns.STEPSARRAY);
        }
        videoURL = step.getVideoURL();
        id = step.getId();
        longDescribtion.setText(step.getDescription());
        shortDescribtion.setText(step.getShortDescription());
        initializePlayer(videoURL);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = id+1;
                if(id > stepsArrayList.size()-1){
                    Toast.makeText(getActivity() , getString(R.string.last_step) , Toast.LENGTH_LONG).show();
                    // set the id with the max length
                    id = stepsArrayList.size()-1;
                }else{
                    initializePlayer(stepsArrayList.get(id).getVideoURL());
                    longDescribtion.setText(stepsArrayList.get(id).getDescription());
                    shortDescribtion.setText(stepsArrayList.get(id).getShortDescription());

                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = id - 1;
                if(id < 0){
                    Toast.makeText(getActivity() , getString(R.string.first_step), Toast.LENGTH_LONG).show();
                    id = 0;
                }else{
                    initializePlayer(stepsArrayList.get(id).getVideoURL());
                    longDescribtion.setText(stepsArrayList.get(id).getDescription());
                    shortDescribtion.setText(stepsArrayList.get(id).getShortDescription());

                }
            }
        });



        if (savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);

        }

        return rootView;
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

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking_app")).
                createMediaSource(uri);
    }

    private void initializePlayer(String videoURL) {
        currentWindow= 0;
        playbackPosition = 0;
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);
        playWhenReady = false;
        Uri uri = Uri.parse(videoURL);

        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        if (playbackPosition>0)
            player.seekTo(currentWindow , playbackPosition);
        player.setPlayWhenReady(playWhenReady);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY , playWhenReady);
    }
}
