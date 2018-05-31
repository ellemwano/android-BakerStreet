package com.mwano.lauren.baker_street.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A Fragment subclass that displays the video and description for the selected step
 */
public class DetailStepPageFragment extends Fragment {

    @BindView(R.id.exoplayer)
    ImageView mImagePlaceholder;
    @BindView(R.id.tv_detail_description)
    TextView mDescriptionTextView;
    //@BindView(R.id.button_previous) Button mPreviousButton;
    //@BindView(R.id.button_next) Button mNextButton;

    public Step mStep;
    public int mStepId;
    public ArrayList<Step> mStepList;

    public static final String STEP = "step";
    public static final String STEP_ID = "step id";

    // Constructor
    public DetailStepPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(STEP)) {
            mStep = getArguments().getParcelable(STEP);
            mStepId = getArguments().getInt(STEP_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_step_page, container, false);
        ButterKnife.bind(this, rootview);
        // Set content to views
        mDescriptionTextView.setText(mStep.getDescription());

        return rootview;
    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param step String description of selected step in step list
     * @param stepId int id of the selected step in step list
     * @return fragment
     */
   // Changed Step from String to ArrayList
    public static DetailStepPageFragment newStepInstance(Step step, int stepId) {
        DetailStepPageFragment stepFragment = new DetailStepPageFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelable(STEP, step);
        arguments.putInt(STEP_ID, stepId);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }
}
