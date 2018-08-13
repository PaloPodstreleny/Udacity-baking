package com.project.podstreleny.pavol.baking;

import android.arch.lifecycle.ViewModel;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeDetailActivity;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailRecipeInstrumentedTest {




    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    private RecipeDetailViewModel viewModel;


    @Before
    public void init(){
//        viewModel = mock();
        //TODO finish tomorrow
        // viewModel = mock()
    }




}
