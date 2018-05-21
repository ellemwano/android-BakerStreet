package com.mwano.lauren.baker_street.json;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    public static final String JSON_FILE = "/topher/2017/May/59121517_baking/baking.json";

    @GET(JSON_FILE)
    Call<List<Recipe>> getRecipes();
}
