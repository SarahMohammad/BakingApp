package com.example.nano.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.nano.bakingapp.Constatns;
import com.example.nano.bakingapp.R;
import com.example.nano.bakingapp.activities.RecipesActivity;
import com.example.nano.bakingapp.models.Ingredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BakingAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,  ArrayList<Ingredients> ingredientList) {

        StringBuilder stringBuilder = new StringBuilder();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //put the ingredients in intent and open ingredients activity
        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);


        for (Ingredients ingredient : ingredientList) {
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            String ingredientName = ingredient.getIngredient();
            String line = quantity + " " + measure + " " + ingredientName;
            stringBuilder.append( line + "\n");
        }
        views.setTextViewText(R.id.appwidget_text, stringBuilder);

        // OnClick intent for textview
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constatns.SHAREDPREF,MODE_PRIVATE);
        String result  = sharedpreferences.getString(Constatns.WIDGETINGREDIENTS , null);
        Gson gson = new Gson();
        ArrayList<Ingredients> ingredients = gson.fromJson(result,new TypeToken<ArrayList<Ingredients>>(){}.getType());
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

