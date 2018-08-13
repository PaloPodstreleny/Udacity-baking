package com.project.podstreleny.pavol.baking.ui;


import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.SingleFragmentActivityTestPurpose;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.ui.recipieMaster.RecipeDetailFragment;
import com.project.podstreleny.pavol.baking.util.EspressoTestUtil;
import com.project.podstreleny.pavol.baking.util.FakeGenerator;
import com.project.podstreleny.pavol.baking.util.ViewModelFactoryProvider;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MasterFragmentTest {

    private static final int RECIPE_ID = 1;
    @Rule
    public IntentsTestRule<SingleFragmentActivityTestPurpose> singleFragmentActivityActivityTestRule = new IntentsTestRule<>(SingleFragmentActivityTestPurpose.class);


    private RecipeDetailViewModel mViewModel;
    private final MutableLiveData<List<RecipeStep>> mRecipeSteps = new MutableLiveData<>();
    private final MutableLiveData<List<RecipeIngredients>> mRecipeIngredients = new MutableLiveData<>();

    private List<RecipeIngredients> fakeIngredients;
    private ArrayList<RecipeStep> fakeSteps;


    @Before
    public void init() {
        intending(Matchers.not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        //Mock ViewModel
        mViewModel = mock(RecipeDetailViewModel.class);
        when(mViewModel.getSteps()).thenReturn(mRecipeSteps);
        when(mViewModel.getIngredients()).thenReturn(mRecipeIngredients);

        //Create fake response
        fakeIngredients = new ArrayList<>();
        fakeSteps = new ArrayList<>();

        fakeIngredients.addAll(FakeGenerator.generateFakeIngredients(10,RECIPE_ID));
        fakeSteps.addAll(FakeGenerator.generateFakeSteps(10,RECIPE_ID));

        final TestDetailDetailFragment fragment = new TestDetailDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(BundleHelper.RECIPE_ID,RECIPE_ID);
        fragment.setArguments(bundle);
        fragment.setFactory(ViewModelFactoryProvider.getViewModelFactory(mViewModel));
        singleFragmentActivityActivityTestRule.getActivity().setFragment(fragment);
        EspressoTestUtil.disableProgressBarAnimations(singleFragmentActivityActivityTestRule);


    }

    @Test
    public void checkVisibilityOfData(){
        mRecipeSteps.postValue(fakeSteps);
        mRecipeIngredients.postValue(fakeIngredients);
        onView(withId(R.id.steps_rv)).check(matches(isVisible()));
        onView(withId(R.id.ingredients_rv)).check(matches(isVisible()));


    }

    @Test
    public void checkVisibilityOfNoData(){
        mRecipeSteps.postValue(null);
        mRecipeIngredients.postValue(null);
        onView(withId(R.id.steps_rv)).check(matches(not(isVisible())));
        onView(withId(R.id.ingredients_rv)).check(matches(not(isVisible())));
    }



    private Matcher<View> isVisible(){
        return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
    }



    public static class TestDetailDetailFragment extends RecipeDetailFragment {

    }



}
