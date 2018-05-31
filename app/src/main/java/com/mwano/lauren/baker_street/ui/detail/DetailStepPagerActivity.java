package com.mwano.lauren.baker_street.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEPS_LIST;
import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEP_SELECTED;


public class DetailStepPagerActivity extends FragmentActivity {

    @BindView(R.id.step_pager)
    ViewPager mStepPager;

    private PagerAdapter mPagerAdapter;
    public ArrayList<Step> mSteps;
    public Step mCurrentStep;
    private int mStepId;
    private String mDescription;

    //private static final int NUM_PAGES = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step_pager);
        ButterKnife.bind(this);

        // Get the selected recipe from the intent
        if (savedInstanceState == null) {
            final Intent intentMasterDetailStep = getIntent();
            if (intentMasterDetailStep.hasExtra(STEP_SELECTED)
                    && intentMasterDetailStep.hasExtra(STEPS_LIST)) {
                mSteps = intentMasterDetailStep.getParcelableArrayListExtra(STEPS_LIST);
                mCurrentStep = intentMasterDetailStep.getParcelableExtra(STEP_SELECTED);
                // Get the id of the selected step from the intent
                mStepId = mCurrentStep.getId();
                // Get the ingredients List from the intent extra
                mDescription = mCurrentStep.getDescription();
                // Set Recipe name on toolbar
                setTitle(mCurrentStep.getShortDescription());
            }
        }

        // Instantiate a ViewPager and a PagerAdapter
        mPagerAdapter = new DetailStepPagerAdapter(getSupportFragmentManager(), mSteps);

//        // Instantiate a fragment passing in the data from the intent
//        DetailStepPageFragment detailFragment =
//                DetailStepPageFragment.newStepInstance((String) mDescription, (int) mStepId);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.detail_steps_container, detailFragment)
//                .commit();

        mStepPager.setAdapter(mPagerAdapter);
        mStepPager.setCurrentItem(mStepId);

    }

    @Override
    public void onBackPressed() {
        if(mStepPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mStepPager.setCurrentItem(mStepPager.getCurrentItem() - 1);
        }


    }

    /**
     * A simple pager adapter that represents the DetailStepPageFragment objects, in
     * sequence.
     */
    private class DetailStepPagerAdapter extends FragmentStatePagerAdapter {

        // Constructor
        public DetailStepPagerAdapter(FragmentManager fm, ArrayList<Step> steps) {
            super(fm);
            mSteps = steps;
        }

        @Override
        public Fragment getItem(int position) {
            return DetailStepPageFragment.newStepInstance(mDescription, mStepId);
        }

        @Override
        public int getCount() {
            return mSteps.size();
        }
    }
}
