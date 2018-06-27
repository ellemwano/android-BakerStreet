
package com.mwano.lauren.baker_street.data.local;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    // Reference to the repository
    private RecipeRepository mRepository;
    // LiveData variable to cache the list of Recipes
    private LiveData<List<Recipe>> mObservableRecipes;
    //private boolean hasInternetConnection;
    private static boolean internetState = false;


    // Constructor with a reference to the repository, getting the list of recipes from the repository
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
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
        //Timber.v(String.valueOf(internetState));
    }

    // Delete all recipes
    public void deleteAllRecipes() {
        mRepository.deleteAllRecipesFromDb();
    }





    // Use MediatorLivedata  (like tpakis repo)
    // Remove getAllRecipes from constructor and add MediatorLiveData instead
    // Set a getter method for fetched data from network, from repo
    // Set a getter for data from DB (from repo)?
    // Or just a general one, no repo connection?
    // if create a conditional getter method in repo (list empty vs list full)
    // or no internet connection = get from DB
    // and call method here inside new general method to get data.
}




