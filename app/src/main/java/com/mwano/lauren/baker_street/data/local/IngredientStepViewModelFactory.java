
package com.mwano.lauren.baker_street.data.local;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.model.Recipe;

public class IngredientStepViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    //private RecipeDatabase mDb;
    private int mRecipeId;
    private Application mApplication;

    // Constructor
    public IngredientStepViewModelFactory(Application application, int recipeId) {
        mApplication = application;
        mRecipeId = recipeId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new IngredientStepViewModel (mApplication, mRecipeId);
    }
}
