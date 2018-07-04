
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

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class IngredientStepViewModel  extends ViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // Cache selected Recipe
    private LiveData<Recipe> mSingleRecipe;
    // Cache Ingredients list
    private LiveData<List<Ingredient>> mIngredientsList;
    // Cache Steps list
    private LiveData<List<Step>> mStepsList;
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

    // Get name
    public String getRecipeName(Recipe recipe) {
        mRecipeName = recipe.getName();
        return mRecipeName;
    }

}