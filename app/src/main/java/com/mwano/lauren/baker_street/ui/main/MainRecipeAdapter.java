package com.mwano.lauren.baker_street.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_main_recipe_card,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        mContext = holder.mNameTextView.getContext();
        Recipe currentRecipe = mRecipes.get(position);
        holder.mNameTextView.setText(currentRecipe.getName());
        holder.mServingsTextView.setText(String.valueOf(currentRecipe.getServings()));
        // Instantiate Picasso to handle the Recipe image
        Picasso mPicasso = Picasso.get();
        // Load recipe image if exists and non null, else load a placeholder
        if(!TextUtils.isEmpty(currentRecipe.getImage())){
            mPicasso.load(currentRecipe.getImage())
                    .placeholder(R.drawable.ingred)
                    .error(R.drawable.ingred)
                    .into(holder.mRecipeImageView);
        } else {
            mPicasso.load(R.drawable.ingred)
                    .placeholder(R.drawable.ingred)
                    .error(R.drawable.ingred)
                    .into(holder.mRecipeImageView);
        }
        // Add transparency to image
        holder.mRecipeImageView.setAlpha(0.85f);
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

        @BindView(R.id.recipe_iv) ImageView mRecipeImageView;
        @BindView(R.id.recipe_name_tv) TextView mNameTextView;
        @BindView(R.id.servings_tv) TextView mServingsTextView;


        //RecipeViewHolder constructor
        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe currentRecipe = mRecipes.get(adapterPosition);
            mRecipeClickHandler.onClick(currentRecipe);
        }
    }
}

