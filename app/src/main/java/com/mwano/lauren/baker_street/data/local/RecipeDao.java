
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

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    // Insert a single Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    // Insert all Recipes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllRecipes(List<Recipe> recipes);

    // Get single recipe by id
    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    LiveData<Recipe> getRecipeById(int id);

    // Get single recipe by id for the widget (no live data)
    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    Recipe getRecipeByIdForWidget(int id);

    // Get all recipes
    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    // Delete all Recipes
    @Query("DELETE FROM recipe")
    void deleteAllRecipes();
}
