package com.project.podstreleny.pavol.baking.ui;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.SingleFragmentActivityTestPurpose;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.ui.recipeStepDetail.RecipeStepDetailFragment;
import com.project.podstreleny.pavol.baking.util.ViewModelFactoryProvider;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DetailFragmentTest {

    @Rule
    public IntentsTestRule<SingleFragmentActivityTestPurpose> singleFragmentActivityActivityTestRule = new IntentsTestRule<>(SingleFragmentActivityTestPurpose.class);

    private RecipeDetailViewModel mViewModel;
    private final MutableLiveData<RecipeStep> mRecipeSteps = new MutableLiveData<>();
    private final MutableLiveData<Long> mInitialStep = new MutableLiveData<>();

    private RecipeStep fakeStepImage;

    @Before
    public void init() {
        mViewModel = mock(RecipeDetailViewModel.class);
        when(mViewModel.getActualRecipeStep()).thenReturn(mRecipeSteps);
        when(mViewModel.getInitialSeek()).thenReturn(mInitialStep);
        fakeStepImage = mock(RecipeStep.class);
        FakeDetailTestFragment testFragment = new FakeDetailTestFragment();
        testFragment.setFactory(ViewModelFactoryProvider.getViewModelFactory(mViewModel));
        singleFragmentActivityActivityTestRule.getActivity().setFragment(testFragment);
    }

    @Test
    public void checkTextVisibility() {
        mRecipeSteps.postValue(fakeStepImage);
        when(fakeStepImage.getDescription()).thenReturn("Any string");
        onView(withId(R.id.description_tv)).check(ViewAssertions.matches(isVisible()));
        onView(withId(R.id.playerView)).check(ViewAssertions.matches(isGONE()));
        onView(withId(R.id.thumbnail_iv)).check(ViewAssertions.matches(isGONE()));
    }

    @Test
    public void checkVideoVisibility() {
        mRecipeSteps.postValue(fakeStepImage);
        when(fakeStepImage.hasVideoURL()).thenReturn(true);
        when(fakeStepImage.getVideoURL()).thenReturn("https://www.youtube.com/watch?v=-2922rYZSMg");
        when(fakeStepImage.getVideoUri()).thenReturn(Uri.parse("https://www.youtube.com/watch?v=-2922rYZSMg"));

        onView(withId(R.id.description_tv)).check(ViewAssertions.matches(isVisible()));
        onView(withId(R.id.playerView)).check(ViewAssertions.matches(isVisible()));
        onView(withId(R.id.thumbnail_iv)).check(ViewAssertions.matches(not(isVisible())));
    }


    @Test
    public void checkThumbnailVisibility() {
        mRecipeSteps.postValue(fakeStepImage);
        when(fakeStepImage.hasImage()).thenReturn(true);
        when(fakeStepImage.getThumbnailURL()).thenReturn("String");

        onView(withId(R.id.description_tv)).check(ViewAssertions.matches(isVisible()));
        onView(withId(R.id.playerView)).check(ViewAssertions.matches(not(isVisible())));
        onView(withId(R.id.thumbnail_iv)).check(ViewAssertions.matches(isVisible()));
    }

    private Matcher<View> isGONE() {
        return withEffectiveVisibility(ViewMatchers.Visibility.GONE);
    }

    private Matcher<View> isVisible() {
        return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
    }


    public static class FakeDetailTestFragment extends RecipeStepDetailFragment {

    }


}
