package com.project.podstreleny.pavol.baking.ui.recipieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private static final String STEP = "Step";
    private static final int OFFSET = 1;
    private List<RecipeStep> steps;

    public TabsPagerAdapter(FragmentManager manager, List<RecipeStep> recipeSteps){
        super(manager);
        this.steps = recipeSteps;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(Intent.EXTRA_TEXT,steps.get(position));
        final RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return STEP + position + OFFSET;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return steps.size();
    }
}
