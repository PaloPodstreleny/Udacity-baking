package com.project.podstreleny.pavol.baking.viewModels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.repositories.RecipeDetailRepository;

import java.util.List;

public class RecipeDetailViewModel extends AndroidViewModel {

    private final RecipeDetailRepository mDetailRepository;
    private MutableLiveData<Integer> movieID = new MutableLiveData<>();

    public final LiveData<List<RecipeIngredients>> ingredients = Transformations.switchMap(movieID, new Function<Integer, LiveData<List<RecipeIngredients>>>() {
        @Override
        public LiveData<List<RecipeIngredients>> apply(Integer input) {
            return mDetailRepository.getIngredientsByRecipeID(input);
        }
    });

    public final LiveData<List<RecipeStep>> steps = Transformations.switchMap(movieID, new Function<Integer, LiveData<List<RecipeStep>>>() {
        @Override
        public LiveData<List<RecipeStep>> apply(Integer input) {
            return mDetailRepository.getRecipeStepsByRecipeID(input);
        }
    });

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
        mDetailRepository = RecipeDetailRepository.getInstance(application);
    }

    public void setMovieID(int id){
        movieID.setValue(id);
    }



}
