
package com.mwano.lauren.baker_street.data.local;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mwano.lauren.baker_street.data.network.ApiClient;
import com.mwano.lauren.baker_street.data.network.ApiInterface;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mRecipes;
    private Recipe mRecipe;
    private LiveData<List<Ingredient>> mIngredientsList;

    private static final String TAG = RecipeRepository.class.getSimpleName();

    // Constructor
    RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
    }

    /**
     * Fetch all recipes from the Network via Retrofit
     */
    public LiveData<List<Recipe>> getRecipesFromNetwork() {
        final MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
        ApiInterface request = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Recipe>> call = request.getRecipes();

        // Asynchronous request
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                mRecipes.setValue(response.body());
                insertAllRecipes(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                //Toast.makeText
                //(MainActivity.this, "error message", Toast.LENGTH_LONG).show();
                Log.d(TAG, t.getMessage());
            }
        });
        return mRecipes;
    }

    /**
     * Get all recipes from the database
     *
     * @return the List of Recipes
     */
    @SuppressLint("StaticFieldLeak")
    public LiveData<List<Recipe>> getRecipesFromDb() {
        final MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
        new AsyncTask<Void, Void, List<Recipe>>() {
            @Override
            protected List<Recipe> doInBackground(Void... params) {
                return mRecipeDao.getAllRecipes();
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                mRecipes.setValue(recipes);
            }
        }.execute();
        return mRecipes;
    }

    /**
     * Insert all recipes to the database
     */
    @SuppressWarnings("unchecked")
    @SuppressLint("StaticFieldLeak")
    public void insertAllRecipes(final List<Recipe> recipes) {
        new AsyncTask<List<Recipe>, Void, Void>() {
            @Override
            protected final Void doInBackground(List<Recipe>... params) {
                deleteAllRecipesFromDb();
                mRecipeDao.insertAllRecipes(recipes);
                return null;
            }
        }.execute();
    }


    public Recipe loadSingleRecipeById (final int id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipe = mRecipeDao.getRecipeById(id);
                Log.d(TAG, "recipe by id " + mRecipe);
            }
        });
        return mRecipe;
    }

    // Fetch all Recipes, from Network or DB, according to internet connection.
    public LiveData<List<Recipe>> getRecipeList(boolean internetState) {
        if (internetState) {
            return getRecipesFromNetwork();
        } else {
            return getRecipesFromDb();
        }
    }

    // Delete all recipes
    public void deleteAllRecipesFromDb() {
        mRecipeDao.deleteAllRecipes();
    }
}











