package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMasterFragment extends Fragment {

    @BindView(R.id.ingredients_rv)
    RecyclerView mRecyclerViewIngredients;

    @BindView(R.id.steps_rv)
    RecyclerView mRecyclerViewSteps;

    private AdapterIngredients mAdapterIngredients;
    private AdapterSteps mAdapterSteps;
    private Integer movieID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail_master,container,false);
        ButterKnife.bind(this,view);
        movieID = getArguments().getInt("ID");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapterIngredients = new AdapterIngredients();
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewIngredients.setHasFixedSize(true);
        mRecyclerViewIngredients.setAdapter(mAdapterIngredients);

        mAdapterSteps = new AdapterSteps();
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setAdapter(mAdapterSteps);

        RecipeDetailViewModel viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);

        if(movieID != null){
            viewModel.setMovieID(movieID);
        }

        viewModel.ingredients.observe(this, new Observer<List<RecipeIngredients>>() {
            @Override
            public void onChanged(@Nullable List<RecipeIngredients> ingredients) {
                if (ingredients != null && !ingredients.isEmpty()){
                    mAdapterIngredients.swapData(ingredients);
                }else {
                    //No ingrediets
                }
            }
        });

        viewModel.steps.observe(this, new Observer<List<RecipeStep>>() {
            @Override
            public void onChanged(@Nullable List<RecipeStep> recipeSteps) {
                if (recipeSteps != null && !recipeSteps.isEmpty()){
                    mAdapterSteps.swapData(recipeSteps);
                }else {
                    //No igredients
                }
            }
        });

    }
}
