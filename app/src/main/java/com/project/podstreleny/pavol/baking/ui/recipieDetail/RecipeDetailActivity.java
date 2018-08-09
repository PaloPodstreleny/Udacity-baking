package com.project.podstreleny.pavol.baking.ui.recipieDetail;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);


            final int position = bundle.getInt(BundleHelper.ACTUAL_POSITION);
            final ArrayList<RecipeStep> list = bundle.getParcelableArrayList(BundleHelper.LIST_OF_STEPS);


            PagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), list);
            mViewPager.setAdapter(pagerAdapter);
            mViewPager.setCurrentItem(position);

            //Load by findViewByID because of checking landscape layout
            mTabLayout = findViewById(R.id.tab_layout);

            //Check if tablayout exists
            if (mTabLayout != null) {
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }


    }


}
