
package com.mwano.lauren.baker_street.data.local;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mwano.lauren.baker_street.data.network.ApiClient;
import com.mwano.lauren.baker_street.data.network.ApiInterface;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipes;
    // add single recipe by id?
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
    @SuppressLint("StaticFieldLeak")
    public void insertAllRecipes(final List<Recipe> recipes) {
        new AsyncTask<List<Recipe>, Void, Void>() {
            @Override
            protected final Void doInBackground(List<Recipe>... params) {
                deleteAllRecipesFromDb();
//                for (Recipe recipe : params[0]) {
//                    mRecipeDao.insertRecipe(recipe);
//                }
                mRecipeDao.insertAllRecipes(recipes);
                //mRecipeDao.insertRecipe(recipes.get(1));
                return null;
            }
        }.execute();
    }


    // TODO Logic where get data
    // if no internet db and db full = db
    // if no internet and db empty = error, check connection
    // if internet and db empty = network

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


    // set logic in method here
    // in viewmodel, set livedata  method same name and return recipe list
    // in viewmodel, set livedata getter for recipe list

    // in mainactivity, call VMProviders.
    // call getter for all recipes from VM and add observer and override onChanged()
    // in onChanged(), set adapter, passing recipe list (+ remove observer?)








