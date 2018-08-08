package com.project.podstreleny.pavol.baking.ui.mainPage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.model.IRecipe;
import com.project.podstreleny.pavol.baking.service.Resource;
import com.project.podstreleny.pavol.baking.viewModels.RecipeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_pb)
    ProgressBar mProgressBar;

    @BindView(R.id.error_tv)
    TextView mErrorTextView;

    @BindView(R.id.retry_btn)
    Button mRetryButton;

    private BasicRecipeDescriptionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final int SPAN_COUNT = getResources().getInteger(R.integer.span_count);


        mAdapter = new BasicRecipeDescriptionAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        //Get ViewModel
        final RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.recipes.observe(this, new Observer<Resource<List<Recipe>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Recipe>> listResource) {
                if(listResource != null){
                    List<Recipe> recipies = listResource.getData();
                    switch (listResource.getStatus()){
                        case LOADING:
                            changeProgressBarVisibility(View.VISIBLE);
                            changeErrorTextVisibility(null);
                            changeRetryButtonVisibility(View.GONE);
                            changeRecyclerViewVisibility(View.GONE);
                            break;
                        case ERROR:
                            if(recipies != null && !recipies.isEmpty()){
                                // showing data from db
                                succesfullResponse(recipies);
                            }else {
                                changeRecyclerViewVisibility(View.GONE);
                                changeProgressBarVisibility(View.GONE);
                                changeRetryButtonVisibility(View.VISIBLE);
                                changeErrorTextVisibility(getResources().getString(R.string.no_internet_connection));
                            }
                            break;

                        case SUCCESS:
                            succesfullResponse(recipies);
                            break;
                        default:
                            throw new IllegalArgumentException("Status can be just: ERROR,LOADING, SUCCESS");
                    }
                }
            }
        });




    }

    private void succesfullResponse(List<Recipe> recipies){
        mAdapter.swapData(recipies);
        changeProgressBarVisibility(View.GONE);
        changeErrorTextVisibility(null);
        changeRetryButtonVisibility(View.GONE);
        changeRecyclerViewVisibility(View.VISIBLE);
    }

    private void changeProgressBarVisibility(int visibility){
        mProgressBar.setVisibility(visibility);
    }

    private void changeRetryButtonVisibility(int visibility){
        mRetryButton.setVisibility(visibility);
    }

    private void changeErrorTextVisibility(@Nullable String error){
        if (error == null){
            mErrorTextView.setVisibility(View.GONE);
        }else {
            mErrorTextView.setText(error);
            mErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    private void changeRecyclerViewVisibility(int visibility){
        mRecyclerView.setVisibility(visibility);
    }
}
