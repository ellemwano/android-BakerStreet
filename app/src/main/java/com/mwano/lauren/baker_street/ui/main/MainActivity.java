package com.mwano.lauren.baker_street.ui.main;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.network.ApiClient;
import com.mwano.lauren.baker_street.data.network.ApiInterface;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.ui.twoPane.MasterDetailActivity;
import com.mwano.lauren.baker_street.ui.master.MasterIngredientsPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


    /**
    Code source for Retrofit
    https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
//    and the use of APIManager:
//    http://codingsonata.com/retrofit-tutorial-android-part-1-introduction/
    */
public class MainActivity extends AppCompatActivity
        implements MainRecipeAdapter.RecipeAdapterOnClickHandler{

    @BindView(R.id.card_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.tv_recipe_name_main)
    TextView recipeNameMainTextView;
    @Nullable
    @BindView(R.id.tv_to_baking_steps)
    TextView toStepsTextView;
    @Nullable
    @BindView(R.id.default_tablet_layout)
    LinearLayout defaultTabletLayout;
    @Nullable
    @BindView(R.id.main_ingredients_container)
    LinearLayout mainTabletLayout;

    private MainRecipeAdapter mMainRecipeAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mColumnsNumber;
    private Boolean mTwoPane;
    private List<Recipe> mRecipes;
    private Recipe mCurrentRecipe;
    private int mRecipeId;
    private List<Ingredient> mIngredients;
    private String mRecipeName;

    public static final String RECIPE = "recipe";
    private final String TAG = MainActivity.class.getSimpleName();


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
        if(findViewById(R.id.main_tablet_layout) != null) {
            mTwoPane = true;
            mColumnsNumber = 3;

            if (savedInstanceState == null) {
                // Set welcome screen
                mainTabletLayout.setVisibility(View.GONE);
                defaultTabletLayout.setVisibility(View.VISIBLE);
//                if(mRecipes != null) {
//                    mRecipeId = 0;
//                    mCurrentRecipe = mRecipes.get(mRecipeId);
//                    //Log.d(TAG, "Default recipe is: " + mCurrentRecipe.getName());
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    MasterIngredientsPageFragment ingredientsFragment = MasterIngredientsPageFragment
//                            .newIngredientsInstance((ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
//                    fragmentManager.beginTransaction()
//                            .add(R.id.main_ingredients_container, ingredientsFragment)
//                            .commit();
//                }
            } else {
                defaultTabletLayout.setVisibility(View.GONE);
                mainTabletLayout.setVisibility(View.VISIBLE);
                mCurrentRecipe = savedInstanceState.getParcelable(RECIPE);
                recipeNameMainTextView.setText(mCurrentRecipe.getName());
                FragmentManager fragmentManager = getSupportFragmentManager();
                MasterIngredientsPageFragment ingredientsFragment = MasterIngredientsPageFragment
                        .newIngredientsInstance((ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
                fragmentManager.beginTransaction()
                        .add(R.id.main_ingredients_container, ingredientsFragment)
                        .commit();
            }
            // On two-pane, open MasterDetail activity for selected recipe via a button
            toStepsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intentSentMainMasterDetail = new Intent(MainActivity.this, MasterDetailActivity.class);
                    intentSentMainMasterDetail.putExtra(RECIPE, mCurrentRecipe);
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
        mMainRecipeAdapter = new MainRecipeAdapter(this, mRecipes, this);
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
            defaultTabletLayout.setVisibility(View.GONE);
            mainTabletLayout.setVisibility(View.VISIBLE);
            mCurrentRecipe = currentRecipe;
            recipeNameMainTextView.setText(mCurrentRecipe.getName());
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
