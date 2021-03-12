package com.example.tryit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class PostRecipe extends AppCompatActivity {
    java.util.ArrayList<Ingredient> ingredients = new java.util.ArrayList<Ingredient>();
    Recipe recipe = new Recipe();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recipe);

        Button butt_addIn = findViewById(R.id.button_add_in);
        Button butt_postRec = findViewById(R.id.button_post_rec);
        Button butt_saveRec = findViewById(R.id.button_save_rec);

        butt_addIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout ingName_layout = (TextInputLayout) findViewById(R.id.in_name_layout);
                TextInputLayout ingUnit_layout = (TextInputLayout) findViewById(R.id.unit_input_layout);
                TextInputLayout ingAmt_layout = (TextInputLayout) findViewById(R.id.amount_input_layout);

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
                }

                if(ingUnit.isEmpty()) {
                    ingUnit_layout.setError("Enter unit");
                    ingUnit_layout.setErrorEnabled(true);
                    emptyCount++;
                }

                if(ingAmount.isEmpty()) {
                    ingAmt_layout.setError("Enter amount");
                    ingAmt_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  amount = Double.parseDouble(ingAmount);

                if(emptyCount == 0) {
                    Ingredient ing = new Ingredient(ingName, ingUnit, amount);
                    recipe.addIngredient(ing);
//                    ingredients.add(ing);
                }


            }
        });

        butt_saveRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout ingName_layout = (TextInputLayout) findViewById(R.id.in_name_layout);
                TextInputLayout ingUnit_layout = (TextInputLayout) findViewById(R.id.unit_input_layout);
                TextInputLayout ingAmt_layout = (TextInputLayout) findViewById(R.id.amount_input_layout);
                TextInputLayout recName_layout = (TextInputLayout) findViewById(R.id.rec_name_layout);
                TextInputLayout steps_layout = (TextInputLayout) findViewById(R.id.steps_layout);

                String ingName = ingName_layout.getEditText().getText().toString();
                String recName = recName_layout.getEditText().getText().toString();
                String steps = steps_layout.getEditText().getText().toString();

                int emptyCount = 0;

                if(recName.isEmpty()) {
                    ingUnit_layout.setError("Enter name");
                    ingUnit_layout.setErrorEnabled(true);
                    emptyCount++;
                }

                if(steps.isEmpty()) {
                    ingUnit_layout.setError("Enter instructions");
                    ingUnit_layout.setErrorEnabled(true);
                    emptyCount++;
                }

                if(emptyCount == 0 && !recipe.ingredients.isEmpty()) {
                    recipe.name = ingName;
                    recipe.steps = steps;
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


    }
}