package com.mwano.lauren.baker_street.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.RecipeDatabase;
import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.data.local.viewmodel.IngredientStepViewModel;
import com.mwano.lauren.baker_street.data.local.viewmodel.IngredientStepViewModelFactory;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.twoPane.MasterDetailTwoPaneActivity;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEPS_LIST;


/**
 * ViewPager implementation. Code source:
 * https://developer.android.com/training/animation/screen-slide
 * http://www.truiton.com/2013/05/android-fragmentstatepageradapter-example/
 */

public class DetailStepPagerActivity extends AppCompatActivity {

    @BindView(R.id.step_pager)
    ViewPager mStepPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mPageIndicatorView;

    private PagerAdapter mPagerAdapter;
    private int mRecipeId;
    public ArrayList<Step> mSteps;
    public Step mCurrentStep;
    private int mStepId;
    private String mDescription;
    private IngredientStepViewModel mViewModel;
    private RecipeDatabase mRecipeDatabase;
    private RecipeRepository mRecipeRepository;
    private Recipe mCurrentRecipe;


    private static final String CURRENT_STEP = "current step";
    private static final String STEP_LIST = "step list";
    private static final String STEP_ID = "step id";
    private static final String STEP_DESCRIPTION = "step description";
    private static final String RECIPE_ID = "recipe id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step_pager);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            // Get the selected step from the intent
            final Intent intentMasterDetailStep = getIntent();
            if (intentMasterDetailStep.hasExtra(CURRENT_STEP)
                    && intentMasterDetailStep.hasExtra(STEPS_LIST)) {
                mSteps = intentMasterDetailStep.getParcelableArrayListExtra(STEPS_LIST);
                mCurrentStep = intentMasterDetailStep.getParcelableExtra(CURRENT_STEP);
            }
//            if (intentMasterDetailStep.hasExtra(CURRENT_STEP)) {
//                mCurrentStep = intentMasterDetailStep.getParcelableExtra(CURRENT_STEP);
//                mRecipeId = intentMasterDetailStep.getParcelableExtra(RECIPE_ID);
//            }
//            // Instantiate Database
//            mRecipeDatabase = RecipeDatabase.getDatabase(this);
//            // Instantiate Repository
//            mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());
//            // ViewModel
//            IngredientStepViewModelFactory factory =
//                    new IngredientStepViewModelFactory(mRecipeRepository, mRecipeId);
//            mViewModel =
//                    ViewModelProviders.of(this, factory).get(IngredientStepViewModel.class);
//            mViewModel.getSingleRecipe().observe(DetailStepPagerActivity.this, new Observer<Recipe>() {
//                        @Override
//                        public void onChanged(@Nullable Recipe recipe) {
//                            mCurrentRecipe = recipe;
//                            // Get list of steps
//                            mSteps = mCurrentRecipe.getSteps();
//                            // Get the id of the selected step from the intent
//                            mStepId = mCurrentStep.getId();
//                            // Get the ingredients List from the intent extra
//                            mDescription = mCurrentStep.getDescription();
//                            // Set Recipe name on toolbar
//                            setTitle(mCurrentStep.getShortDescription());
//                            mPagerAdapter = new DetailStepPagerAdapter(getSupportFragmentManager(), mCurrentStep);
//                            mStepPager.setAdapter(mPagerAdapter);
//                            mStepPager.setCurrentItem(mStepId);
//                        }
//            });
        }
        if (savedInstanceState != null) {
            mCurrentStep = savedInstanceState.getParcelable(CURRENT_STEP);
            mSteps = savedInstanceState.getParcelableArrayList(STEP_LIST);
            mStepId = savedInstanceState.getInt(STEP_ID);
            mDescription = savedInstanceState.getString(STEP_DESCRIPTION);
        }
        // Get the id of the selected step from the intent
        mStepId = mCurrentStep.getId();
        // Get the ingredients List from the intent extra
        mDescription = mCurrentStep.getDescription();
        // Set Recipe name on toolbar
        setTitle(mCurrentStep.getShortDescription());
        // Instantiate a ViewPager and a PagerAdapter
        mPagerAdapter = new DetailStepPagerAdapter(getSupportFragmentManager(), mCurrentStep);
        mStepPager.setAdapter(mPagerAdapter);
        mStepPager.setCurrentItem(mStepId);

        // Set page indicator
        mPageIndicatorView.setCount(mSteps.size()); // specify total count of indicators
        mPageIndicatorView.setViewPager(mStepPager);
        mPageIndicatorView.setSelection(mStepId);
    }


    /**
     * A simple pager adapter that represents the DetailStepPageFragment objects, in
     * sequence.
     */
    private class DetailStepPagerAdapter extends FragmentStatePagerAdapter {

        // Constructor
        public DetailStepPagerAdapter(FragmentManager fm, Step step) {
            super(fm);
            mCurrentStep = step;
        }

        @Override
        public Fragment getItem(int position) {
            mCurrentStep = mSteps.get(position);
            return DetailStepPageFragment.newStepInstance(mCurrentStep, position);
        }

        @Override
        public int getCount() {
            if (mSteps == null) return 0;
            return mSteps.size();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_ID, mStepId);
        outState.putString(STEP_DESCRIPTION, mDescription);
        outState.putParcelable(CURRENT_STEP, mCurrentStep);
        outState.putParcelableArrayList(STEP_LIST, mSteps);
    }
}
