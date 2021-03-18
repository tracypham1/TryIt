package com.example.tryit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;
import com.google.android.material.textfield.TextInputLayout;
import android.graphics.Color;

import org.w3c.dom.Text;

public class PostRecipe extends AppCompatActivity {
    String ingName, ingUnit, ingAmount, recName, steps;

    java.util.ArrayList<Ingredient> ingredients = new java.util.ArrayList<Ingredient>();
    Recipe recipe = new Recipe();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recipe);

        final Button butt_addIn = findViewById(R.id.button_add_in);
        final Button butt_postRec = findViewById(R.id.button_post_rec);
        final Button butt_saveRec = findViewById(R.id.button_save_rec);

        final TextInputLayout ingName_layout = findViewById(R.id.in_name_layout);
        final TextInputLayout ingUnit_layout = findViewById(R.id.unit_input_layout);
        final TextInputLayout ingAmt_layout = findViewById(R.id.amount_input_layout);
        final TextInputLayout recName_layout = findViewById(R.id.rec_name_layout);
        final TextInputLayout steps_layout = findViewById(R.id.steps_layout);




        butt_addIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingName = ingName_layout.getEditText().getText().toString().trim();
                ingUnit = ingUnit_layout.getEditText().getText().toString().trim();
                ingAmount = ingAmt_layout.getEditText().getText().toString().trim();

                //if one of them is empty, display error message
                int emptyCount = 0;
                double amount = 0.0;

                if(ingName.isEmpty()) {
                    ingName_layout.setError("Enter name");
                    ingName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  ingName_layout.setErrorEnabled(false);

                if(ingUnit.isEmpty()) {
                    ingUnit_layout.setError("Enter unit");
                    ingUnit_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  ingUnit_layout.setErrorEnabled(true);

                if(ingAmount.isEmpty()) {
                    ingAmt_layout.setError("Enter amount");
                    ingAmt_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  {
                    amount = Double.parseDouble(ingAmount);
                    ingAmt_layout.setErrorEnabled(false);
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
                String recName = recName_layout.getEditText().getText().toString().trim();
                String steps = steps_layout.getEditText().getText().toString().trim();

                int emptyCount = 0;

                if(recName.isEmpty() || recName.trim().startsWith(" ")) {
                    recName_layout.setError("Enter name");
                    recName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else recName_layout.setErrorEnabled(false);

                if(steps.isEmpty() || steps.trim().startsWith(" ")) {
                    steps_layout.setError("Enter instructions");
                    steps_layout.setErrorEnabled(true);
                    emptyCount++;
                } else steps_layout.setErrorEnabled(false);

                if(emptyCount == 0 && !recipe.ingredients.isEmpty()) {
                    ingName_layout.setErrorEnabled(false);
                    ingUnit_layout.setErrorEnabled(false);
                    ingAmt_layout.setErrorEnabled(false);

                    recipe.name = recName;
                    recipe.steps = steps;
                } else {
                    butt_addIn.setBackgroundColor(Color.parseColor("#dc143c"));
                    butt_addIn.setTextColor(Color.WHITE);
                }
            }
        });

        final CheckBox whole_checkbox = (CheckBox) findViewById(R.id.checkBox_whole);
        whole_checkbox.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(whole_checkbox.isChecked()) {
                    ingUnit_layout.getEditText().setEnabled(false);
                    ingUnit = "whole";
                } else ingUnit_layout.getEditText().setEnabled(true);
            }
        });


    }
}