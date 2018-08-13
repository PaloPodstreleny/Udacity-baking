package com.project.podstreleny.pavol.baking.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.project.podstreleny.pavol.baking.R;

public class MainActivity extends AppCompatActivity {

    public static final String HAS_EXTRA = "has_extra";
    private static final String FLAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState != null){
            return;
        }

        final MainActivityFragment fragment = new MainActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,fragment,FLAG).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(HAS_EXTRA,true);
    }
}
