package com.project.podstreleny.pavol.baking.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.MutableLiveData;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.SingleFragmentActivityTestPurpose;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.service.Resource;
import com.project.podstreleny.pavol.baking.ui.main.MainActivityFragment;
import com.project.podstreleny.pavol.baking.ui.main.RecipeAdapter;
import com.project.podstreleny.pavol.baking.util.EspressoTestUtil;
import com.project.podstreleny.pavol.baking.util.FakeGenerator;
import com.project.podstreleny.pavol.baking.util.TestUtil;
import com.project.podstreleny.pavol.baking.util.ViewModelFactoryProvider;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;
import com.project.podstreleny.pavol.baking.viewModels.RecipeViewModel;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainPageFragmentTest {

    private static final int CURRENT_POSITION = 1;

    @Rule
    public IntentsTestRule<SingleFragmentActivityTestPurpose> mainIntentsTestRule = new IntentsTestRule<>(SingleFragmentActivityTestPurpose.class);

    private RecipeViewModel mViewModel;
    private final MutableLiveData<Resource<List<Recipe>>> mRecipes = new MutableLiveData<>();
    private List<Recipe> fakeRecipes;

    @Before
    public void init() {
        intending(Matchers.not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        //Mock ViewModel
        mViewModel = mock(RecipeViewModel.class);
        when(mViewModel.getRecipes()).thenReturn(mRecipes);

        //Create fake response
        fakeRecipes = new ArrayList<>();
        fakeRecipes.addAll(FakeGenerator.generateFakeRecipes(10));

        final TestMainActivityFragment fragment = new TestMainActivityFragment();
        fragment.setViewModerProvider(ViewModelFactoryProvider.getViewModelFactory(mViewModel));
        mainIntentsTestRule.getActivity().setFragment(fragment);
        EspressoTestUtil.disableProgressBarAnimations(mainIntentsTestRule);

    }

    @Test
    public void displayDataTest() {
        mRecipes.postValue(Resource.success(fakeRecipes));
        onView(withId(R.id.recipe_rv)).check(matches(isVisible()));
        onView(withId(R.id.error_tv)).check(matches(not(isVisible())));
        onView(withId(R.id.retry_btn)).check(matches(not(isVisible())));
        onView(withId(R.id.loading_pb)).check(matches(not(isVisible())));
    }

    @Test
    public void showErrorTest() {
        mRecipes.postValue(Resource.<List<Recipe>>error("wtf", null));
        onView(withId(R.id.recipe_rv)).check(matches(not(isVisible())));
        onView(withId(R.id.error_tv)).check(matches(isVisible()));
        onView(withId(R.id.retry_btn)).check(matches(isVisible()));
        onView(withId(R.id.loading_pb)).check(matches(not(isVisible())));
    }

    @Test
    public void retryButtonGetData() {
        //Check is error message and button are displayed
        showErrorTest();

        //Click do nothing and return success
        onView(withId(R.id.retry_btn)).perform(click());
        doNothing().when(mViewModel).reFetchData();

        //Check if new data are correctly displayed
        displayDataTest();

    }

    @Test
    public void retryButtonGetError() {
        showErrorTest();
        onView(withId(R.id.retry_btn)).perform(click());
        doNothing().when(mViewModel).reFetchData();
        showErrorTest();
    }


    @Test
    public void loading() {
        mRecipes.postValue(Resource.<List<Recipe>>loading(null));
        onView(withId(R.id.loading_pb)).check(matches(isVisible()));
        onView(withId(R.id.retry_btn)).check(matches(not(isVisible())));
        onView(withId(R.id.error_tv)).check(matches(not(isVisible())));
        onView(withId(R.id.recipe_rv)).check(matches(not(isVisible())));
    }

    @Test
    public void errorNetworkResultWithData() {
        mRecipes.postValue(Resource.error("wtf", fakeRecipes));
        onView(withId(R.id.recipe_rv)).check(matches(isVisible()));
        onView(withId(R.id.error_tv)).check(matches(not(isVisible())));
        onView(withId(R.id.retry_btn)).check(matches(not(isVisible())));
        onView(withId(R.id.loading_pb)).check(matches(not(isVisible())));
    }

    @Test
    public void checkSentIntentDataTest() {
        displayDataTest();
        final Recipe recipe = fakeRecipes.get(CURRENT_POSITION);
        onView(withId(R.id.recipe_rv))
                .perform(RecyclerViewActions.<RecipeAdapter.RecipeViewHolder>scrollToPosition(CURRENT_POSITION))
                .perform(RecyclerViewActions.<RecipeAdapter.RecipeViewHolder>actionOnItemAtPosition(CURRENT_POSITION, click()));
        intended(allOf(hasExtra(BundleHelper.RECIPE_ID, recipe.getId())));
    }

    @Test
    public void checkDataValueOfRecyclerViewAtPosition() {
        displayDataTest();
        final Recipe recipe = fakeRecipes.get(CURRENT_POSITION);

        onView(allOf(withId(R.id.recipe_rv)))
                .perform(RecyclerViewActions.<RecipeAdapter.RecipeViewHolder>scrollToPosition(CURRENT_POSITION))
                .check(matches(TestUtil.atPosition(CURRENT_POSITION, allOf(hasDescendant(withText(recipe.getName())), hasDescendant(withText(recipe.getServings() + " portions"))))));

    }


    private Matcher<View> isVisible(){
        return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
    }


    public static class TestMainActivityFragment extends MainActivityFragment {

    }


}
