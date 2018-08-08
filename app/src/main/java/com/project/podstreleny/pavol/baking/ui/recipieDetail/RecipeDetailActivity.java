package com.project.podstreleny.pavol.baking.ui.recipieDetail;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            Bundle bundle  = intent.getBundleExtra(Intent.EXTRA_TEXT);
            ArrayList<RecipeStep> list = bundle.getParcelableArrayList(Intent.EXTRA_TEXT);
            PagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(),list);
            mViewPager.setAdapter(pagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }







    }


}
