package com.example.nano.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.models.Ingredients;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.adapters.IngredientsAdapter;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    RecyclerView ingredientsRecycler;
    ArrayList<Ingredients> ingredientsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ingredientsRecycler = findViewById(R.id.recycler_ingredients);
        ingredientsList = getIntent().getParcelableArrayListExtra(Constatns.INGREDIENTS_LIST);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(IngredientsActivity.this));
        ingredientsRecycler.setAdapter(new IngredientsAdapter(IngredientsActivity.this, ingredientsList));

    }
}
