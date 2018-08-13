package com.project.podstreleny.pavol.baking;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.podstreleny.pavol.baking.db.BakingDatabase;
import com.project.podstreleny.pavol.baking.db.dao.RecipeDao;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.utils.BundleHelper;

import java.util.List;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplication());
    }
}


class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private BakingDatabase mDatabase;
    private List<RecipeIngredients> recipeIngredients;
    private Context context;

    public GridRemoteViewsFactory(Application application) {
        mDatabase = BakingDatabase.getDatabaseInstance(application);
        context = application;
    }


    @Override
    public void onCreate() {

    }

    //Caled on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        //Get data
        if (mDatabase != null) {
            final RecipeDao dao = mDatabase.recipeDao();
            int getLastRecipeID = dao.getLastRecipeID();
            recipeIngredients = dao.getListOfIngredientsFromID(getLastRecipeID);

        }
    }

    @Override
    public void onDestroy() {
        recipeIngredients = null;
    }

    @Override
    public int getCount() {
        if (recipeIngredients == null) {
            return 0;
        } else {
            return recipeIngredients.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (recipeIngredients == null || recipeIngredients.size() == 0) return null;

        final RecipeIngredients ingredient = recipeIngredients.get(i);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bakind_widget_list_item);

        //Set TextView
        remoteViews.setTextViewText(R.id.ingredients_summary_tvv, ingredient.getIngredientSummary());

        Bundle extras = new Bundle();
        extras.putInt(BundleHelper.RECIPE_ID, ingredient.getRecipeID());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.ingredients_summary_tvv, fillInIntent);
        return remoteViews;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
