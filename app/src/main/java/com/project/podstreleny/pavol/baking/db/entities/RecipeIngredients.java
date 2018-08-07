package com.project.podstreleny.pavol.baking.db.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;


@Entity(tableName = "ingredients",
        foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id",childColumns = "recipeID"),
        indices = @Index("recipeID"))
public class RecipeIngredients {

    private int quantity;
    private String measure;
    private String ingredient;

    @ColumnInfo(name = "recipe_id")
    private int recipeID;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
