
package com.mwano.lauren.baker_street.data.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // LiveData variable to cache the list of Recipes
    private LiveData<List<Recipe>> mAllRecipes;

    // Constructor with a reference to the repository, getting the list of recipes from the repository
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }

    // Getter for all recipes
    LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }
}
