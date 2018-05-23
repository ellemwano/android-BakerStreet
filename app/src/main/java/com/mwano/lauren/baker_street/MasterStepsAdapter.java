package com.mwano.lauren.baker_street;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        return new MasterStepsViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    // TODO
    @Override
    public void onBindViewHolder(MasterStepsViewHolder holder, int position) {

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

        // Constructor
        // TODO
        public MasterStepsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_master_steps, parent, false));
        }
    }

}
