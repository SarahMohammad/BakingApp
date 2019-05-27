package com.example.nano.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.models.BakingModel;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.models.Steps;
import com.example.nano.bakingapp.adapters.StepsAdapter;

import java.util.ArrayList;


public class StepsListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    ArrayList<Steps> stepsList;
    RecyclerView recyclerView;
    BakingModel bakingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        bakingModel = getIntent().getParcelableExtra(Constatns.STEPS_LIST);
        assert bakingModel!=null;
        stepsList = bakingModel.getSteps();

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
        recyclerView.setAdapter(new StepsAdapter(this, bakingModel, mTwoPane));

    }
}
