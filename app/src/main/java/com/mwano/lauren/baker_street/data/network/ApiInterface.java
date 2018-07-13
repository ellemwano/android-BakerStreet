
package com.mwano.lauren.baker_street.data.network;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String RECIPES = "/topher/2017/May/59121517_baking/baking.json";

    @GET(RECIPES)
    Call<List<Recipe>> getRecipes();
}
