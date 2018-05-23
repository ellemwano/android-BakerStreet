package com.mwano.lauren.baker_street;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.model.Ingredient;

import java.util.List;

public class MasterIngredientsAdapter
        extends RecyclerView.Adapter<MasterIngredientsAdapter.MasterIngredientsViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredients;

    // Constructor
    public MasterIngredientsAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    // check
    @Override
    public MasterIngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO
        return new MasterIngredientsViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    // check
    @Override
    public void onBindViewHolder(MasterIngredientsViewHolder holder, int position) {
        // TODO
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    public void setStepData(List<Ingredient> ingredientData) {
        mIngredients = ingredientData;
        notifyDataSetChanged();
    }


    // ViewHolder
    public static class MasterIngredientsViewHolder extends RecyclerView.ViewHolder {

        // Constructor
        //TODO
        public MasterIngredientsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_master_ingredients, parent, false));
        }
    }

}