package com.project.podstreleny.pavol.baking.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe ")
    LiveData<List<Recipe>> getRecipies();

    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    LiveData<List<RecipeIngredients>> getAllIngredientsByRecipeID(int id);

    @Query("SELECT * FROM recipe_step WHERE recipe_id = :id ORDER BY id DESC")
    LiveData<List<RecipeStep>> getAllRecipesStepsByRecipeID(int id);

}
