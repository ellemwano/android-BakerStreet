/*
 * Copyright (c) <2018> <Laurence Moineau>
 *
 * MIT Licence
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mwano.lauren.baker_street.ui.twoPane;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.detail.DetailStepPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterIngredientsPageFragment;
import com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity;
import com.mwano.lauren.baker_street.ui.master.MasterStepsAdapter;
import com.mwano.lauren.baker_street.ui.master.MasterStepsPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mwano.lauren.baker_street.ui.main.MainActivity.RECIPE;

public class MasterDetailActivity extends AppCompatActivity
    implements MasterStepsAdapter.StepAdapterOnClickHandler {

    @BindView(R.id.tv_recipe_name)
    TextView mRecipeNameTextView;
    private Recipe mCurrentRecipe;
    private List<Step> mSteps;
    private Step mCurrentStep;
    private String mRecipeName;
    private int mStepId;
    private String mDescription;
    private List<Ingredient> mIngredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        ButterKnife.bind(this);


        if (savedInstanceState == null) {
            final Intent intentReceivedMainMasterDetail = getIntent();
            if (intentReceivedMainMasterDetail.hasExtra(RECIPE)) {
                mCurrentRecipe = intentReceivedMainMasterDetail.getParcelableExtra(RECIPE);
                if(mCurrentRecipe != null) {
                    // Get the steps arrayList from the intent extra
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
            mCurrentStep = mSteps.get(mStepId);
            DetailStepPageFragment detailFragment =
                    DetailStepPageFragment.newStepInstance(mCurrentStep, mStepId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tablet_detail_steps_container, detailFragment)
                    .commit();
        }
        mRecipeNameTextView.setText(mRecipeName);
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
