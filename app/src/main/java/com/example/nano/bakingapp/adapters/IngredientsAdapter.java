package com.example.nano.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nano.bakingapp.models.Ingredients;
import com.example.nano.bakingapp.R;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Ingredients> ingredients;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredients) {
        this.mContext = context;
        this.ingredients = ingredients;

    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView;
        IngredientsAdapter.ViewHolder viewHolder;

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_ingredient, parent, false);
        viewHolder = new IngredientsAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, final int position) {
        holder.ingredient.setText(ingredients.get(position).getQuantity()+" "+ingredients.get(position).getMeasure()+" "+ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient ;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_tv);


        }
    }
}
