
package com.mwano.lauren.baker_street.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwano.lauren.baker_street.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromIngredientsList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        Gson  gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        String json = gson.toJson(ingredients, type);
        return json;
    }

    @TypeConverter
    public List<Ingredient> toIngredientsList(String ingredientsString) {
        if (ingredientsString == null) {
            return null;
        }
        Gson  gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        List<Ingredient> ingredients = gson.fromJson(ingredientsString, type);
        return ingredients;
    }




}
