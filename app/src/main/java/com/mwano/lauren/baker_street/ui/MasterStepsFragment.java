package com.mwano.lauren.baker_street.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.MasterStepsAdapter;
import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

public class MasterStepsFragment extends Fragment {

    // Recipe includes a List of Ingredients
    public List<Step> mSteps = new ArrayList<Step>();
    RecyclerView mStepsRecyclerView;

    public static final String STEP_KEY = "steps";

    // Constructor
    public MasterStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            mSteps = getArguments().getParcelableArrayList(STEP_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_steps, container, false);
        // LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // RecyclerView
        mStepsRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_steps);
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        //Adapter
        MasterStepsAdapter stepsAdapter =
                new MasterStepsAdapter(getContext(), mSteps);
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
    public static MasterStepsFragment newInstance (ArrayList<Step> steps) {
        MasterStepsFragment stepFragment = new MasterStepsFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(STEP_KEY, steps);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }




}
