package com.project.podstreleny.pavol.baking.repositories;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.project.podstreleny.pavol.baking.AppExecutor;
import com.project.podstreleny.pavol.baking.BakingWidgetProvider;
import com.project.podstreleny.pavol.baking.R;
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

import java.util.GregorianCalendar;
import java.util.List;

public class RecipeRepository {

    private static final String LOG = RecipeRepository.class.getSimpleName();
    private static RecipeRepository INSTANCE;
    private final Context context;

    private BakingDatabase mDatabase;
    private RecipeDao mRecipeDao;
    private BakingEndPoint mBakingEndpoint;
    private AppExecutor mExecutor;

    private RecipeRepository(Context context, BakingDatabase database, BakingEndPoint bakingEndPoint, AppExecutor executor){
        this.context = context;
        mDatabase = database;
        mRecipeDao = database.recipeDao();
        mBakingEndpoint = bakingEndPoint;
        mExecutor = executor;
    }

    public static RecipeRepository getInstance(Application application){
        if(INSTANCE == null){
            synchronized (RecipeRepository.class) {
                INSTANCE = new RecipeRepository(
                        application,
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
                    r.setLastVisit(System.currentTimeMillis());

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


    public void setActualRecipe(final Recipe recipe){
        mExecutor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                recipe.setLastVisit(System.currentTimeMillis());
                mRecipeDao.updateRecipe(recipe);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));
                manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_gv);
                BakingWidgetProvider.onUpdateWidget(context,manager,appWidgetIds);
            }
        });

    }



}
