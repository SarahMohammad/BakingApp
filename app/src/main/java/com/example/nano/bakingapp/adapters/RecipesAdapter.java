package com.example.nano.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.models.BakingModel;
import com.example.nano.bakingapp.activities.StepsListActivity;
import com.example.nano.bakingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    Context mContext;
    ArrayList<BakingModel> recipes;
    Gson gson ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public RecipesAdapter(Context context, ArrayList<BakingModel> recipes) {
        this.mContext = context;
        this.recipes = recipes;
        gson = new Gson();
        sharedpreferences = mContext.getSharedPreferences(Constatns.SHAREDPREF,MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    @NonNull
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView;
        ViewHolder viewHolder;

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recipe, parent, false);
        viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.ViewHolder holder, final int position) {
        holder.recipeName.setText(recipes.get(position).getName());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the ingredients in shared to view in the widget
                editor.putString(Constatns.WIDGETINGREDIENTS , gson.toJson(recipes.get(position).getIngredients()));
                editor.commit();

                Intent i = new Intent(mContext , StepsListActivity.class);
                i.putExtra(Constatns.STEPS_LIST, recipes.get(position));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ConstraintLayout item;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            item = itemView.findViewById(R.id.recipe_layout_item);
        }
    }
}
