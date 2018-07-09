package com.mwano.lauren.baker_street.ui.master;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.IngredientStepViewModel;
import com.mwano.lauren.baker_street.data.local.IngredientStepViewModelFactory;
import com.mwano.lauren.baker_street.data.local.RecipeDatabase;
import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE;
import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE_ID;


/**
 * Code source for ViewPager (java + xml) - Codelab:
 * https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#3
 */

public class MasterRecipePagerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    // Shared Preferences object
    private SharedPreferences mPreferences;
    private RecipeRepository mRecipeRepository;
    private RecipeDatabase mRecipeDatabase;
    private Recipe mCurrentRecipe;
    public int mRecipeId;
    private String mRecipeName;
    private List<Ingredient> mIngredients = new ArrayList<>();
    private List<Step> mSteps = new ArrayList<>();
    private IngredientStepViewModel mViewModel;

    public static final String RECIPE_ID = "recipe id";
    public static final String RECIPE_NAME = "recipe name";
    // Name of Shared Preferences file
    public static final String SHARED_PREFS = "com.mwano.lauren.baker_street.preferences";

    private static final String TAG = MasterRecipePagerActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_recipes_pager);
        // Bind views
        ButterKnife.bind(this);
        // Adding Toolbar to Main screen
        setSupportActionBar(toolbar);

        // Initialise SharedPreferences
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Instantiate Database
        mRecipeDatabase = RecipeDatabase.getDatabase(this);
        // Instantiate Repository
        mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());

        if (savedInstanceState != null) {
            //mRecipeId = savedInstanceState.getInt(RECIPE_ID);
            //mRecipeName = savedInstanceState.getString(RECIPE_NAME);
            mRecipeId = mPreferences.getInt(RECIPE_ID, 1);
            mRecipeName = mPreferences.getString(RECIPE_NAME,"Nutella Pie");
        } else {
            // Get the selected Recipe id from the intent
            Bundle receivedBundle = getIntent().getExtras();
            mRecipeId = receivedBundle.getInt(RECIPE_ID);
            Log.d(TAG, "Received Recipe id = " + mRecipeId );
            // OK - ID correct
        }

        // ViewModel
        IngredientStepViewModelFactory factory =
                new IngredientStepViewModelFactory(mRecipeRepository, mRecipeId);
        mViewModel = ViewModelProviders.of(this, factory).get(IngredientStepViewModel.class);
        // Get the selected recipe from the ID from the intent
        mViewModel.getSingleRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                mCurrentRecipe = recipe;
                Log.d(TAG, "Current Recipe from DB = " + mCurrentRecipe);
                //
                mRecipeName = mViewModel.getRecipeName(mCurrentRecipe);
                Log.d(TAG, "Current RecipeName from DB = " + mCurrentRecipe.getName());
                //
                mIngredients = mCurrentRecipe.getIngredients();
                // Get the steps arrayList from the intent extra
                mSteps = mCurrentRecipe.getSteps();
                // Add the Recipe id and name to SharedPreferences
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.putInt(RECIPE_ID, mRecipeId);
                preferencesEditor.putString(RECIPE_NAME, mRecipeName);
                preferencesEditor.apply();
                // Set Recipe name on toolbar
                setTitle(mRecipeName);
                // Setting ViewPager for each Tabs
                setupViewPager(viewPager);
                // Add tabs
                tabs.setupWithViewPager(viewPager);
            }
        });

        // TODO check
        // Sending the Recipe ID to Steps and Ingredients fragments
        Bundle IdArgument = new Bundle();
        IdArgument.putInt(RECIPE_ID, mRecipeId);
        Log.d(TAG, "Sent Recipe id from Master Activity : " + mRecipeId);
        //
        MasterIngredientsPageFragment ingredientFragment = new MasterIngredientsPageFragment();
        ingredientFragment.setArguments(IdArgument);
        MasterStepsPageFragment stepFragment = new MasterStepsPageFragment();
        stepFragment.setArguments(IdArgument);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        // Create instance of the ingredients fragment and add it to activity
        MasterIngredientsPageFragment ingredientFragment =
                MasterIngredientsPageFragment.newIngredientsInstance((ArrayList<Ingredient>) mIngredients);
        adapter.addFragment(ingredientFragment, getResources().getString(R.string.viewpager_ingredients_tab));
        // Create instance of the steps fragment and add it to activity
        MasterStepsPageFragment stepFragment =
                MasterStepsPageFragment.newStepsInstance((ArrayList<Step>) mSteps);
        adapter.addFragment(stepFragment, getResources().getString(R.string.viewpager_steps_tab));
        viewPager.setAdapter(adapter);
    }

    /**
     * Adapter
     */
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(RECIPE_ID, mRecipeId);
//        outState.putString(RECIPE_NAME, mRecipeName);
//    }
}
