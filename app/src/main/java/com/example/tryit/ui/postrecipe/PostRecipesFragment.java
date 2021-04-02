package com.example.tryit.ui.postrecipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.tryit.R;
import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;
import com.google.android.material.textfield.TextInputLayout;
import android.graphics.Color;
import android.widget.Toast;
import android.view.LayoutInflater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnSuccessListener;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class PostRecipesFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String ingName, ingUnit, ingAmount, recName, steps;
    private Recipe recipe = new Recipe();

    String userID = auth.getCurrentUser().getUid();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_postrecipes, container, false);

        final Button butt_addIn = root.findViewById(R.id.button_add_in);
        final Button butt_postRec = root.findViewById(R.id.button_post_rec);
        final Button butt_saveRec = root.findViewById(R.id.button_save_rec);
        final CheckBox whole_checkbox = root.findViewById(R.id.checkBox_whole);

        final TextInputLayout ingName_layout = root.findViewById(R.id.in_name_layout);
        final TextInputLayout ingUnit_layout = root.findViewById(R.id.unit_input_layout);
        final TextInputLayout ingAmt_layout = root.findViewById(R.id.amount_input_layout);
        final TextInputLayout recName_layout = root.findViewById(R.id.rec_name_layout);
        final TextInputLayout steps_layout = root.findViewById(R.id.steps_layout);

        butt_saveRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recName = recName_layout.getEditText().getText().toString().trim();
                steps = steps_layout.getEditText().getText().toString().trim();

                //add recipe to db
                recipe.setName(recName);
                recipe.setSteps(steps);

                //set up ingredients map
                Map<String, ArrayList> ingMap = new HashMap<>();
                if(!recipe.getIngredients().isEmpty()) {
                    for (Ingredient i: recipe.getIngredients()) {
                        ArrayList list = new ArrayList<String>();
                        list.add(String.valueOf(i.amount));
                        list.add(i.unit);
                        ingMap.put(i.name, list);
                    }
                }

                //set up db map
                Map<String, Object> rec = new HashMap<>();
                rec.put("name", recName);
                rec.put("steps", steps);
                rec.put("ingredients", ingMap);

                String uploadID = db.collection("users").document(userID).collection("drafts").document().getId();
                rec.put("docID", uploadID);

                db.collection("users").document(userID).collection("drafts")
                        .document(uploadID).set(rec)
                        .addOnSuccessListener(new OnSuccessListener<Void> () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("Draft Saved!");
                                Toast.makeText(getActivity(), "Draft Saved!",
                                        Toast.LENGTH_SHORT).show();

                                //reset form
                                recName_layout.getEditText().getText().clear();
                                steps_layout.getEditText().getText().clear();
                                ingName_layout.getEditText().getText().clear();
                                ingAmt_layout.getEditText().getText().clear();
                                ingUnit_layout.getEditText().getText().clear();
                                whole_checkbox.setChecked(false);
                                ingUnit_layout.getEditText().setEnabled(true);
                                recipe = new Recipe();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error Occurred! Try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        butt_addIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingName = ingName_layout.getEditText().getText().toString().trim();
                if(!whole_checkbox.isChecked())    ingUnit = ingUnit_layout.getEditText().getText().toString().trim();
                ingAmount = ingAmt_layout.getEditText().getText().toString().trim();

                //if one of them is empty, display error message
                int emptyCount = 0;
                double amount = 0.0;

                if(ingName.isEmpty()) {
                    ingName_layout.setError("Enter name");
                    ingName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else  ingName_layout.setErrorEnabled(false);

                if(ingUnit.isEmpty() && !whole_checkbox.isChecked()) {
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
                    Toast.makeText(getActivity(), "Ingredient Added",
                            Toast.LENGTH_SHORT).show();
                }

                //reset form
                ingName_layout.getEditText().getText().clear();
                ingAmt_layout.getEditText().getText().clear();
                ingUnit_layout.getEditText().getText().clear();
                whole_checkbox.setChecked(false);
                ingUnit_layout.getEditText().setEnabled(true);

            }
        });

        butt_postRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recName = recName_layout.getEditText().getText().toString().trim();
                steps = steps_layout.getEditText().getText().toString().trim();

                int emptyCount = 0;

                if(recName.isEmpty()) {
                    recName_layout.setError("Enter name");
                    recName_layout.setErrorEnabled(true);
                    emptyCount++;
                } else recName_layout.setErrorEnabled(false);

                if(steps.isEmpty()) {
                    steps_layout.setError("Enter instructions");
                    steps_layout.setErrorEnabled(true);
                    emptyCount++;
                } else steps_layout.setErrorEnabled(false);

                if(emptyCount == 0 && !recipe.getIngredients().isEmpty()) {
                    butt_addIn.setBackgroundColor(Color.parseColor("#cccccc"));
                    butt_addIn.setTextColor(Color.BLACK);

                    ingName_layout.setErrorEnabled(false);
                    ingUnit_layout.setErrorEnabled(false);
                    ingAmt_layout.setErrorEnabled(false);
                    recName_layout.setErrorEnabled(false);
                    steps_layout.setErrorEnabled(false);

                    //add recipe to db
                    recipe.setName(recName);
                    recipe.setSteps(steps);

                    //set up ingredients map
                    Map<String, ArrayList> ingMap = new HashMap<>();
                    for (Ingredient i: recipe.getIngredients()) {
                        ArrayList list = new ArrayList<String>();
                        list.add(String.valueOf(i.amount));
                        list.add(i.unit);
                        ingMap.put(i.name, list);
                    }

                    //-------------------------TRACY INCLUDE THIS vv------------------------------------
                    //set up db map
                    final Map<String, Object> rec = new HashMap<>();
                    rec.put("name", recName);
                    rec.put("steps", steps);
                    rec.put("ingredients", ingMap);

                    final String uploadID = db.collection("recipes").document().getId();
                    rec.put("docID", uploadID);

                    db.collection("recipes")
                        .document(uploadID).set(rec)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("Recipe Posted!");
                                Toast.makeText(getActivity(), "Recipe Posted!",
                                    Toast.LENGTH_SHORT).show();

                                //upload recipe to user's 'posts' collection with uploadID
                                try {
                                    db.collection("users").document(userID).collection("posts").document(uploadID).set(rec);
                                } catch(ArithmeticException e) {
                                    System.out.println("Couldn't save to drafts");
                                }
                    //----------------------------------------------------------------------------

                                //reset form
                                recName_layout.getEditText().getText().clear();
                                steps_layout.getEditText().getText().clear();
                                ingName_layout.getEditText().getText().clear();
                                ingAmt_layout.getEditText().getText().clear();
                                ingUnit_layout.getEditText().getText().clear();
                                whole_checkbox.setChecked(false);
                                ingUnit_layout.getEditText().setEnabled(true);
                                recipe = new Recipe();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error Occurred! Try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                } else {
                    butt_addIn.setBackgroundColor(Color.parseColor("#dc143c"));
                    butt_addIn.setTextColor(Color.WHITE);
                }

            }
        });

        whole_checkbox.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(whole_checkbox.isChecked()) {
                    ingUnit_layout.getEditText().setEnabled(false);
                    ingUnit = "whole";
                } else ingUnit_layout.getEditText().setEnabled(true);
            }
        });

        return root;
    }
}