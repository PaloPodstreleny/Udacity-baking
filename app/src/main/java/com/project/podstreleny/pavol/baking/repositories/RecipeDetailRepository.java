package com.project.podstreleny.pavol.baking.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.project.podstreleny.pavol.baking.db.BakingDatabase;
import com.project.podstreleny.pavol.baking.db.dao.RecipeDao;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.List;

public class RecipeDetailRepository {

    private static RecipeDetailRepository INSTANCE;

    private RecipeDao mRecipeDao;

    private RecipeDetailRepository(RecipeDao dao) {
        mRecipeDao = dao;
    }

    public static RecipeDetailRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (RecipeRepository.class) {
                INSTANCE = new RecipeDetailRepository(
                        BakingDatabase.getDatabaseInstance(application).recipeDao()
                );
            }
        }
        return INSTANCE;
    }

    public LiveData<List<RecipeIngredients>> getIngredientsByRecipeID(int id) {
        return mRecipeDao.getAllIngredientsByRecipeID(id);
    }

    public LiveData<List<RecipeStep>> getRecipeStepsByRecipeID(int id) {
        return mRecipeDao.getAllRecipesStepsByRecipeID(id);
    }


}
