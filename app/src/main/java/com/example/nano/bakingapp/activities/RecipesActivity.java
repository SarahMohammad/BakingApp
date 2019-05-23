package com.example.nano.bakingapp.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.models.BakingModel;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.RestClient;
import com.example.nano.bakingapp.adapters.RecipesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView recipiesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        recipiesRecyclerView = findViewById(R.id.recycler_recipe);
        getAllBakings();
    }

    private void getAllBakings() {

        final ProgressDialog dialog = ProgressDialog.show(RecipesActivity.this, "",
                Constatns.LOADING, true);
        dialog.show();
        RestClient.GitApiInterface apiService = RestClient.getClient();
        Call<ArrayList<BakingModel>> call = apiService.getBakings();
        call.enqueue(new Callback<ArrayList<BakingModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BakingModel>> call, Response<ArrayList<BakingModel>> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {

                    //find if it was a tablet
                    if (findViewById(R.id.item_detail_container) != null) {
                        recipiesRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this , 4));
                        recipiesRecyclerView.setAdapter(new RecipesAdapter(RecipesActivity.this, response.body()));
                    }else{
                        recipiesRecyclerView.setLayoutManager(new LinearLayoutManager(RecipesActivity.this));
                        recipiesRecyclerView.setAdapter(new RecipesAdapter(RecipesActivity.this, response.body()));
                    }

                } else {
                    Toast.makeText(RecipesActivity.this,"else", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BakingModel>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(RecipesActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
