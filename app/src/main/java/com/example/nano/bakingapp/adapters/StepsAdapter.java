package com.example.nano.bakingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.fragments.IngredientsFragment;
import com.example.nano.bakingapp.models.BakingModel;
import com.example.nano.bakingapp.models.Ingredients;
import com.example.nano.bakingapp.fragments.RecipeInfoDetailFragment;
import com.example.nano.bakingapp.activities.IngredientsActivity;
import com.example.nano.bakingapp.activities.StepDetailActivity;
import com.example.nano.bakingapp.activities.StepsListActivity;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private final StepsListActivity mParentActivity;
    private final ArrayList<Steps> mValues;
    private final boolean mTwoPane;
    private ArrayList<Ingredients> ingredients;



    public StepsAdapter(StepsListActivity parent, BakingModel bakingModel, boolean twoPane) {
        mValues = bakingModel.getSteps();
        ingredients = bakingModel.getIngredients();
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(position == 0){
            holder.mStepName.setText(Constatns.INGREDIENTS);

        }else{
            holder.mStepName.setText(mValues.get(position-1).getShortDescription());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for tablet view
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    if (position == 0){
                        //inflate the ingredients fragment
                        arguments.putParcelableArrayList(Constatns.INGREDIENTS , ingredients);
                        IngredientsFragment fragment = new IngredientsFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    }else{
                        //inflate the step fragment to view the video
                        arguments.putParcelable(Constatns.STEP , mValues.get(position-1));
                        arguments.putParcelableArrayList(Constatns.STEPSARRAY , mValues);
                        RecipeInfoDetailFragment fragment = new RecipeInfoDetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    }

                } else {
                    //for phone view
                    if(position == 0){
                        //open ingredients activity
                        Intent i = new Intent(view.getContext() , IngredientsActivity.class);
                        i.putExtra(Constatns.INGREDIENTS_LIST, ingredients);
                        view.getContext().startActivity(i);

                    }else{
                        //open step details activity
                        Intent intent = new Intent(view.getContext(), StepDetailActivity.class);
                        //send video details
                        intent.putExtra(Constatns.STEP , mValues.get(position-1));
                        intent.putExtra(Constatns.STEPSARRAY, mValues);
                        view.getContext().startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mStepName;

        ViewHolder(View view) {
            super(view);
            mStepName = view.findViewById(R.id.step_tv);
        }
    }
}