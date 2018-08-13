package com.project.podstreleny.pavol.baking.viewModels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.repositories.RecipeRepository;
import com.project.podstreleny.pavol.baking.service.Resource;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private LiveData<Resource<List<Recipe>>> cachedData;
    private MutableLiveData<Boolean> fetcher = new MutableLiveData<>();
    public LiveData<Resource<List<Recipe>>> recipes = Transformations.switchMap(fetcher, new Function<Boolean, LiveData<Resource<List<Recipe>>>>() {
        @Override
        public LiveData<Resource<List<Recipe>>> apply(Boolean input) {
            if(input){
                return mRepository.getRecepies();
            }
            if(cachedData == null){
                cachedData = mRepository.getRecepies();
            }
            return cachedData;
        }
    });

    public void updateActuallyLookingRecipe(Recipe recipe){
        mRepository.setActualRecipe(recipe);
    }

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = RecipeRepository.getInstance(application);
    }

    public void fetchData(){
        fetcher.setValue(false);
    }

    public void reFatchData(){
        fetcher.setValue(true);
    }

    public LiveData<Resource<List<Recipe>>> getRecipes(){
        return recipes;
    }




}
