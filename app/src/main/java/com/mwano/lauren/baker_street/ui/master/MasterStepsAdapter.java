package com.mwano.lauren.baker_street.ui.master;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterStepsAdapter
        extends RecyclerView.Adapter<MasterStepsAdapter.MasterStepsViewHolder> {

    private Context mContext;
    private List<Step> mSteps;

    // TODO Add onClickHandler


    // Constructor
    public MasterStepsAdapter(Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
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
        holder.stepsIdTextView.setText(String.valueOf(mStep.getId()));
        holder.stepsTextView.setText(mStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    public void setStepData(List<Step> stepData) {
        mSteps = stepData;
        notifyDataSetChanged();
    }


    // ViewHolder
    public static class MasterStepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_id) TextView stepsIdTextView;
        @BindView(R.id.tv_step_description) TextView stepsTextView;

        // Constructor
        public MasterStepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
