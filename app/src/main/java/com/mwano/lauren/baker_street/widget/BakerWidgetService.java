package com.mwano.lauren.baker_street.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.database.RecipeDatabase;
import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.utils.Utils;

import java.util.List;

import static com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity.RECIPE_ID;
import static com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity.RECIPE_NAME;
import static com.mwano.lauren.baker_street.ui.master.MasterRecipePagerActivity.SHARED_PREFS;

/**
 * We add the Service to the Manifest
 */
public class BakerWidgetService extends RemoteViewsService {


    private Context mContext;
    private List<Ingredient> mIngredientsListWidget;
    private SharedPreferences mPreferences;
    private int mRecipeId;
    private Recipe mRecipe;
    private String mRecipeName;
    private Ingredient mIngredient;
    private RecipeDatabase mRecipeDatabase;
    private RecipeRepository mRecipeRepository;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakerWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    /**
     * This class acts as an adapter for the ListView.
     */
    public class BakerWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        // Constructor
        public BakerWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            // Initialise SharedPreferences
            mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            // Instantiate Database
            mRecipeDatabase = RecipeDatabase.getDatabase(getApplicationContext());
            // Instantiate Repository
            mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());

            if (mPreferences.contains(RECIPE_ID)) {
                mRecipeId = mPreferences.getInt(RECIPE_ID, 1);
                mRecipeName = mPreferences.getString(RECIPE_NAME, "Nutella Cake");
                mRecipe = mRecipeRepository.getSingleRecipeForWidget(mRecipeId);
                mIngredientsListWidget = mRecipe.getIngredients();
            }
        }

        @Override
        public void onDestroy() {
        }

        // Similar to getItemCount() of a usual adapter
        @Override
        public int getCount() {
            if (mIngredientsListWidget == null) return 0;
            return mIngredientsListWidget.size();
        }

        // Similar to onBindViewHolder() of a usual adapter
        @Override
        public RemoteViews getViewAt(int position) {
            mIngredient = mIngredientsListWidget.get(position);
            RemoteViews remoteViews =
                    new RemoteViews(mContext.getPackageName(), R.layout.item_widget_list);
            // Update the textviews inside the remoteviews
            remoteViews.setTextViewText(R.id.tv_widget_ingredient, mIngredient.getIngredient());
            remoteViews.setTextViewText(R.id.tv_widget_quantity,
                    Utils.doubleToStringFormat(mIngredient.getQuantity()));
            remoteViews.setTextViewText(R.id.tv_widget_measure, mIngredient.getMeasure().toLowerCase());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        // Only 1 type of item view
        @Override
        public int getViewTypeCount() {
            return 1;
        }

        // Return position instead of 0 ensures each remoteViews id will be equal to its index in the List
        @Override
        public long getItemId(int position) {
            return position;
        }

        // return true because ids won't be changing
        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
