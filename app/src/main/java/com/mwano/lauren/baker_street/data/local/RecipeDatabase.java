
package com.mwano.lauren.baker_street.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.model.Step;

//@Database(entities = {Recipe.class, IngredientsPerRecipe.class},
//        version = 1, exportSchema = false)

@Database(entities = {Recipe.class, Ingredient.class, Step.class},
        version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase sInstance;
    private static final String DATABASE_NAME = "baker database";
    private static final String TAG = RecipeDatabase.class.getSimpleName();

    // RecipeDao getter
    public abstract RecipeDao recipeDao();

    // IngredientsPerRecipeDao getter
   // public abstract IngredientRecipeDao ingredientRecipeDao();

    // Build a singleton of the RecipeDatabase
    public static RecipeDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (RecipeDatabase.class) {
                if (sInstance == null) {
                    Log.d(TAG, "Creating new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, RecipeDatabase.DATABASE_NAME)
                            .build();
                }
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }
}
