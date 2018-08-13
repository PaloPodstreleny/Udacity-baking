package com.project.podstreleny.pavol.baking;


import android.content.Intent;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.podstreleny.pavol.baking.ui.recipieMaster.RecipeMasterActivity;
import com.project.podstreleny.pavol.baking.util.TestUtil;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MasterRecipePageInstrumentedTest {

    private static final int FIRST_POSITION = 0;
    private static final int RECIPE_ID = 1;
    private static final String INGREDIENTS = "Ingredients";
    private static final String FOLLOW_STEPS = "Follow steps";


    @Rule
    public ActivityTestRule<RecipeMasterActivity> masterActivityActivityTestRule = new ActivityTestRule<>(RecipeMasterActivity.class);

    @Before
    public void init() {
        final Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, RECIPE_ID);
        masterActivityActivityTestRule.launchActivity(intent);
    }

    @Test
    public void checkHeadingTextTest() {
        // Check if ingredients and follow steps are displayed
        onView(allOf(withId(R.id.ingredients_tv))).check(matches(withText(INGREDIENTS))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.steps_tv))).check(matches(withText(FOLLOW_STEPS))).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstItemsInRecyclerViewsInFirstRecipeTest() {
        onView(ViewMatchers.withId(R.id.ingredients_rv)).check(matches(isDisplayed()));

        onView(Matchers.allOf(ViewMatchers.withId(R.id.ingredients_rv)))
                .perform(scrollToPosition(FIRST_POSITION))
                .check(matches(TestUtil.atPosition(FIRST_POSITION, hasDescendant(withText("2.0 CUP of Graham Cracker crumbs")))));

        onView(ViewMatchers.withId(R.id.steps_rv)).check(matches(isDisplayed()));

        onView(Matchers.allOf(ViewMatchers.withId(R.id.steps_rv)))
                .perform(scrollToPosition(FIRST_POSITION))
                .check(matches(TestUtil.atPosition(FIRST_POSITION, hasDescendant(withText("Recipe Introduction")))));

    }

    @After
    public void releaseData() {
        masterActivityActivityTestRule.finishActivity();
    }


}
