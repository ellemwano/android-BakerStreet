package com.mwano.lauren.baker_street.data.local.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // LiveData variable to cache the list of Recipes
    private LiveData<List<Recipe>> mObservableRecipes;
    private static boolean internetState = false;

    // Constructor with a reference to the repository, getting the list of recipes from the repository
    public RecipeViewModel(RecipeRepository repository) {
        mRepository = repository;
        mObservableRecipes = mRepository.getRecipeList(internetState);
    }

    public LiveData<List<Recipe>> getRecipeList() {
        mRepository.getRecipeList(internetState);
        return mObservableRecipes;
    }

    // Getter for all recipes
    public LiveData<List<Recipe>> getObservableRecipes() {
        return mObservableRecipes;
    }

    public void setInternetState(boolean internetState) {
        this.internetState = internetState;
    }

    // Delete all recipes
    public void deleteAllRecipes() {
        mRepository.deleteAllRecipesFromDb();
    }
}




