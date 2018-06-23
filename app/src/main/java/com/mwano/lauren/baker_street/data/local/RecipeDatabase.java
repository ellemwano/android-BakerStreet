
package com.mwano.lauren.baker_street.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
//@TypeConverters()
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase sInstance;
    private static final String DATABASE_NAME = "recipe database";
    private static final String TAG = RecipeDatabase.class.getSimpleName();

    // RecipeDao getter
    public abstract RecipeDao recipeDao();

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
