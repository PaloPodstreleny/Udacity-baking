package com.project.podstreleny.pavol.baking.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.project.podstreleny.pavol.baking.AppExecutor;
import com.project.podstreleny.pavol.baking.db.BakingDatabase;
import com.project.podstreleny.pavol.baking.db.dao.RecipeDao;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.service.BakingEndPoint;
import com.project.podstreleny.pavol.baking.service.NetworkBoundResource;
import com.project.podstreleny.pavol.baking.service.Resource;
import com.project.podstreleny.pavol.baking.service.responses.ApiResponse;
import com.project.podstreleny.pavol.baking.service.retrofit.RetrofitProvider;

import java.util.List;

public class RecipeRepository {

    private static final String LOG = RecipeRepository.class.getSimpleName();
    private static RecipeRepository INSTANCE;

    private BakingDatabase mDatabase;
    private RecipeDao mRecipeDao;
    private BakingEndPoint mBakingEndpoint;
    private AppExecutor mExecutor;

    private RecipeRepository(BakingDatabase database, BakingEndPoint bakingEndPoint, AppExecutor executor){
        mDatabase = database;
        mRecipeDao = database.recipeDao();
        mBakingEndpoint = bakingEndPoint;
        mExecutor = executor;
    }

    public static RecipeRepository getInstance(Application application){
        if(INSTANCE == null){
            synchronized (RecipeRepository.class) {
                INSTANCE = new RecipeRepository(
                        BakingDatabase.getDatabaseInstance(application),
                        RetrofitProvider.getService(BakingEndPoint.class),
                        new AppExecutor()
                );
            }
        }
        return INSTANCE;
    }

    public LiveData<Resource<List<Recipe>>> getRecepies(){

        return new NetworkBoundResource<List<Recipe>,List<Recipe>>(mExecutor){
            @Override
            protected void saveCallResult(@NonNull List<Recipe> item) {
                mDatabase.beginTransaction();
                mRecipeDao.insertAllRecipies(item);
                for (Recipe r : item){

                    for (RecipeIngredients ingredients : r.getIngredients()){
                        ingredients.setRecipeID(r.getId());
                    }

                    for (RecipeStep step : r.getSteps()){
                        step.setRecipeID(r.getId());
                    }

                    mRecipeDao.insertAllIngredients(r.getIngredients());
                    mRecipeDao.insertAllSteps(r.getSteps());
                }

                mDatabase.setTransactionSuccessful();
                mDatabase.endTransaction();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
               return  (data == null || data.isEmpty());
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                return mRecipeDao.getRecipies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {
                return mBakingEndpoint.getAllRecipies();
            }

            @Override
            protected void onFetchFailed() {
                Log.e(LOG,"Problem with fetching data!");
            }


        }.getAsLiveData();
    }



}
