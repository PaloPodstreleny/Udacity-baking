package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeDetailActivity;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMasterFragment extends Fragment implements AdapterSteps.OnRecipeStepClickListener {

    @BindView(R.id.ingredients_rv)
    RecyclerView mRecyclerViewIngredients;

    @BindView(R.id.steps_rv)
    RecyclerView mRecyclerViewSteps;

    private AdapterIngredients mAdapterIngredients;
    private AdapterSteps mAdapterSteps;
    private Integer movieID;
    private RecipeDetailViewModel viewModel;

    private boolean isMobile = true;
    private Integer mPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_master, container, false);
        ButterKnife.bind(this, view);

        //Get data from saveInstanceState -- rotation on Tablet
        if (savedInstanceState != null && savedInstanceState.containsKey(BundleHelper.ACTUAL_POSITION)) {
            mPosition = savedInstanceState.getInt(BundleHelper.ACTUAL_POSITION);
        }

        final Bundle bundle = getArguments();
        if (bundle != null) {
            movieID = getArguments().getInt(BundleHelper.RECIPE_ID);
            if (bundle.containsKey(BundleHelper.TABLE_VERSION)) {
                isMobile = false;
            } else {
                isMobile = true;
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapterIngredients = new AdapterIngredients();
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewIngredients.setHasFixedSize(true);
        mRecyclerViewIngredients.setAdapter(mAdapterIngredients);

        mAdapterSteps = new AdapterSteps(this);
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setAdapter(mAdapterSteps);

        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);

        if (movieID != null) {
            viewModel.setMovieID(movieID);
        }

        viewModel.ingredients.observe(this, new Observer<List<RecipeIngredients>>() {
            @Override
            public void onChanged(@Nullable List<RecipeIngredients> ingredients) {
                if (ingredients != null && !ingredients.isEmpty()) {
                    mAdapterIngredients.swapData(ingredients);
                }
            }
        });

        viewModel.steps.observe(getActivity(), new Observer<List<RecipeStep>>() {
            @Override
            public void onChanged(@Nullable List<RecipeStep> recipeSteps) {
                if (recipeSteps != null && !recipeSteps.isEmpty()) {
                    mAdapterSteps.swapData((ArrayList) recipeSteps);
                    if (!isMobile && mPosition != null) {
                        viewModel.setActualStep(recipeSteps.get(mPosition));
                    }
                }
            }
        });

    }

    @Override
    public void onClick(RecipeStep recipeStep, int position) {
        mPosition = position;
        if (isMobile) {
            //Code for mobile version
            final Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
            final Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(BundleHelper.LIST_OF_STEPS, mAdapterSteps.getSteps());
            bundle.putInt(BundleHelper.ACTUAL_POSITION, position);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        } else {
            //Code for table version
            viewModel.setActualStep(recipeStep);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (!isMobile && mPosition != null) {
            outState.putInt(BundleHelper.ACTUAL_POSITION, mPosition);
        }
        super.onSaveInstanceState(outState);

    }
}
