package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeDetailFragment;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;

public class RecipeMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);

        Intent intent = getIntent();
        if ((intent != null && intent.hasExtra(BundleHelper.RECIPE_ID))) {
            int value = intent.getIntExtra(BundleHelper.RECIPE_ID, -1);
            if (value == -1) {
                return;
            }

            //Load detail fragment which is part of master-detail layout
            FrameLayout frameLayout = findViewById(R.id.fragmentDetail);

            final FragmentManager manager = getSupportFragmentManager();
            final RecipeMasterFragment fragment = new RecipeMasterFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt(BundleHelper.RECIPE_ID, value);

            //If there is master-detail layout
            if (frameLayout != null) {
                bundle.putBoolean(BundleHelper.TABLE_VERSION, true);
            }
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragmetMaster, fragment).commit();

            //If there is a master-detail layout
            if (frameLayout != null) {
                RecipeDetailFragment detailFragment = new RecipeDetailFragment();
                manager.beginTransaction().replace(R.id.fragmentDetail, detailFragment).commit();
            }


        }


    }
}
