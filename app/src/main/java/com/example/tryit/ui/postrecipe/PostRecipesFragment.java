package com.example.tryit.ui.postrecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.R;
import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;
import com.google.android.material.textfield.TextInputLayout;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class PostRecipesFragment extends Fragment {

    private PostRecipesViewModel postRecipesViewModel;
    java.util.ArrayList<Ingredient> ingredients = new java.util.ArrayList<Ingredient>();
    Recipe recipe = new Recipe();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_postrecipes, container, false);

        Button butt_addIn = root.findViewById(R.id.button_add_in);
        Button butt_postRec = root.findViewById(R.id.button_post_rec);
        Button butt_saveRec = root.findViewById(R.id.button_save_rec);

        final TextInputLayout ingName_layout = root.findViewById(R.id.in_name_layout);
        final TextInputLayout ingUnit_layout = root.findViewById(R.id.unit_input_layout);
        final TextInputLayout ingAmt_layout = root.findViewById(R.id.amount_input_layout);
        final TextInputLayout recName_layout = root.findViewById(R.id.rec_name_layout);
        final TextInputLayout steps_layout = root.findViewById(R.id.steps_layout);

        butt_addIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingName = ingName_layout.getEditText().getText().toString();
                String ingUnit = ingUnit_layout.getEditText().getText().toString();
                String ingAmount = ingAmt_layout.getEditText().getText().toString();

                //if one of them is empty, display error message
                int emptyCount = 0;
                double amount = 0.0;

                if(ingName.isEmpty()) {
                    ingName_layout.setError("Enter name");
                    ingName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else ingName_layout.setError(null);

                if(ingUnit.isEmpty()) {
                    ingUnit_layout.setError("Enter unit");
                    ingUnit_layout.setErrorEnabled(true);
                    emptyCount++;
                } else ingUnit_layout.setError(null);

                if(ingAmount.isEmpty()) {
                    ingAmt_layout.setError("Enter amount");
                    ingAmt_layout.setErrorEnabled(true);
                    emptyCount++;
                }  else {
                    ingAmt_layout.setError(null);
                    amount = Double.parseDouble(ingAmount);
                }

                if(emptyCount == 0) {
                    Ingredient ing = new Ingredient(ingName, ingUnit, amount);
                    recipe.addIngredient(ing);
                }


            }
        });

        butt_postRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ingName = ingName_layout.getEditText().getText().toString();
                String recName = recName_layout.getEditText().getText().toString();
                String steps = steps_layout.getEditText().getText().toString();

                int emptyCount = 0;

                if(recName.isEmpty()) {
                    recName_layout.setError("Enter name");
                    recName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else if(recName.startsWith(" ")) {
                    recName_layout.setError("Cannot start with a space");
                    recName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else recName_layout.setError(null);

                if(steps.isEmpty()) {
                    steps_layout.setError("Enter instructions");
                    steps_layout.setErrorEnabled(true);
                    emptyCount++;
                } else steps_layout.setError(null);

                if(emptyCount == 0 && !recipe.getIngredients().isEmpty()) {
                    recipe.setName(recName);
                    recipe.setSteps(steps);
                } else {
                    ingName_layout.setError("Add an ingredient");
                    ingName_layout.setErrorEnabled(true);

                    ingUnit_layout.setError("Add an ingredient");
                    ingUnit_layout.setErrorEnabled(true);

                    ingAmt_layout.setError("Add an ingredient");
                    ingAmt_layout.setErrorEnabled(true);
                }
            }
        });

        return root;
    }
}