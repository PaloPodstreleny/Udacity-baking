package com.project.podstreleny.pavol.baking.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.List;

@Dao
public interface RecipeDao {

    @Update
    void updateRecipe(Recipe recipe);

    @Query("SELECT * FROM ingredients WHERE ingredients.recipe_id = :id")
    List<RecipeIngredients> getListOfIngredientsFromID(int id);

    @Query("SELECT recipe.id FROM recipe ORDER BY last_visit DESC LIMIT 1 ")
    int getLastRecipeID();

    @Query("SELECT * FROM recipe ")
    LiveData<List<Recipe>> getRecipies();

    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    LiveData<List<RecipeIngredients>> getAllIngredientsByRecipeID(int id);

    @Query("SELECT * FROM recipe_step WHERE recipe_id = :id ORDER BY id")
    LiveData<List<RecipeStep>> getAllRecipesStepsByRecipeID(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllRecipies(List<Recipe> recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllIngredients(List<RecipeIngredients> ingredients);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllSteps(List<RecipeStep> steps);

}
