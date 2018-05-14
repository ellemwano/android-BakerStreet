package com.mwano.lauren.baker_street;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipes;

    // RecipeAdapter constructor
    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        mContext = holder.mNameTextView.getContext();
        holder.mNameTextView.setText(mRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

//    public void setRecipeData(List<Recipe> recipeData) {
//        mRecipes = recipeData;
//        notifyDataSetChanged();
//    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView;
        //public TextView mNameTitleTextView;


        //RecipeViewHolder constructor
        public RecipeViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.tv_name);
            //mNameTitleTextView = (TextView)itemView.findViewById(R.id.recipe_name);
        }
    }
}

