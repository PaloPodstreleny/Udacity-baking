package com.project.podstreleny.pavol.baking;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeStepDetailActivity;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailRecipeInstrumentedTest {




    @Rule
    public ActivityTestRule<RecipeStepDetailActivity> activityActivityTestRule = new ActivityTestRule<>(RecipeStepDetailActivity.class);

    private RecipeDetailViewModel viewModel;


    @Before
    public void init(){
//        viewModel = mock();
        //TODO finish tomorrow
        // viewModel = mock()
    }




}
