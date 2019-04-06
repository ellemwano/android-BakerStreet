package com.mwano.lauren.baker_street.ui.master;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.RecipeDatabase;
import com.mwano.lauren.baker_street.repository.RecipeRepository;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.detail.DetailStepPagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Code source help for ViewPager (java + xml) - Codelab:
 * https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#3
 */

public class MasterRecipePagerActivity extends AppCompatActivity
                implements MasterStepsPageFragment.OnStepClickListener {

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
    private int mRecipeId;
    private String mRecipeName;
    private List<Ingredient> mIngredients = new ArrayList<>();
    private List<Step> mSteps = new ArrayList<>();
    private IngredientStepViewModel mViewModel;

    public static final String RECIPE_ID = "recipe id";
    public static final String RECIPE_NAME = "recipe name";
    private static final String STEPS_LIST = "steps";
    private static final String CURRENT_STEP = "current step";
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
        // Set the action bar button to look like an up button
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Initialise SharedPreferences
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // Instantiate Database
        mRecipeDatabase = RecipeDatabase.getDatabase(this);
        // Instantiate Repository
        mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());

        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME);
        } else {
            // Get the selected Recipe id from the intent
            Bundle receivedBundle = getIntent().getExtras();
            mRecipeId = receivedBundle.getInt(RECIPE_ID);
            Log.d(TAG, "Received Recipe id = " + mRecipeId);
        }

        // ViewModel
        IngredientStepViewModelFactory factory =
                new IngredientStepViewModelFactory(mRecipeRepository, mRecipeId);
        mViewModel = ViewModelProviders.of(this, factory).get(IngredientStepViewModel.class);
        // Get the selected recipe from the recipe ID
        mViewModel.getSingleRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                mCurrentRecipe = recipe;
                Log.d(TAG, "Current Recipe from DB = " + mCurrentRecipe);
                // Get the current recipe name
                mRecipeName = mViewModel.getRecipeName(mCurrentRecipe);
                //mRecipeName = mCurrentRecipe.getName();
                Log.d(TAG, "Current RecipeName from DB = " + mRecipeName);
                mIngredients = mCurrentRecipe.getIngredients();
                // Get the steps arrayList for the current recipe
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
    }

    // Add fragments to ViewPager tabs
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

    // On phone, clicking on a step opens the DetailStepPagerActivity for the selected step
    @Override
    public void onStepSelected(Step currentStep, ArrayList<Step> steps) {
        Intent intentDetailStep = new Intent(this, DetailStepPagerActivity.class);
        intentDetailStep.putExtra(CURRENT_STEP, currentStep);
        intentDetailStep.putExtra(STEPS_LIST, steps);
        startActivity(intentDetailStep);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ID, mRecipeId);
        outState.putString(RECIPE_NAME, mRecipeName);
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
}
