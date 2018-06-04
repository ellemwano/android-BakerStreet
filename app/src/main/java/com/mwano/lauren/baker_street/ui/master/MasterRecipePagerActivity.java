package com.mwano.lauren.baker_street.ui.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE;


/**
 * Code source for ViewPager (java + xml) - Codelab:
 * https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#3
 */

public class MasterRecipePagerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;
    private Recipe mCurrentRecipe;
    private String mRecipeName;
    private List<Ingredient> mIngredients = new ArrayList<>();
    private List<Step> mSteps = new ArrayList<>();

    private static final String SELECTED_RECIPE = "recipe selected";
    private static final String RECIPE_NAME = "recipe name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_recipes_pager);
        // Bind views
        ButterKnife.bind(this);
        // Adding Toolbar to Main screen
        setSupportActionBar(toolbar);

        // Get the selected recipe from the intent
        if (savedInstanceState == null) {
            final Intent intentReceivedMainMaster = getIntent();
            if(intentReceivedMainMaster.hasExtra(RECIPE)) {
                mCurrentRecipe = intentReceivedMainMaster.getParcelableExtra(RECIPE);
                // Get the ingredients arrayList from the intent extra
                mIngredients = mCurrentRecipe.getIngredients();
                // Get the steps arrayList from the intent extra
                mSteps = mCurrentRecipe.getSteps();
                // Get Recipe name
                mRecipeName = mCurrentRecipe.getName();
            }
        } else {
            mCurrentRecipe = savedInstanceState.getParcelable(SELECTED_RECIPE);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME);
        }
        // Set Recipe name on toolbar
        setTitle(mRecipeName);
        // Setting ViewPager for each Tabs
        setupViewPager(viewPager);
        // Add tabs
        tabs.setupWithViewPager(viewPager);
    }

    // TODO create strings for tabs title
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        // Create instance of the ingredients fragment and add it to activity
        MasterIngredientsPageFragment ingredientFragment =
                MasterIngredientsPageFragment.newIngredientsInstance((ArrayList<Ingredient>) mIngredients);
        adapter.addFragment(ingredientFragment, "Ingredients");
        // Create instance of the steps fragment and add it to activity
        MasterStepsPageFragment stepFragment =
                MasterStepsPageFragment.newStepsInstance((ArrayList<Step>) mSteps);
        adapter.addFragment(stepFragment, "Steps");
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_RECIPE, mCurrentRecipe);
        outState.putString(RECIPE_NAME, mRecipeName);
    }
}
