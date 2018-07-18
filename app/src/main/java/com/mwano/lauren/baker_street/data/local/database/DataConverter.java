package com.mwano.lauren.baker_street.data.local.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Step;

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

    @TypeConverter
    public String fromStepsList(List<Step> steps) {
        if (steps == null) {
            return null;
        }
        Gson  gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        String json = gson.toJson(steps, type);
        return json;
    }

    @TypeConverter
    public List<Step> toStepsList(String stepsString) {
        if (stepsString == null) {
            return null;
        }
        Gson  gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        List<Step> steps = gson.fromJson(stepsString, type);
        return steps;
    }




}
