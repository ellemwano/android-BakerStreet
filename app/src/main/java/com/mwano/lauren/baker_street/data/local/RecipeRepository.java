package com.mwano.lauren.baker_street.data.local;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mwano.lauren.baker_street.data.local.database.RecipeDao;
import com.mwano.lauren.baker_street.data.local.database.RecipeDatabase;
import com.mwano.lauren.baker_street.data.network.ApiClient;
import com.mwano.lauren.baker_street.data.network.ApiInterface;
import com.mwano.lauren.baker_street.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private static final Object LOCK = new Object();
    private static RecipeRepository sInstance;
    private RecipeDao mRecipeDao;
    private RecipeDatabase mRecipeDatabase;
    private LiveData<List<Recipe>> mRecipes;
    private LiveData<Recipe> mRecipe;

    private static final String TAG = RecipeRepository.class.getSimpleName();

    // Constructor
    private RecipeRepository(RecipeDatabase recipeDatabase, RecipeDao recipeDao) {
        mRecipeDatabase = recipeDatabase;
        mRecipeDao = recipeDao;
    }

    /**
     * Build a singleton of the RecipeDatabase
     * @param database the RecipeDatabase
     * @param dao the RecipeDao
     * @return a RecipeDatabase instance, sInstance
     */
    public synchronized static RecipeRepository getRepositoryInstance(RecipeDatabase database, RecipeDao dao) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipeRepository(database, dao);
                Log.d(TAG, "Made new repository");
            }
        }
        return sInstance;
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
                Log.d(TAG, t.getMessage());
            }
        });
        return mRecipes;
    }

    /**
     * Get all the recipes from the database
     * @return the List of Recipes
     */
    public LiveData<List<Recipe>> getRecipesFromDb() {
        mRecipes = mRecipeDao.getAllRecipes();
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


    /**
     * Query a Recipe by a given id.
     * @param id the id of the given Recipe
     * @return A LiveData Recipe object
     */
    public LiveData<Recipe> loadSingleRecipeById (final int id) {
        mRecipe = mRecipeDao.getRecipeById(id);
        return mRecipe;
    }

    /**
     * Query a Recipe by a given id, for the widget
     * @param id the id of the given Recipe
     * @return a Recipe object (not LiveData)
     */
    public Recipe getSingleRecipeForWidget (final int id) {
        return mRecipeDao.getRecipeByIdForWidget(id);
    }

    /**
     * Check the internet state
     * and decide whether to fetch the recipe List from the network or the database
     * @param internetState the network connection state: connected or not
     * @return a network or db getter according to the internet state
     */
    public LiveData<List<Recipe>> getRecipeList(boolean internetState) {
        if (internetState) {
            return getRecipesFromNetwork();
        } else {
            return getRecipesFromDb();
        }
    }

    /**
     * Delete all recipes from the database
     */
    public void deleteAllRecipesFromDb() {
        mRecipeDao.deleteAllRecipes();
    }
}












