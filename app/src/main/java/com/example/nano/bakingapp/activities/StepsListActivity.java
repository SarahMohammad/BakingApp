package com.example.nano.bakingapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.models.BakingModel;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.models.Steps;
import com.example.nano.bakingapp.adapters.StepsAdapter;
import com.example.nano.bakingapp.widget.BakingAppWidget;

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
//        setTitle("Steps");

//        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setTitle("Steps");

//        Log.e("key" ,sharedpreferences.getString("widger_ingredients" , null) );
//        Toast.makeText(StepsListActivity.this , sharedpreferences.getString("widger_ingredients" , null) , Toast.LENGTH_LONG).show();

//        //Here we create AppWidgetManager to update AppWidget state
//        BakingAppWidget appWidgetManager = BakingAppWidget.getInstance(this);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, YummioWidgetProvider.class));
//        //Pass recipe info into the widget provider
//        YummioWidgetProvider.updateWidgetRecipe(this, ingredientsString, imgResId, appWidgetManager, appWidgetIds);




        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
        recyclerView.setAdapter(new StepsAdapter(this, bakingModel, mTwoPane));

    }
}
