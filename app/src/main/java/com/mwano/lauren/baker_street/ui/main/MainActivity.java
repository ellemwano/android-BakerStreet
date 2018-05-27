package com.mwano.lauren.baker_street.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.json.ApiClient;
import com.mwano.lauren.baker_street.json.ApiInterface;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.ui.detail.DetailStepsActivity;
import com.mwano.lauren.baker_street.ui.master.MasterRecipeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MainRecipeAdapter.RecipeAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipes;
    private MainRecipeAdapter mMainRecipeAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mColumnsNumber;
    private Context mContext;
    private final String TAG = MainActivity.class.getSimpleName();

    /*
    Code source for Retrofit
    https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
//    and the use of APIManager:
//    http://codingsonata.com/retrofit-tutorial-android-part-1-introduction/
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateUi();
    }

    private void populateUi() {
        // Set number of columns in portrait or landscape mode
        mColumnsNumber = (int) getResources().getInteger(R.integer.num_of_columns);
        mGridLayoutManager = new GridLayoutManager(this, mColumnsNumber);
        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        // Adapter
        mMainRecipeAdapter = new MainRecipeAdapter(mContext, mRecipes, this);
        mRecyclerView.setAdapter(mMainRecipeAdapter);
        // Display recipes
        loadRecipes();
    }

    public void loadRecipes() {
        ApiInterface request = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Recipe>> call = request.getRecipes();
        // Asynchronous request
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call,
                                   Response<List<Recipe>> response) {
                mRecipes = response.body();
                // Log.d(TAG, "Number of recipes :" + mRecipes.size());
                mMainRecipeAdapter.setRecipeData(mRecipes);
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText
                        (MainActivity.this, "error message", Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
            }
        });
    }

    // Open MasterRecipeActivity intent, using parcelable
    @Override
    public void onClick(Recipe currentRecipe) {
        Intent intentMasterRecipe = new Intent(this, MasterRecipeActivity.class);
        intentMasterRecipe.putExtra("recipe", currentRecipe);
        startActivity(intentMasterRecipe);
        // Log.d(TAG, "Selected Recipe; " + currentRecipe.getName());
    }

    // TODO remove later
    public void clickButton(View view) {
        Intent intent = new Intent(this, DetailStepsActivity.class);
        startActivity(intent);
    }
}
