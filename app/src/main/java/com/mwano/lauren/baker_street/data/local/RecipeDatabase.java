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

@Database(entities = {Recipe.class, Ingredient.class, Step.class},
        version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase sInstance;
    private static final String DATABASE_NAME = "baker database";
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
