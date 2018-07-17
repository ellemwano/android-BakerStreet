package com.mwano.lauren.baker_street.ui.master;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterStepsAdapter
        extends RecyclerView.Adapter<MasterStepsAdapter.MasterStepsViewHolder> {

    private Context mContext;
    private List<Step> mSteps;
    private final StepAdapterOnClickHandler mStepClickHandler;

    // OnClickHandler interface
    public interface StepAdapterOnClickHandler {
        void onClick(Step currentStep, ArrayList<Step> mSteps);
    }

    // Constructor
    public MasterStepsAdapter(Context context, ArrayList<Step> steps,
                              StepAdapterOnClickHandler stepClickHandler) {
        mContext = context;
        mSteps = steps;
        mStepClickHandler = stepClickHandler;
    }

    @Override
    public MasterStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_master_steps_card, parent, false);
        return new MasterStepsAdapter.MasterStepsViewHolder(mItemView);
    }


    @Override
    public void onBindViewHolder(MasterStepsViewHolder holder, int position) {
        Step mStep = mSteps.get(position);
        holder.stepIdTextView.setText(String.valueOf(mStep.getId()));
        holder.stepTextView.setText(mStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    public void setStepData(ArrayList<Step> stepData) {
        mSteps = stepData;
        notifyDataSetChanged();
    }


    // ViewHolder
    public class MasterStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_id) TextView stepIdTextView;
        @BindView(R.id.tv_step_description) TextView stepTextView;

        // Constructor
        public MasterStepsViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step currentStep = mSteps.get(adapterPosition);
            mStepClickHandler.onClick(currentStep, (ArrayList<Step>) mSteps);
        }
    }
}
