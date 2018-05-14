package com.mwano.lauren.baker_street;

import com.mwano.lauren.baker_street.model.Recipe;


import java.util.List;

public class RecipesResponse {

    private List<Recipe> mRecipeList;

    public RecipesResponse(List<Recipe> recipeList){
        mRecipeList = recipeList;
    }

    public List<Recipe> getRecipeList() {
        return mRecipeList;
    }
}
