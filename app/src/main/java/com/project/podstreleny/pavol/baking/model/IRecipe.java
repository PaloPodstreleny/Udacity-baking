package com.project.podstreleny.pavol.baking.model;

public interface IRecipe {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    int getServings();
    void setServings(int servings);
    String getImage();
    void setImage(String image);
    boolean hasImage();
}
