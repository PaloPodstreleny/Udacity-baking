package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.podstreleny.pavol.baking.R;

public class RecipeMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            int value = intent.getIntExtra(Intent.EXTRA_TEXT,-1);
            if(value == -1){
                return;
            }

            final FragmentManager manager = getSupportFragmentManager();
            final RecipeMasterFragment fragment = new RecipeMasterFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt("ID",value);
            fragment.setArguments(bundle);
            manager.beginTransaction().add(R.id.fragmetMaster,fragment).commit();

        }


    }
}
