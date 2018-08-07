package com.project.podstreleny.pavol.baking.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.model.IRecipe;
import com.project.podstreleny.pavol.baking.repositories.RecipeRepository;
import com.project.podstreleny.pavol.baking.service.Resource;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    public final LiveData<Resource<List<Recipe>>> recipes;

    private RecipeRepository mRepository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = RecipeRepository.getInstance(application);
        recipes = mRepository.getRecepies();
    }


}
