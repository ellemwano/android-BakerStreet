package com.mwano.lauren.baker_street.data.local.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.data.local.viewmodel.RecipeViewModel;

public class RecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private RecipeRepository mRecipeRepository;

    // Constructor
    public RecipeViewModelFactory(RecipeRepository repository) {
        mRecipeRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(mRecipeRepository);
    }
}
