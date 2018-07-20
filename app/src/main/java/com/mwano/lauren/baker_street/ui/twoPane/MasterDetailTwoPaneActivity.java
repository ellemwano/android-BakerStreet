package com.mwano.lauren.baker_street.ui.twoPane;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.database.RecipeDatabase;
import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.data.local.viewmodel.IngredientStepViewModel;
import com.mwano.lauren.baker_street.data.local.viewmodel.IngredientStepViewModelFactory;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.detail.DetailStepPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE_ID;

public class MasterDetailTwoPaneActivity extends AppCompatActivity
    implements MasterStepsPageFragment.OnStepClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Recipe mCurrentRecipe;
    private List<Step> mSteps;
    private Step mCurrentStep;
    private String mRecipeName;
    private int mRecipeId;
    private int mStepId;
    private RecipeDatabase mRecipeDatabase;
    private RecipeRepository mRecipeRepository;
    private IngredientStepViewModel mViewModel;

    private static final String RECIPE_NAME = "recipe_name";
    private static final String TAG = MasterDetailTwoPaneActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail_two_pane);
        ButterKnife.bind(this);
        // Adding Toolbar to Main screen
        setSupportActionBar(toolbar);
        // Set the action bar button to look like an up button
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Get the selected Recipe id from the MainActivity (2-pane layout) intent
            Bundle receivedBundle = getIntent().getExtras();
            mRecipeId = receivedBundle.getInt(RECIPE_ID);
            Log.d(TAG, "Received Recipe id = " + mRecipeId);

            // Instantiate Database
            mRecipeDatabase = RecipeDatabase.getDatabase(this);
            // Instantiate Repository
            mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());
            // ViewModel
            IngredientStepViewModelFactory factory =
                    new IngredientStepViewModelFactory(mRecipeRepository, mRecipeId);
            mViewModel =
                    ViewModelProviders.of(this, factory).get(IngredientStepViewModel.class);
            mViewModel.getSingleRecipe().observe(MasterDetailTwoPaneActivity.this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    mCurrentRecipe = recipe;
                    // Get the steps arrayList from the intent extra
                    mSteps = mCurrentRecipe.getSteps();
                    // Get Recipe name
                    mRecipeName = mCurrentRecipe.getName();

                    // Set Steps fragment
                    MasterStepsPageFragment stepFragment =
                            MasterStepsPageFragment.newStepsInstance((ArrayList<Step>) mSteps);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.two_pane_steps_container, stepFragment)
                            .commit();

                    // Set Detail Step fragment to Step 0 by default
                    mStepId = 0;
                    mCurrentStep = mSteps.get(mStepId);
                    DetailStepPageFragment detailFragment =
                            DetailStepPageFragment.newStepInstance(mCurrentStep, mStepId);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.tablet_detail_step_container, detailFragment)
                            .commit();

                    // Set recipe name to toolbar
                    toolbar.setTitle(mRecipeName);
                }
            });
        } else {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME);
        }
    }

    // Display selected Detail fragment from Master (2-pane left) click
    @Override
    public void onStepSelected(Step currentStep, ArrayList<Step> steps) {
        mCurrentStep = currentStep;
        mSteps = steps;
        mStepId = mCurrentStep.getId();
        DetailStepPageFragment detailFragment =
                DetailStepPageFragment.newStepInstance(mCurrentStep, mStepId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tablet_detail_step_container, detailFragment)
                .commit();
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

}
