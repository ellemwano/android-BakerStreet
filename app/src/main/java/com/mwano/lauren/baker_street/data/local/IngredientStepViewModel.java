
package com.mwano.lauren.baker_street.data.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

public class IngredientStepViewModel  extends AndroidViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // Variable to cache the selected Recipe
    private LiveData<Recipe> mObservableSingleRecipe;
    // Cache Ingredients list
    private LiveData<List<Ingredient>> mObservableIngredientsList;

    // Constructor with a reference to the repository, getting the Selected recipe from the repository
    public IngredientStepViewModel(@NonNull Application application, int recipeId) {
        super(application);
        mRepository = new RecipeRepository(application);
        mObservableSingleRecipe = mRepository.loadRecipeById(recipeId);
    }

    // Get single Recipe by its id
    public LiveData<Recipe> getSingleRecipe(int recipeId) {
        mRepository.loadRecipeById(recipeId);
        return mObservableSingleRecipe;
    }

    // Get Recipe's ingredients
    public LiveData<List<Ingredient>> getRecipeIngredients(int recipeId) {
        mRepository.loadIngredientsForRecipe(recipeId);
        return mObservableIngredientsList;
    }

    // Set Ingredients
    public void setRecipeIngredients (LiveData<List<Ingredient>> ingredients) {
        mObservableIngredientsList = ingredients;
    }


    // in Main activity, create intent to Master passing in the recipeId
    // Here, create getters / setters for all ingredients and steps fields
    // add a getname method (for title and widget later)
    // In main Activity, modify intent passing in only recipeId
    // in DAO, create new query, recipe by id
    // ad  to repository
}
