package com.mwano.lauren.baker_street.ui.master;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Provides UI for the view with the Steps for the selected recipe
 */
public class MasterStepsPageFragment extends Fragment
        implements MasterStepsAdapter.StepAdapterOnClickHandler {

    @BindView(R.id.rv_steps)
    RecyclerView mStepsRecyclerView;

    public List<Step> mSteps = new ArrayList<>();

    public static final String STEPS_LIST = "steps";
    private static final String TAG = MasterStepsPageFragment.class.getSimpleName();

    // Constructor
    public MasterStepsPageFragment() {
    }

    //Define a new interface onStepClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnStepClickListener interface calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(Step currentStep, ArrayList<Step> steps);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnStepClickListener");
        }
    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param steps ArrayList of Step objects of selected recipe in recipe list
     * @return fragment
     */
    public static MasterStepsPageFragment newStepsInstance(ArrayList<Step> steps) {
        MasterStepsPageFragment stepFragment = new MasterStepsPageFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(STEPS_LIST, steps);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            if (getArguments().containsKey(STEPS_LIST)) {
                mSteps = getArguments().getParcelableArrayList(STEPS_LIST);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_steps_page, container, false);
        ButterKnife.bind(this, rootView);
        // LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // RecyclerView
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        //Adapter
        MasterStepsAdapter stepsAdapter =
                new MasterStepsAdapter(getContext(), (ArrayList<Step>) mSteps, this);
        mStepsRecyclerView.setAdapter(stepsAdapter);

        return rootView;
    }

    /**
     * When a step is clicked, the fragment uses the interface callback to deliver the event
     * to the host activity
     * @param currentStep The selected step
     * @param steps  the list of steps for the given recipe
     */
    @Override
    public void onClick(Step currentStep, ArrayList<Step> steps) {
        // Send the event to the host activity
        mCallback.onStepSelected(currentStep, steps);
        Log.d(TAG, "Step selected is: " + currentStep);

    }
}