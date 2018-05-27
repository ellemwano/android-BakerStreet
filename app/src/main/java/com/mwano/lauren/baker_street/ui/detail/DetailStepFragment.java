package com.mwano.lauren.baker_street.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A Fragment subclass that displays the video and description for the selected step
 */
public class DetailStepFragment extends Fragment {


    //@BindView(R.id.exoplayer) ImageView mImagePlaceholder;
    @BindView(R.id.tv_detail_description) TextView mDescriptionTextView;
    @BindView(R.id.button_previous) Button mPreviousButton;
    @BindView(R.id.button_next) Button mNextButton;

    public String mStep;

    public static final String STEP = "step";

    // Constructor
    public DetailStepFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(STEP)) {
            mStep = getArguments().getString(STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, rootview);
        // Set content to views
        mDescriptionTextView.setText(mStep);

        return rootview;

    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param step String description of selected step in step list
     * @return fragment
     */
    public static DetailStepFragment newStepInstance(String step) {
        DetailStepFragment stepFragment = new DetailStepFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putString(STEP, step);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }
}
