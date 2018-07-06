/*
 * Copyright (c) <2018> <Laurence Moineau>
 *
 * MIT Licence
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mwano.lauren.baker_street.ui.master;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.utils.Utils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        @BindView(R.id.tv_ingredient) TextView ingredientsTextView;
        @BindView(R.id.tv_quantity) TextView quantityTextView;
        @BindView(R.id.tv_measure) TextView measureTextView;

        // Constructor
        public MasterIngredientsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}