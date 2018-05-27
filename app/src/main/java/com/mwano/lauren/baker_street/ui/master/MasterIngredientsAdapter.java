package com.mwano.lauren.baker_street.ui.master;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.Utils;


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
        // mContext = parent.getContext();
        View mItemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_master_ingredients, parent, false);
        return new MasterIngredientsViewHolder(mItemView);
    }

    // check
    @Override
    public void onBindViewHolder(MasterIngredientsViewHolder holder, int position) {
        // mContext = holder.ingredientsTextView.getContext();
        Ingredient mIngredient = mIngredients.get(position);
        holder.ingredientsTextView.setText(mIngredient.getIngredient());
        holder.quantityTextView.setText(Utils.doubleToStringFormat(mIngredient.getQuantity()));
        holder.measureTextView.setText(mIngredient.getMeasure().toLowerCase());
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    public void setIngredientData(List<Ingredient> ingredientData) {
        mIngredients = ingredientData;
        notifyDataSetChanged();
    }


    // ViewHolder
    public static class MasterIngredientsViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientsTextView;
        public TextView quantityTextView;
        public TextView measureTextView;

        // Constructor
        public MasterIngredientsViewHolder(View view) {
            super(view);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.tv_ingredient);
            quantityTextView = (TextView) itemView.findViewById(R.id.tv_quantity);
            measureTextView = (TextView) itemView.findViewById(R.id.tv_measure);

        }
    }

}