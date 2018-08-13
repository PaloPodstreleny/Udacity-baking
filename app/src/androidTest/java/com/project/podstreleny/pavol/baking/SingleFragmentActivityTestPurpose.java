package com.project.podstreleny.pavol.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class SingleFragmentActivityTestPurpose extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment,fragment,"TEST").commit();
    }

    public void setFragmentWithBundle(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        setFragment(fragment);
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment,fragment).commit();
    }

}
