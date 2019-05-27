package com.example.nano.bakingapp;

import android.app.Instrumentation;
import android.app.Activity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.nano.bakingapp.activities.RecipesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;

@RunWith(AndroidJUnit4.class)
public class IntentTesting {


    @Rule
    public IntentsTestRule<RecipesActivity> mActivityRule = new IntentsTestRule<>(
            RecipesActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void intentTest(){

        // Let the UI load completely first
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Recyclerview scroll to position
        onView(ViewMatchers.withId(R.id.recycler_recipe)).perform(RecyclerViewActions.scrollToPosition(4));

        //Perform Recyclerview click on item at position
        onView(withId(R.id.recycler_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if intent (RecipeActivity to StepDetailActivity) has RECIPE_INTENT_EXTRA
        intended(hasExtraWithKey(Constatns.STEPS_LIST));

    }

}
