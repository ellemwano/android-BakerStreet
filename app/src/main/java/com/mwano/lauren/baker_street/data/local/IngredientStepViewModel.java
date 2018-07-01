
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

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class IngredientStepViewModel  extends AndroidViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // Variable to cache the selected Recipe
    private Recipe mSingleRecipe;
    // Cache Ingredients list
    private List<Ingredient> mIngredientsList;
    private String mRecipeName;

    private static final String TAG = IngredientStepViewModel.class.getSimpleName();

    // Constructor with a reference to the repository, getting the Selected recipe from the repository
    public IngredientStepViewModel(@NonNull Application application, int recipeId) {
        super(application);
        mRepository = new RecipeRepository(application);
        mSingleRecipe = mRepository.loadSingleRecipeById(recipeId);
        Log.d(TAG, "Single Recipe is: "  + mSingleRecipe);
        // Single Recipe is null
    }

    // Get single Recipe by its id
    public Recipe getSingleRecipe() {
        return mSingleRecipe;
    }

//    // Get Recipe's ingredients
//    public LiveData<List<Ingredient>> getRecipeIngredients(int recipeId) {
//        mRepository.loadIngredientsForRecipe(recipeId);
//        return mObservableIngredientsList;
//    }

    // Get Ingredients
    public List<Ingredient> getIngredientsList(Recipe recipe) {
        recipe.getIngredients();
        return mIngredientsList;
    }

    // Set Recipe
    public void setSingleRecipe(Recipe singleRecipe) {
        mSingleRecipe = singleRecipe;
    }

    // Get name
    public String getRecipeName(Recipe recipe) {
       recipe.getName();
       return mRecipeName;
    }

    // Set Ingredients
    public void setRecipeIngredients (List<Ingredient> ingredients) {
        mIngredientsList = ingredients;
    }


    // in Main activity, create intent to Master passing in the recipeId
    // Here, create getters / setters for all ingredients and steps fields
    // add a getname method (for title and widget later)
    // In main Activity, modify intent passing in only recipeId
    // in DAO, create new query, recipe by id
    // ad  to repository
}
