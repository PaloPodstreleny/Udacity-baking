package com.project.podstreleny.pavol.baking.ui.main;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.service.Resource;
import com.project.podstreleny.pavol.baking.ui.recipieMaster.RecipeMasterActivity;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;
import com.project.podstreleny.pavol.baking.viewModels.RecipeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {

    @BindView(R.id.recipe_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_pb)
    ProgressBar mProgressBar;

    @BindView(R.id.error_tv)
    TextView mErrorTextView;

    @BindView(R.id.retry_btn)
    Button mRetryButton;

    private ViewModelProvider.Factory factory;
    private RecipeViewModel viewModel;
    private RecipeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int SPAN_COUNT = getResources().getInteger(R.integer.span_count);

        mAdapter = new RecipeAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        //Get ViewModel
        viewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);
        viewModel.fetchData();
        viewModel.getRecipes().observe(this, new Observer<Resource<List<Recipe>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Recipe>> listResource) {
                if (listResource != null) {
                    List<Recipe> recipies = listResource.getData();
                    switch (listResource.getStatus()) {
                        case LOADING:
                            changeProgressBarVisibility(View.VISIBLE);
                            changeErrorTextVisibility(null);
                            changeRetryButtonVisibility(View.GONE);
                            changeRecyclerViewVisibility(View.GONE);
                            break;
                        case ERROR:
                            if (recipies != null && !recipies.isEmpty()) {
                                // showing data from db
                                successfulResponse(recipies);
                            } else {
                                changeRecyclerViewVisibility(View.GONE);
                                changeProgressBarVisibility(View.GONE);
                                changeRetryButtonVisibility(View.VISIBLE);
                                changeErrorTextVisibility(getResources().getString(R.string.no_internet_connection));
                            }
                            break;

                        case SUCCESS:
                            successfulResponse(recipies);
                            break;
                        default:
                            throw new IllegalArgumentException("Status can be just: ERROR,LOADING, SUCCESS");
                    }
                }
            }
        });

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.reFetchData();
            }
        });
    }

    private void successfulResponse(List<Recipe> recipies) {
        mAdapter.swapData(recipies);
        changeProgressBarVisibility(View.GONE);
        changeErrorTextVisibility(null);
        changeRetryButtonVisibility(View.GONE);
        changeRecyclerViewVisibility(View.VISIBLE);
    }

    private void changeProgressBarVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    private void changeRetryButtonVisibility(int visibility) {
        mRetryButton.setVisibility(visibility);
    }

    private void changeErrorTextVisibility(@Nullable String error) {
        if (error == null) {
            mErrorTextView.setVisibility(View.GONE);
        } else {
            mErrorTextView.setText(error);
            mErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    private void changeRecyclerViewVisibility(int visibility) {
        mRecyclerView.setVisibility(visibility);
    }


    @VisibleForTesting
    public void setViewModerProvider(ViewModelProvider.Factory factory) {
        this.factory = factory;
    }

    @Override
    public void onClick(@NonNull Recipe recipe) {
        viewModel.updateRecipeTimeStamp(recipe);
        Intent intent = new Intent(getActivity(), RecipeMasterActivity.class);
        intent.putExtra(BundleHelper.RECIPE_ID, recipe.getId());
        startActivity(intent);
    }
}
