package com.example.nano.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.adapters.IngredientsAdapter;
import com.example.nano.bakingapp.models.Ingredients;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {
    ArrayList<Ingredients> ingredients;
    RecyclerView ingredientsRecycler;

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT > 23) {
//            releasePlayer();
//        }
//    }
//    private void releasePlayer() {
//        if (player != null) {
//            playbackPosition = player.getCurrentPosition();
//            currentWindow = player.getCurrentWindowIndex();
//            playWhenReady = player.getPlayWhenReady();
//            player.release();
//            player = null;
//        }
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            ingredients = bundle.getParcelableArrayList("ingredients");
        }


        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ingredientsRecycler = rootView.findViewById(R.id.recycler_ingredients);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsRecycler.setAdapter(new IngredientsAdapter(getActivity(), ingredients));

        return rootView;
    }
}
