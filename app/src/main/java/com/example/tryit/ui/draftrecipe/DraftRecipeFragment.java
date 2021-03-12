package com.example.tryit.ui.draftrecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertController;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tryit.MainActivity;
import com.example.tryit.R;
import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;
import com.example.tryit.models.SQLDraftsDbHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DraftRecipeFragment extends Fragment {

    private DraftRecipeViewModel draftRecipeViewModel;

    //reference to buttons and controls
    Button btn_add_ing, btn_save;
    TextInputLayout in_rec_name, in_ing_name, in_ing_amt, in_ing_unit, in_directions;
    RecyclerView rv_drafts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        draftRecipeViewModel =
                ViewModelProviders.of(this).get(DraftRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_draftrecipe, container, false);

        //assign the references to the xml widgets
        btn_add_ing = root.findViewById(R.id.dr_button_add_in);
        btn_save = root.findViewById(R.id.dr_button_save);
        in_rec_name = root.findViewById(R.id.dr_rec_name_layout);
        in_ing_name = root.findViewById(R.id.dr_in_name_layout);
        in_ing_amt = root.findViewById(R.id.dr_amount_input_layout);
        in_ing_unit = root.findViewById(R.id.dr_unit_input_layout);
        in_directions = root.findViewById(R.id.dr_directions_layout);
        rv_drafts = root.findViewById(R.id.dr_rv_drafts);

        // recipe object created when page is opened
        final Recipe recipe = new Recipe();

        //add button listeners
        btn_add_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ingName = in_ing_name.getEditText().getText().toString();
                String ingAmount = in_ing_amt.getEditText().getText().toString();
                String ingUnit = in_ing_unit.getEditText().getText().toString();

                //if one of them is empty, display error message
                int emptyCount = 0;
                double amount = 0.0;

                if(ingName.isEmpty()) {
                    in_ing_name.setError("Enter name");
                    in_ing_name.setErrorEnabled(true);
                    emptyCount++;
                } else {
                    in_ing_name.setError(null);
                }

                if(ingAmount.isEmpty()) {
                    in_ing_amt.setError("Enter amount");
                    in_ing_amt.setErrorEnabled(true);
                    emptyCount++;
                } else {
                    in_ing_amt.setError(null);
                    amount = Double.parseDouble(ingAmount);
                }

                if(ingUnit.isEmpty()) {
                    in_ing_unit.setError("Enter unit");
                    in_ing_unit.setErrorEnabled(true);
                    emptyCount++;
                }else {
                    in_ing_unit.setError(null);
                }

                if(emptyCount == 0) {
                    Ingredient ing = new Ingredient(ingName, ingUnit, amount);
                    recipe.addIngredient(ing);

                    in_ing_name.setError(null);
                    in_ing_amt.setError(null);
                    in_ing_unit.setError(null);
                    Toast.makeText(getActivity(),"Ingredient Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLDraftsDbHelper sqlDraftsDbHelper = new SQLDraftsDbHelper(getActivity());

                String recName = in_rec_name.getEditText().getText().toString();
                String steps = in_directions.getEditText().getText().toString();
                String ingName = in_ing_name.getEditText().getText().toString();
                String ingAmount = in_ing_amt.getEditText().getText().toString();
                String ingUnit = in_ing_unit.getEditText().getText().toString();
                int emptyCount = 0;

                if(recName.isEmpty()) {
                    in_rec_name.setError("Enter name");
                    in_rec_name.setErrorEnabled(true);
                    emptyCount++;
                } else {
                    in_rec_name.setError(null);
                }

                if(steps.isEmpty()) {
                    in_directions.setError("Enter instructions");
                    in_directions.setErrorEnabled(true);
                    emptyCount++;
                } else {
                    in_directions.setError(null);
                }

                // when everything is filled out and ready to be added to db
                if((emptyCount == 0) && !recipe.getIngredients().isEmpty()) {
//                    recipe.setName(ingName);
//                    recipe.setSteps(steps);

                    //clear the error messages
                    in_rec_name.setError(null);
                    in_directions.setError(null);
                    in_ing_name.setError(null);
                    in_ing_amt.setError(null);
                    in_ing_unit.setError(null);

                    Toast.makeText(getActivity(),"Draft Saved!", Toast.LENGTH_SHORT).show();



                    //add to dp
                    boolean success = sqlDraftsDbHelper.addOne(recipe);
                    Toast.makeText(getActivity(),"Success = " + success, Toast.LENGTH_SHORT).show();

                    // show everything in db atm in toast
                    List<Recipe> allRecipes = sqlDraftsDbHelper.getAll();
                    Toast.makeText(getActivity(), allRecipes.toString(), Toast.LENGTH_LONG).show();

                    // else keep telling user there is an error
                } else {
                    if(ingName.isEmpty()) {
                        in_ing_name.setError("Enter name");
                        in_ing_name.setErrorEnabled(true);
                        emptyCount++;
                    } else {
                        in_ing_name.setError(null);
                    }

                    if(ingAmount.isEmpty()) {
                        in_ing_amt.setError("Enter amount");
                        in_ing_amt.setErrorEnabled(true);
                        emptyCount++;
                    } else {
                        in_ing_amt.setError(null);
                    }

                    if(ingUnit.isEmpty()) {
                        in_ing_unit.setError("Enter unit");
                        in_ing_unit.setErrorEnabled(true);
                        emptyCount++;
                    }else {
                        in_ing_unit.setError(null);
                    }

                    Toast.makeText(getActivity(),"Draft Incomplete (Ingredients Not Added)!", Toast.LENGTH_SHORT).show();

                    // show everything in db atm in toast
                    List<Recipe> allRecipes = sqlDraftsDbHelper.getAll();
                    Toast.makeText(getActivity(), allRecipes.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}