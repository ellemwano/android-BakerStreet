package com.mwano.lauren.baker_street;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;


public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipes;
    private final RecipeAdapterOnClickHandler mRecipeClickHandler;

    // OnClickHandler interface
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe currentRecipe);
    }

    // MainRecipeAdapter constructor
    public MainRecipeAdapter(Context context, List<Recipe> recipes,
                             RecipeAdapterOnClickHandler recipeClickHandler) {
        mContext = context;
        mRecipes = recipes;
        mRecipeClickHandler = recipeClickHandler;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_recipe_card,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        mContext = holder.mNameTextView.getContext();
        Recipe currentRecipe = mRecipes.get(position);
        holder.mNameTextView.setText(currentRecipe.getName());
        holder.mServingsTextView.setText(String.valueOf(currentRecipe.getServings()));
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

    public void setRecipeData(List<Recipe> recipeData) {
        mRecipes = recipeData;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mNameTextView;
        public TextView mServingsTextView;


        //RecipeViewHolder constructor
        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView)itemView.findViewById(R.id.recipe_name_tv);
            mServingsTextView = (TextView)itemView.findViewById(R.id.servings_tv);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe currentRecipe = mRecipes.get(adapterPosition);
            mRecipeClickHandler.onClick(currentRecipe);
        }
    }
}

