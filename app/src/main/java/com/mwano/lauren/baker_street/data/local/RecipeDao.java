
package com.mwano.lauren.baker_street.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

import retrofit2.http.DELETE;

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
    Recipe loadRecipeById(int id);

    // Get all recipes
    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes();

    // Delete all Recipes
    @Query("DELETE FROM recipe")
    void deleteAllRecipes();
}
