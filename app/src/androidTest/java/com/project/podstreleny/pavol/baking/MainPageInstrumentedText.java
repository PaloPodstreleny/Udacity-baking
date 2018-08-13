package com.project.podstreleny.pavol.baking;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;


import com.project.podstreleny.pavol.baking.ui.main.RecipeAdapter;
import com.project.podstreleny.pavol.baking.ui.main.MainActivity;
import com.project.podstreleny.pavol.baking.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainPageInstrumentedText {

    private static final int CURRENT_ITEM = 2;
    private static final int POSITION = 0;
    private static final String NUTELA_PIE = "Nutella Pie";
    private static final String PORTIONS = "8 portions";

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);


    @Before
    public void setupAllExternalIntents(){
       intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }

    @Test
    public void checkDataPassedToAnotherActivityTest(){
        // Scroll to position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_rv))
                .perform(RecyclerViewActions.<RecipeAdapter.RecipieViewHolder>actionOnItemAtPosition(CURRENT_ITEM, click()));

        // Check intent value send to another activity
        intended(allOf(hasExtra(Intent.EXTRA_TEXT,CURRENT_ITEM + 1)));

    }

    @Test
    public void checkActualValuesOfCardViewTest(){
        // Scroll to recyclerView position and check if data in textviews are correct
        onView(allOf(ViewMatchers.withId(R.id.recipe_rv)))
                .perform(scrollToPosition(POSITION))
                .check(matches(TestUtil.atPosition(POSITION,hasDescendant(withText(NUTELA_PIE)))))
                .check(matches(TestUtil.atPosition(POSITION,hasDescendant(withText(PORTIONS)))));

    }


}
