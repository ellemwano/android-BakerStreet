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

    // TODO
    @Override
    public MasterStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_master_steps_card, parent, false);
        return new MasterStepsAdapter.MasterStepsViewHolder(mItemView);
    }

    // TODO
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

        public TextView stepsIdTextView;
        public TextView stepsTextView;

        // Constructor
        // TODO
        public MasterStepsViewHolder(View view) {
            super(view);
            stepsIdTextView = (TextView) itemView.findViewById(R.id.tv_step_id);
            stepsTextView = (TextView) itemView.findViewById(R.id.tv_step_description);
        }
    }

}
