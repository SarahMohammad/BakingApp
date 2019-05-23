package com.example.nano.bakingapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.fragments.RecipeInfoDetailFragment;
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

public class StepDetailActivity extends AppCompatActivity {

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
                new DefaultRenderersFactory(this),
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        playerView = findViewById(R.id.video_view);
        longDescribtion = findViewById(R.id.tv_step_description);
        shortDescribtion = findViewById(R.id.tv_step_short_description);
        previous = findViewById(R.id.previous_btn);
        next = findViewById(R.id.next_btn);

        if(getIntent().getParcelableExtra(Constatns.STEP)!=null){
            step = getIntent().getParcelableExtra(Constatns.STEP);
            stepsArrayList = getIntent().getParcelableArrayListExtra("steps_array");
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
                    Toast.makeText(StepDetailActivity.this , "this is the last step" , Toast.LENGTH_LONG).show();
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
                    Toast.makeText(StepDetailActivity.this , "this is the first step" , Toast.LENGTH_LONG).show();
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

//        Toolbar toolbar =  findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);

//        getAllBakings();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Show the Up button in the action bar.
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
//        if (savedInstanceState == null) {
//            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//            Bundle arguments = new Bundle();
////            arguments.putString(RecipeInfoDetailFragment.ARG_ITEM_ID,
////                    getIntent().getStringExtra(RecipeInfoDetailFragment.ARG_ITEM_ID));
//            RecipeInfoDetailFragment fragment = new RecipeInfoDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.item_detail_container, fragment)
//                    .commit();
//        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            // This ID represents the Home or Up button
//            navigateUpTo(new Intent(this, StepsListActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
