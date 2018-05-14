package com.mwano.lauren.baker_street.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mwano.lauren.baker_street.RecipeAdapter;
import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.RecipesResponse;
import com.mwano.lauren.baker_street.RequestInterface;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipes;
    private RecipeAdapter mRecipeAdapter;
    private Context mContext;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        loadRecipes();
    }

    public void loadRecipes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<Recipe>> call = request.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call,
                                   Response<List<Recipe>> response) {

                List<Recipe> recipesResponse = response.body();
                mRecipes = recipesResponse;
                Log.d(TAG, "mRecipes.toString()");
                mRecipeAdapter = new RecipeAdapter(mContext, mRecipes);
                mRecyclerView.setAdapter(mRecipeAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
