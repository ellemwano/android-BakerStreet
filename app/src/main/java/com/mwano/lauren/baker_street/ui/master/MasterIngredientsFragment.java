package com.mwano.lauren.baker_street.ui.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Provides UI for the view with the Ingredients for the selected recipe
 */
public class MasterIngredientsFragment extends Fragment {

    // Recipe includes a List of Ingredients
    public List<Ingredient> mIngredients = new ArrayList<Ingredient>();
    @BindView(R.id.rv_ingredients) RecyclerView mIngredientsRecyclerView;
    LinearLayoutManager mLayoutManager;

    public static final String INGREDIENT_KEY = "ingredients";

    // Constructor
    public MasterIngredientsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(INGREDIENT_KEY)) {
            mIngredients = getArguments().getParcelableArrayList(INGREDIENT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_ingredients, container, false);
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
        return rootView;
    }


    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param ingredients ArrayList of Ingredient objects of selected recipe in recipe list
     * @return fragment
     */
    public static MasterIngredientsFragment newInstance (ArrayList<Ingredient> ingredients) {
        MasterIngredientsFragment ingredientFragment = new MasterIngredientsFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(INGREDIENT_KEY, ingredients);
        ingredientFragment.setArguments(arguments);
        return ingredientFragment;
    }
}
