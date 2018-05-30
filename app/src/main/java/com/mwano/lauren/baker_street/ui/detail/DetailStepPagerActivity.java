package com.mwano.lauren.baker_street.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import static com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment.STEP_SELECTED;


public class DetailStepPagerActivity extends AppCompatActivity {

    private Step mCurrentStep;
    private String mDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step_pager);
        // Get the selected recipe from the intent
        if (savedInstanceState == null) {
            final Intent intentMasterDetailStep = getIntent();
            if (intentMasterDetailStep.hasExtra(STEP_SELECTED)) {
                mCurrentStep = intentMasterDetailStep.getParcelableExtra(STEP_SELECTED);
                // Get the ingredients List from the intent extra
                mDescription = mCurrentStep.getDescription();
                // Set Recipe name on toolbar
                setTitle(mCurrentStep.getShortDescription());
            }
        }

        DetailStepPageFragment detailFragment =
                DetailStepPageFragment.newStepInstance((String) mDescription);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_steps_container, detailFragment)
                .commit();
    }
}
