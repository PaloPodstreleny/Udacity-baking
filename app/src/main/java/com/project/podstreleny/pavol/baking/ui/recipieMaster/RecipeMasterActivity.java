package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeDetailFragment;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;

public class RecipeMasterActivity extends AppCompatActivity {

    private int actualPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);

        if(savedInstanceState != null && savedInstanceState.containsKey(BundleHelper.ACTUAL_POSITION)){
            Log.v(RecipeMasterFragment.class.getSimpleName(),"Great!");
            final int actualPosition = savedInstanceState.getInt(BundleHelper.ACTUAL_POSITION);

        }



        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            int value = intent.getIntExtra(Intent.EXTRA_TEXT,-1);
            if(value == -1){
                return;
            }

            FrameLayout frameLayout = findViewById(R.id.fragmentDetail);



            final FragmentManager manager = getSupportFragmentManager();
            final RecipeMasterFragment fragment = new RecipeMasterFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt("ID",value);
            if(frameLayout != null){
                bundle.putBoolean(BundleHelper.TABLE_VERSION,true);
            }
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragmetMaster,fragment).commit();

            if(frameLayout != null){
                RecipeDetailFragment detailFragment = new RecipeDetailFragment();
                manager.beginTransaction().replace(R.id.fragmentDetail,detailFragment).commit();
            }


        }


    }
}
