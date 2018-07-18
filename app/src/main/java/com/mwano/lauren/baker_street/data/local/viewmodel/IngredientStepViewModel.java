package com.mwano.lauren.baker_street.data.local.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.model.Recipe;

public class IngredientStepViewModel  extends ViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // Cache selected Recipe
    public LiveData<Recipe> mSingleRecipe;
    // Cache Recipe name
    private String mRecipeName;
    private int mRecipeId;

    private static final String TAG = IngredientStepViewModel.class.getSimpleName();

    // Constructor with a reference to the repository, getting the Selected recipe from the repository
    public IngredientStepViewModel(RecipeRepository repository, int recipeId) {
        mRecipeId = recipeId;
        mRepository = repository;
        mSingleRecipe = mRepository.loadSingleRecipeById(mRecipeId);
        Log.d(TAG, "Single Recipe is: " + mSingleRecipe);
    }

    // Get single Recipe by its id
    public LiveData<Recipe> getSingleRecipe() {
        return mSingleRecipe;
    }

    // Set Recipe
    public void setSingleRecipe(LiveData<Recipe> recipe) {
        mSingleRecipe = recipe;
    }

    // Get Recipe name
    public String getRecipeName(Recipe recipe) {
        mRecipeName = recipe.getName();
        return mRecipeName;
    }
}