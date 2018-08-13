package com.project.podstreleny.pavol.baking.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.project.podstreleny.pavol.baking.model.IRecipe;

import java.util.List;

@Entity(tableName = "recipe")
public class Recipe implements IRecipe {

    @PrimaryKey
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

    @ColumnInfo(name = "last_visit")
    private long lastVisit;

    public long getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(long lastVisit) {
        this.lastVisit = lastVisit;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasImage() {
        return image.length() > 0;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getServings() {
        return servings;
    }

    @Override
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

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Ignore
    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(){

    }
}
