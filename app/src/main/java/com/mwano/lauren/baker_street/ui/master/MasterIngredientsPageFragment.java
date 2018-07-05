package com.mwano.lauren.baker_street.ui.master;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.data.local.IngredientStepViewModel;
import com.mwano.lauren.baker_street.data.local.IngredientStepViewModelFactory;
import com.mwano.lauren.baker_street.data.local.RecipeDatabase;
import com.mwano.lauren.baker_street.data.local.RecipeRepository;
import com.mwano.lauren.baker_street.model.Ingredient;
import com.mwano.lauren.baker_street.model.Recipe;
import com.mwano.lauren.baker_street.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Provides UI for the view with the Ingredients for the selected recipe
 */
public class MasterIngredientsPageFragment extends Fragment {

    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsRecyclerView;
    // Recipe includes a List of Ingredients
    public List<Ingredient> mIngredients = new ArrayList<>();
    //public List<Ingredient> mIngredients;
    LinearLayoutManager mLayoutManager;
    private RecipeDatabase mRecipeDatabase;
    private RecipeRepository mRecipeRepository;
    private int mRecipeId;
    private Recipe mCurrentRecipe;

    public static final String INGREDIENTS_LIST = "ingredients";
    public static final String TAG = MasterIngredientsPageFragment.class.getSimpleName();

    // Constructor
    public MasterIngredientsPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if(getArguments() != null && getArguments().containsKey(INGREDIENTS_LIST)) {
        //    mIngredients = getArguments().getParcelableArrayList(INGREDIENTS_LIST);
        //}
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_ingredients_page, container, false);
        ButterKnife.bind(this, rootView);
        // LinearLayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        // RecyclerView
        mIngredientsRecyclerView.setLayoutManager(mLayoutManager);
        mIngredientsRecyclerView.setHasFixedSize(true);
        //Adapter
        MasterIngredientsAdapter ingredientsAdapter =
                new MasterIngredientsAdapter(getContext(), mIngredients);
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);
        //ingredientsAdapter.setIngredientData(mIngredients);

        // ViewModel
        // Instantiate Database
        mRecipeDatabase = RecipeDatabase.getDatabase(getActivity());
        // Instantiate Repository
        mRecipeRepository = RecipeRepository.getRepositoryInstance(mRecipeDatabase, mRecipeDatabase.recipeDao());
       IngredientStepViewModelFactory factory =
                new IngredientStepViewModelFactory(mRecipeRepository, mRecipeId);
        final IngredientStepViewModel viewModel =
                ViewModelProviders.of(this, factory).get(IngredientStepViewModel.class);
        // Get the selected recipe from the ID from the intent
        viewModel.getSingleRecipe().observe(getActivity(), new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                mCurrentRecipe = recipe;
                Log.d(TAG, "Current Recipe from fragment = " + mCurrentRecipe);
                mIngredients = mCurrentRecipe.getIngredients();
            }
        });


        return rootView;
    }


    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param ingredients ArrayList of Ingredient objects of selected recipe in recipe list
     * @return fragment
     */
    public static MasterIngredientsPageFragment newIngredientsInstance(List<Ingredient> ingredients) {
        MasterIngredientsPageFragment ingredientFragment = new MasterIngredientsPageFragment();
        // Set the bundle arguments for the fragment.
        //Bundle arguments = new Bundle();
        //arguments.putParcelableArrayList(INGREDIENTS_LIST, new ArrayList<>(ingredients));
        //ingredientFragment.setArguments(arguments);
        return ingredientFragment;
    }
}
