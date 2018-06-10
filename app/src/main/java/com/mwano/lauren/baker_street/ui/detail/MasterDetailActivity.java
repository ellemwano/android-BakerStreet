package com.mwano.lauren.baker_street.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.master.MasterIngredientsPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity;
import com.mwano.lauren.baker_street.ui.master.MasterStepsAdapter;
import com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment;

import java.util.ArrayList;
import java.util.List;

import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE;

public class MasterDetailActivity extends AppCompatActivity
    implements MasterStepsAdapter.StepAdapterOnClickHandler {

    private Boolean mTwoPane;
    private Recipe mCurrentRecipe;
    private List<Step> mSteps;
    private Step mCurrentStep;
    private String mRecipeName;
    private int mStepId;
    private List<Ingredient> mIngredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

//        // TODO review boolean
//        final boolean b = findViewById(R.id.master_detail_layout) != null;
//        mTwoPane = true;

        if (savedInstanceState == null) {
            final Intent intentReceivedMainMasterDetail = getIntent();
            if (intentReceivedMainMasterDetail.hasExtra(RECIPE)) {
                mCurrentRecipe = intentReceivedMainMasterDetail.getParcelableExtra(RECIPE);
                // Get the steps arrayList from the intent extra
                if(mCurrentRecipe != null) {
                    mSteps = mCurrentRecipe.getSteps();
                    // Get Recipe name
                    mRecipeName = mCurrentRecipe.getName();
                }
            }

            // TODO check
            // Set Steps fragment
            MasterStepsPageFragment stepFragment =
                    MasterStepsPageFragment.newStepsInstance((ArrayList<Step>) mSteps);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_steps_container, stepFragment)
                    .commit();

            // TODO Set Detail fragment to 0 by default
            mStepId = 0;
            DetailStepPageFragment detailFragment =
                    DetailStepPageFragment.newStepInstance(mCurrentStep, mStepId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tablet_detail_steps_container, detailFragment)
                    .commit();
        }
    }

    // Open selected Detail fragment from Master intent
    @Override
    public void onClick(Step currentStep, ArrayList<Step> steps) {
        mStepId = currentStep.getId();
        DetailStepPageFragment detailFragment =
                DetailStepPageFragment.newStepInstance(currentStep, mStepId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tablet_detail_steps_container, detailFragment)
                .commit();
    }
}
