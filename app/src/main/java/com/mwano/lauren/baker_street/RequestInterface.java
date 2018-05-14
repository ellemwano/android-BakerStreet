package com.mwano.lauren.baker_street;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<RecipesResponse> getRecipes();
}
