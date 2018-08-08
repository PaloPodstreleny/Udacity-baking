package com.project.podstreleny.pavol.baking.ui.recipieDetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String LOG = RecipeDetailFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ButterKnife.bind(this,view);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Check if Fragment contains some arguments -- Arguments are sut just when we are using mobile version of app
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(Intent.EXTRA_TEXT))
        {

            // MobileVersion
            final RecipeStep step = bundle.getParcelable(Intent.EXTRA_TEXT);
            setView(step);
        }else {
            // Table version
            final RecipeDetailViewModel viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
            viewModel.getActualRecipeStep().observe(this, new Observer<RecipeStep>() {
                @Override
                public void onChanged(@Nullable RecipeStep recipeStep) {
                    if(recipeStep != null){
                        Log.v(RecipeDetailFragment.class.getSimpleName(),"Everything is good +"+recipeStep.getShortDescription());
                    }
                }
            });
        }



    }

    private void setView(RecipeStep step){

    }
}
