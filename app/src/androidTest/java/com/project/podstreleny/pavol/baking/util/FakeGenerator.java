package com.project.podstreleny.pavol.baking.util;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class FakeGenerator {
    public static List<Recipe> generateFakeRecipes(int numberOfRecipies){
        final ArrayList<Recipe> list = new ArrayList<>();
        for (int x = 0; x <numberOfRecipies; x++){
            list.add(new Recipe(x,"Recipe "+x,8,""));
        }
        return list;
    }

    public static List<RecipeIngredients> generateFakeIngredients(int numberOfIngredients, final int recipeID){
        final ArrayList<RecipeIngredients> list = new ArrayList<>();
        for (int x = 0; x < numberOfIngredients; x++){
            list.add(new RecipeIngredients(x,1.0f,"CUP","This is some text",recipeID));
        }
        return list;
    }

    public static List<RecipeStep> generateFakeSteps(int numberOfSteps, final int recipeID){
        final ArrayList<RecipeStep> list = new ArrayList<>();
        for (int x = 0; x < numberOfSteps; x++){
            list.add(new RecipeStep(x,"Short description","Long description","This is some url","String",recipeID));
        }
        return list;
    }
}
