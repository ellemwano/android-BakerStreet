package com.mwano.lauren.baker_street.ui.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;
import com.mwano.lauren.baker_street.ui.detail.DetailStepActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Provides UI for the view with the Steps for the selected recipe
 */
public class MasterStepsFragment extends Fragment
        implements MasterStepsAdapter.StepAdapterOnClickHandler {

    // Recipe includes a List of Ingredients
    public List<Step> mSteps = new ArrayList<Step>();
    @BindView(R.id.rv_steps) RecyclerView mStepsRecyclerView;

    public static final String STEPS_LIST = "steps";
    public static final String STEP_SELECTED = "selected step";

    // Constructor
    public MasterStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(STEPS_LIST)) {
            mSteps = getArguments().getParcelableArrayList(STEPS_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_steps, container, false);
        ButterKnife.bind(this, rootView);
        // LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // RecyclerView
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        //Adapter
        MasterStepsAdapter stepsAdapter =
                new MasterStepsAdapter(getContext(), mSteps, this);
        mStepsRecyclerView.setAdapter(stepsAdapter);
        //stepsAdapter.setStepData(mSteps);
        return rootView;
    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param steps ArrayList of Step objects of selected recipe in recipe list
     * @return fragment
     */
    public static MasterStepsFragment newStepsInstance(ArrayList<Step> steps) {
        MasterStepsFragment stepFragment = new MasterStepsFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(STEPS_LIST, steps);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }

    @Override
    public void onClick(Step currentStep) {
        Intent intentDetailStep = new Intent(getActivity(), DetailStepActivity.class);
        intentDetailStep.putExtra(STEP_SELECTED, currentStep);
        startActivity(intentDetailStep);
    }
}