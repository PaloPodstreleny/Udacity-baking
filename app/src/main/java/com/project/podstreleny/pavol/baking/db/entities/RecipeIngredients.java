package com.project.podstreleny.pavol.baking.db.entities;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "ingredients",
        foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id",childColumns = "recipe_id"),
        indices = @Index("recipe_id"))
public class RecipeIngredients {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private float quantity;
    private String measure;
    private String ingredient;

    @ColumnInfo(name = "recipe_id")
    private int recipeID;


    public RecipeIngredients(){

    }

    @Ignore
    public RecipeIngredients(int id, float quantity, String measure, String ingredient, int recipeID) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeID = recipeID;
    }

    @Ignore
    public String getIngredientSummary(){
        return ""+getQuantity()+" "+getMeasure()+" of " + getIngredient();
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
