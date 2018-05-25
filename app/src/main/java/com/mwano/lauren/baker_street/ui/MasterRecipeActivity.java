package com.mwano.lauren.baker_street.ui;

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


/**
 * Code source for ViewPager (java + xml) - Codelab:
 * https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#3
 */

public class MasterRecipeActivity extends AppCompatActivity {

    private Recipe mCurrentRecipe;
    private List<Ingredient> mIngredients = new ArrayList<>();
    private List<Step> mSteps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_recipes);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the selected recipe from the intent
        if (savedInstanceState == null) {
            final Intent intentThatStartedThisActivity = getIntent();
            if(intentThatStartedThisActivity.hasExtra("recipe")) {
                mCurrentRecipe = intentThatStartedThisActivity.getParcelableExtra("recipe");
                // Get the ingredients arrayList from the intent extra
                mIngredients = mCurrentRecipe.getIngredients();
//                // Create instance of the ingredients fragment and add it to activity
//                MasterIngredientsFragment ingredientFragment =
//                        MasterIngredientsFragment.newInstance((ArrayList<Ingredient>) mIngredients);
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_content, ingredientFragment)
//                        .commit();
                // Get the steps arrayList from the intent extra
                mSteps = mCurrentRecipe.getSteps();
//                // Create instance of the steps fragment and add it to activity
//                MasterStepsFragment stepFragment =
//                        MasterStepsFragment.newInstance((ArrayList<Step>) mSteps);
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_content, stepFragment)
//                        .commit();
            }


        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Add tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        // Create instance of the ingredients fragment and add it to activity
        MasterIngredientsFragment ingredientFragment =
                MasterIngredientsFragment.newInstance((ArrayList<Ingredient>) mIngredients);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.main_content, ingredientFragment)
//                .commit();
        adapter.addFragment(ingredientFragment, "Ingredients");
        // Create instance of the steps fragment and add it to activity
        MasterStepsFragment stepFragment =
                MasterStepsFragment.newInstance((ArrayList<Step>) mSteps);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.main_content, stepFragment)
//                .commit();
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
}
