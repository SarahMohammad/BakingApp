package com.example.nano.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.activities.StepDetailActivity;
import com.example.nano.bakingapp.models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecipeInfoDetailFragment extends Fragment {
//    private Steps step;
//    private Bitmap stepThumbnail;
//    TextView tvStepShortDescription , tvStepDescription;
//    ImageView thumbnailView;
//    private SimpleExoPlayer mExoPlayer;
//    SimpleExoPlayerView playerView;


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
        hideSystemUi();
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

//        Bundle bundle = this.getArguments();
//        if (bundle!=null){
//            step = bundle.getParcelable(Constatns.STEP);
//        }
//
//        if (savedInstanceState != null){
//            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
//            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
//        }


        final View rootView = inflater.inflate(R.layout.fragment_recipe_info_detail, container, false);



        playerView = rootView.findViewById(R.id.playerView);
        longDescribtion = rootView.findViewById(R.id.tv_step_description);
        shortDescribtion = rootView.findViewById(R.id.tv_step_short_description);
        previous = rootView.findViewById(R.id.previous_btn);
        next = rootView.findViewById(R.id.next_btn);

//        if(getIntent().getParcelableExtra(Constatns.STEP)!=null){
//            step = getIntent().getParcelableExtra(Constatns.STEP);
//            stepsArrayList = getIntent().getParcelableArrayListExtra("steps_array");
//        }
        if(getArguments() != null) {
         step = getArguments().getParcelable(Constatns.STEP);
         stepsArrayList = getArguments().getParcelableArrayList("steps_array");
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
                    Toast.makeText(getActivity() , "this is the last step" , Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity() , "this is the first step" , Toast.LENGTH_LONG).show();
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
//            Toast.makeText(StepDetailActivity.this , playbackPosition +"--"+playWhenReady , Toast.LENGTH_LONG).show();

        }



//        tvStepShortDescription= rootView.findViewById(R.id.tv_step_shtionort_description);
//        tvStepDescription = rootView.findViewById(R.id.tv_step_descrip);
//        thumbnailView = rootView.findViewById(R.id.thumbnailView);
//        playerView = rootView.findViewById(R.id.playerView);
        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        tvStepShortDescription.setText(step.getShortDescription());
//        tvStepDescription.setText(step.getDescription());
//
//        // General Case
//        // 1 - If Video exists: Show Video
//        // 2 - If Thumbnail exists: Show image view with the thumbnail where at the place where the video would normally show up.
//
//        // If video exists, initialize player (General case 1)
//        if (!TextUtils.isEmpty(step.getVideoURL()))
//            initializerPlayer(Uri.parse(step.getVideoURL()));
//        else if (!step.getThumbnailURL().isEmpty()) {
//            // if Thumbnail exists but no video exists (General case 2)
//            thumbnailView.setVisibility(View.VISIBLE);
//
//
//            Picasso.get().load(step.getThumbnailURL()).into(thumbnailView);
//        }
//    }

//    private void initializerPlayer(Uri mediaUri){
//        if (mExoPlayer==null){
//            playerView.setVisibility(View.VISIBLE);
//            TrackSelector trackSelector = new DefaultTrackSelector();
//            LoadControl loadControl = new DefaultLoadControl();
//            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
//            playerView.setPlayer(mExoPlayer);
//
//            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
//
//            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
//            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
//                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
//            mExoPlayer.prepare(mediaSource);
//            if (position>0)
//                mExoPlayer.seekTo(position);
//            mExoPlayer.setPlayWhenReady(playWhenReady);
//        }
//    }
//
//    private boolean playWhenReady = true;
//    private long position = -1;
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mExoPlayer != null){
//            position = mExoPlayer.getCurrentPosition();
//            playWhenReady = mExoPlayer.getPlayWhenReady();
//            releasePlayer();
//        }
//    }
//
//    private void releasePlayer() {
//        if (mExoPlayer != null) {
//            mExoPlayer.stop();
//            mExoPlayer.release();
//            mExoPlayer = null;
//        }
//    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
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
//        player.setPlayWhenReady(playWhenReady);
//        player.seekTo(currentWindow, playbackPosition);
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
