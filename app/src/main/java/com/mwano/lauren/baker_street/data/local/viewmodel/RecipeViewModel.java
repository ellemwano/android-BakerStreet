
/*
 * Copyright (c) <2018> <Laurence Moineau>
 *
 * MIT Licence
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mwano.lauren.baker_street.data.local.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

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




