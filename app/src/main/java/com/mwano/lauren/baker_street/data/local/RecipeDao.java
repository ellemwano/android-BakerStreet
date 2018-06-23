
package com.mwano.lauren.baker_street.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    // No insert / delete
    // Only query (select all or select one)
    // Update

    // Add LiveData<> wrapper to observable data

    // One-time load static data


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    // Get all recipes
    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    // Get single recipe by id
    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    LiveData<Recipe> loadRecipeById(int id);

    // Get ingredients
    @Query("SELECT ingredients_list FROM recipe WHERE recipeId = :id")
    LiveData<List<Ingredient>> loadIngredientsById(int id);
}
