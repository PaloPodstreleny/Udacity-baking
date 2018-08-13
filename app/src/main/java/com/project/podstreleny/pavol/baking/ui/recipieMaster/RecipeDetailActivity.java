package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.ui.recipeStepDetail.RecipeStepDetailFragment;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String HAS_EXTRA = "has_extra";
    private static final String MASTER_FLAG = "Master_flag";
    private static final String DETAIL_FLAG = "Detail_flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);

        if(savedInstanceState != null){
            return;
        }

        //Get intent from activity / widget provider
        Intent intent = getIntent();

        //If intent is null or intent does not have extra RECIPE_ID return from method;
        if(intent == null || !intent.hasExtra(BundleHelper.RECIPE_ID)){
            return;
        }

        final int value = intent.getIntExtra(BundleHelper.RECIPE_ID,-1);

        final FragmentManager manager = getSupportFragmentManager();
        final RecipeDetailFragment fragment = new RecipeDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(BundleHelper.RECIPE_ID, value);

        //Try to load detail fragment which is part of master-detail layout
        FrameLayout frameLayout = findViewById(R.id.fragmentDetail);

        //If there is detail fragment begin transaction and add TABLE_VERSION indicator to master bundle
        if (frameLayout != null) {
            bundle.putBoolean(BundleHelper.TABLE_VERSION, true);

            RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
            manager.beginTransaction().add(R.id.fragmentDetail, detailFragment,DETAIL_FLAG).commit();
        }
        fragment.setArguments(bundle);
        manager.beginTransaction().add(R.id.fragmetMaster, fragment,MASTER_FLAG).commit();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(HAS_EXTRA,true);
    }
}
