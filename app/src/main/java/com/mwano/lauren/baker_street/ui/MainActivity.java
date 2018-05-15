package com.mwano.lauren.baker_street.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mwano.lauren.baker_street.RecipeAdapter;
import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.RequestInterface;
import com.mwano.lauren.baker_street.model.Recipe;

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
    private GridLayoutManager mGridLayoutmanager;
    private int mColumnsNumber;
    private Context mContext;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mColumnsNumber = (int) getResources().getInteger(R.integer.num_of_columns);
        mGridLayoutmanager = new GridLayoutManager(this, mColumnsNumber);
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setLayoutManager(mGridLayoutmanager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(mContext, mRecipes);
        mRecyclerView.setAdapter(mRecipeAdapter);
        loadRecipes();
    }

    public void loadRecipes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
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
                mRecipeAdapter.setRecipeData(mRecipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
