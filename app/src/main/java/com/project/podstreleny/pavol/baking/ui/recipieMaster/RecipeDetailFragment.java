package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.ui.recipieDetail.RecipeStepDetailActivity;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.OnRecipeStepClickListener {

    private static final String ACTUAL_POSITION = "ACTUAL_POSITION";

    @BindView(R.id.ingredients_rv)
    RecyclerView mRecyclerViewIngredients;

    @BindView(R.id.steps_rv)
    RecyclerView mRecyclerViewSteps;

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private Integer recipeID;
    private RecipeDetailViewModel viewModel;

    private boolean isMobile = true;
    private int mPosition;
    private ViewModelProvider.Factory factory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_master, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Get bundle from activity
        final Bundle bundle = getArguments();
        if (bundle != null) {
            recipeID = getArguments().getInt(BundleHelper.RECIPE_ID);
            // Check if there is tablet or mobile version
            isMobile = (!bundle.containsKey(BundleHelper.TABLE_VERSION));
        }

        //Get actual position from
        if (savedInstanceState != null && savedInstanceState.containsKey(ACTUAL_POSITION)) {
            mPosition = savedInstanceState.getInt(ACTUAL_POSITION);
        }

        mIngredientsAdapter = new IngredientsAdapter();
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewIngredients.setHasFixedSize(true);
        mRecyclerViewIngredients.setAdapter(mIngredientsAdapter);

        mStepsAdapter = new StepsAdapter(this);
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setAdapter(mStepsAdapter);

        viewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeDetailViewModel.class);

        if (recipeID != null) {
            viewModel.setMovieID(recipeID);
        }

        viewModel.getIngredients().observe(this, new Observer<List<RecipeIngredients>>() {
            @Override
            public void onChanged(@Nullable List<RecipeIngredients> ingredients) {
                if (ingredients != null && !ingredients.isEmpty()) {
                    mIngredientsAdapter.swapData(ingredients);
                }else {
                    mRecyclerViewIngredients.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getSteps().observe(getActivity(), new Observer<List<RecipeStep>>() {
            @Override
            public void onChanged(@Nullable List<RecipeStep> recipeSteps) {
                if (recipeSteps != null && !recipeSteps.isEmpty()) {
                    mStepsAdapter.swapData(recipeSteps);
                    viewModel.setActualStep(recipeSteps.get(mPosition));
                }else {
                    mRecyclerViewSteps.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onClick(RecipeStep recipeStep, int position) {
        mPosition = position;
        viewModel.setActualStep(recipeStep);
        if (isMobile) {
            //Code for mobile version
            final Intent intent = new Intent(getContext(), RecipeStepDetailActivity.class);
            final Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(BundleHelper.LIST_OF_STEPS, mStepsAdapter.getSteps());
            bundle.putInt(BundleHelper.ACTUAL_POSITION, position);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTUAL_POSITION, mPosition);
    }

    @VisibleForTesting
    public void setFactory(ViewModelProvider.Factory factory){
        this.factory = factory;
    }
}
