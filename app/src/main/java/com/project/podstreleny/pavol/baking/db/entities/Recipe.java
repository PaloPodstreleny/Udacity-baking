package com.project.podstreleny.pavol.baking.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

@Entity(tableName = "recipe")
public class Recipe {

    private int id;

    @ColumnInfo(name = "recipe_name")
    private String name;

    @ColumnInfo(name = "recipe_servings")
    private int servings;

    @Ignore
    private List<RecipeIngredients> ingredients;

    @Ignore
    private List<RecipeStep> steps;

    @ColumnInfo(name = "recipe_image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getServings() {
        return servings;
    }


    public void setServings(int servings) {
        this.servings = servings;
    }

    @Ignore
    public List<RecipeIngredients> getIngredients() {
        return ingredients;
    }

    @Ignore
    public void setIngredients(List<RecipeIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Ignore
    public List<RecipeStep> getSteps() {
        return steps;
    }

    @Ignore
    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
