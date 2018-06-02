package com.mwano.lauren.baker_street.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEPS_LIST;
import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEP_SELECTED;


/**
 * ViewPager implementation. Code source:
 * https://developer.android.com/training/animation/screen-slide
 * http://www.truiton.com/2013/05/android-fragmentstatepageradapter-example/
 */

public class DetailStepPagerActivity extends AppCompatActivity {

    @BindView(R.id.step_pager)
    ViewPager mStepPager;
    @BindView(R.id.button_to_first)
    Button buttonFirst;
    @BindView(R.id.button_to_last)
    Button buttonLast;


    private PagerAdapter mPagerAdapter;
    public ArrayList<Step> mSteps;
    public Step mCurrentStep;
    private int mStepId;
    private String mDescription;
    private String mVideoUrl;

    // TODO Add page indicator
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
                // Get the video Url from the intent
                mVideoUrl = mCurrentStep.getVideoURL();
                // Set Recipe name on toolbar
                //setTitle(mCurrentStep.getShortDescription());
            }
        }
        // Instantiate a ViewPager and a PagerAdapter
        mPagerAdapter = new DetailStepPagerAdapter(getSupportFragmentManager(), mCurrentStep);
        mStepPager.setAdapter(mPagerAdapter);
        mStepPager.setCurrentItem(mStepId);

        // Set up First and Last buttons
        buttonFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mStepPager.setCurrentItem(0);
            }
        });
        buttonLast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mStepPager.setCurrentItem(mSteps.size() - 1);
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        if (mStepPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mStepPager.setCurrentItem(mStepPager.getCurrentItem() - 1);
//        }
//    }


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
            return mSteps.size();
        }
    }
}
