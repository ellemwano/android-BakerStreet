
package com.mwano.lauren.baker_street.data.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class IngredientStepViewModel  extends ViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // Cache selected Recipe
    private LiveData<Recipe> mSingleRecipe;
    // Cache Ingredients list
    private List<Ingredient> mIngredientsList;
    // Cache Steps list
    private List<Step> mStepsList;
    // Cache Recipe name
    private String mRecipeName;

    private static final String TAG = IngredientStepViewModel.class.getSimpleName();

    // Constructor with a reference to the repository, getting the Selected recipe from the repository
    public IngredientStepViewModel(RecipeRepository repository, int recipeId) {
        mRepository = repository;
        mSingleRecipe = mRepository.loadSingleRecipeById(recipeId);
        Log.d(TAG, "Single Recipe is: " + mSingleRecipe);
        // Single Recipe is null
    }

    // Get single Recipe by its id
    public LiveData<Recipe> getSingleRecipe() {
        return mSingleRecipe;
    }

    // Set Recipe
    public void setSingleRecipe(LiveData<Recipe> singleRecipe) {
        mSingleRecipe = singleRecipe;
    }

    // Get name
    public String getRecipeName(Recipe recipe) {
        mRecipeName = recipe.getName();
        return mRecipeName;
    }

    // Get Ingredients
    public List<Ingredient> getRecipeIngredients(Recipe recipe) {
        mIngredientsList = recipe.getIngredients();
        return mIngredientsList;
    }

    // Set Ingredients
    public void setRecipeIngredients(List<Ingredient> ingredients) {
        mIngredientsList = ingredients;
    }

    // Get steps
    public List<Step> getRecipeSteps(Recipe recipe) {
        mStepsList = recipe.getSteps();
        return mStepsList;
    }

    // in Main activity, create intent to Master passing in the recipeId
    // Here, create getters / setters for all ingredients and steps fields
    // add a getname method (for title and widget later)
    // In main Activity, modify intent passing in only recipeId
    // in DAO, create new query, recipe by id
    // ad  to repository
}
