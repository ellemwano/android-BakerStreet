
package com.mwano.lauren.baker_street.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mwano.lauren.baker_street.model.Recipe;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//@Entity(tableName = "ingredient", foreignKeys = @ForeignKey(entity = Recipe.class,
//        parentColumns = "recipeId", childColumns = "keyRecipeId", onDelete = CASCADE))
public class IngredientsPerRecipe {

//    private double quantity;
//
//    private String measure;
//
//    @PrimaryKey
//    @NonNull
//    private String ingredient;
//
//    private int keyRecipeId; // Points to a Recipe
//
//    // Constructor
//    public IngredientsPerRecipe() {
//    }
//
//    // Getters and Setters
//    public double getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(double quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getMeasure() {
//        return measure;
//    }
//
//    public void setMeasure(String measure) {
//        this.measure = measure;
//    }
//
//    public String getIngredient() {
//        return ingredient;
//    }
//
//    public void setIngredient(String ingredient) {
//        this.ingredient = ingredient;
//    }
//
//    public int getKeyRecipeId() {
//        return keyRecipeId;
//    }
//
//    public void setKeyRecipeId(int keyRecipeId) {
//        this.keyRecipeId = keyRecipeId;
//    }
}
