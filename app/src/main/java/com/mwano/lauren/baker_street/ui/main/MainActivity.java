package com.mwano.lauren.baker_street.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.json.ApiClient;
import com.mwano.lauren.baker_street.json.ApiInterface;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.ui.detail.MasterDetailActivity;
import com.mwano.lauren.baker_street.ui.master.MasterIngredientsPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MainRecipeAdapter.RecipeAdapterOnClickHandler{

    public static final String RECIPE = "recipe";
    private final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.card_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.button_steps)
    Button stepsButton;
    private List<Recipe> mRecipes;
    private MainRecipeAdapter mMainRecipeAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mColumnsNumber;
    private Context mContext;
    private Boolean mTwoPane;
    private Recipe mCurrentRecipe;
    private List<Ingredient> mIngredients;
    private String mRecipeName;

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
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        // Set app name to toolbar
        toolbar.setTitle(R.string.app_name);
        loadRecipes();

        // Check if 2-pane layout
        if(findViewById(R.id.main_ingredients_container) != null) {
            mTwoPane = true;
            mColumnsNumber = 3;

            if (savedInstanceState == null) {
                // TODO Set default recipe
                if(mRecipes != null) {
                    mCurrentRecipe = mRecipes.get(0);
                    //Log.d(TAG, "Default recipe is: " + mCurrentRecipe.getName());
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    MasterIngredientsPageFragment ingredientsFragment = MasterIngredientsPageFragment
                            .newIngredientsInstance((ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
                    fragmentManager.beginTransaction()
                            .add(R.id.main_ingredients_container, ingredientsFragment)
                            .commit();
                }
            } else {
                mCurrentRecipe = savedInstanceState.getParcelable(RECIPE);
            }
            // On two-pane, open MasterDetail activity for selected recipe via a button
            final Intent intentSentMainMasterDetail = new Intent(this, MasterDetailActivity.class);
            intentSentMainMasterDetail.putExtra(RECIPE, mCurrentRecipe);
            stepsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intentSentMainMasterDetail);
                }
            });
        } else {
            // We're in a single-pane mode, displaying fragments on a phone, in different activities
            mTwoPane = false;
            // Set number of columns in portrait or landscape mode
            mColumnsNumber = (int) getResources().getInteger(R.integer.num_of_columns);
        }
        populateUi();
        // Set Recipe name on toolbar
        if(mCurrentRecipe != null) {
            mRecipeName = mCurrentRecipe.getName();
            setTitle(mRecipeName);
        }
    }

    private void populateUi() {
        mGridLayoutManager = new GridLayoutManager(this, mColumnsNumber);
        // RecyclerView
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        // Adapter
        mMainRecipeAdapter = new MainRecipeAdapter(mContext, mRecipes, this);
        mRecyclerView.setAdapter(mMainRecipeAdapter);
//        // Display recipes
//        loadRecipes();
    }

    // TODO add no Connection error
    public void loadRecipes() {
        ApiInterface request = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Recipe>> call = request.getRecipes();
        // Asynchronous request
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call,
                                   Response<List<Recipe>> response) {
                mRecipes = response.body();
                Log.d(TAG, "Number of recipes :" + mRecipes.size());
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

    // On two-pane, create Ingredients fragment for selected Recipe
    // On single-pane, open MasterRecipePagerActivity, passing in selected Recipe
    @Override
    public void onClick(Recipe currentRecipe) {
        if(mTwoPane) {
            mCurrentRecipe = currentRecipe;
            ArrayList<Ingredient> mIngredients = (ArrayList<Ingredient>) mCurrentRecipe.getIngredients();
            MasterIngredientsPageFragment ingredientFragment =
                    MasterIngredientsPageFragment.newIngredientsInstance(mIngredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_ingredients_container, ingredientFragment)
                    .commit();
        } else {
            Intent intentSentMainMaster = new Intent(this, MasterRecipePagerActivity.class);
            intentSentMainMaster.putExtra(RECIPE, currentRecipe);
            startActivity(intentSentMainMaster);
            // Log.d(TAG, "Selected Recipe; " + currentRecipe.getName());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE, mCurrentRecipe);
    }

}
