package com.example.tryit.ui.draftrecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import java.util.Collection;
import java.util.Map;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
































































































































































































































































































public class DraftRecipeFragment extends Fragment {

    private DraftRecipeViewModel draftRecipeViewModel;
    SQLDraftsDbHelper draftsDbHelper;

    //reference to buttons and controls
    Button btn_add_ing, btn_save, btn_view_drafts;
    String ingName, ingAmount, ingUnit;
    TextInputLayout in_rec_name, in_ing_name, in_ing_amt, in_ing_unit, in_directions;
    CheckBox whole_checkBox;
    ListView rv_drafts;

    //popup window
    Button btn_cancel, btn_post, btn_delete;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        draftRecipeViewModel =
                ViewModelProviders.of(this).get(DraftRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_draftrecipe, container, false);

        //assign the references to the xml widgets
        btn_add_ing = root.findViewById(R.id.dr_button_add_in);
        btn_save = root.findViewById(R.id.dr_button_save);
        btn_view_drafts = root.findViewById(R.id.dr_view_drafts);
        in_rec_name = root.findViewById(R.id.dr_rec_name_layout);
        in_ing_name = root.findViewById(R.id.dr_in_name_layout);
        in_ing_amt = root.findViewById(R.id.dr_amount_input_layout);
        in_ing_unit = root.findViewById(R.id.dr_unit_input_layout);
        in_directions = root.findViewById(R.id.dr_directions_layout);
        rv_drafts = root.findViewById(R.id.dr_rv_drafts);
        whole_checkBox = root.findViewById(R.id.dr_whole_checkBox);

        // recipe object created when page is opened
        final Recipe recipe = new Recipe();

        //add button listeners
        btn_add_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ingName = in_ing_name.getEditText().getText().toString().trim();
                ingAmount = in_ing_amt.getEditText().getText().toString().trim();
                ingUnit = in_ing_unit.getEditText().getText().toString().trim();

                //if one of them is empty, display error message
                int emptyCount = 0;
                double amount = 0.0;

                if(ingName.isEmpty()) {
                    in_ing_name.setError("Enter name");
                    in_ing_name.setErrorEnabled(true);
                    emptyCount++;
                } else {
//                    in_ing_name.setError(null);
                    in_ing_name.setErrorEnabled(false);
                }

                if(ingAmount.isEmpty()) {
                    in_ing_amt.setError("Enter amount");
                    in_ing_amt.setErrorEnabled(true);
                    emptyCount++;
                } else {
//                    in_ing_amt.setError(null);
                    in_ing_amt.setErrorEnabled(false);
                    amount = Double.parseDouble(ingAmount);
                }

                if(ingUnit.isEmpty() && !whole_checkBox.isChecked()) {
                    in_ing_unit.setError("Enter unit");
                    in_ing_unit.setErrorEnabled(true);
                    emptyCount++;
                }else {
//                    in_ing_unit.setError(null);
                    in_ing_unit.setErrorEnabled(false);
                }

                if(emptyCount == 0) {
                    Ingredient ing = new Ingredient(ingName, ingUnit, amount);
                    recipe.addIngredient(ing);

//                    in_ing_name.setError(null);
//                    in_ing_amt.setError(null);
//                    in_ing_unit.setError(null);
                    in_ing_name.setErrorEnabled(false);
                    in_ing_amt.setErrorEnabled(false);
                    in_ing_unit.setErrorEnabled(false);

                    in_ing_name.getEditText().getText().clear();
                    in_ing_unit.getEditText().getText().clear();
                    in_ing_amt.getEditText().getText().clear();
                    whole_checkBox.setChecked(false);
                    in_ing_unit.getEditText().setEnabled(true);

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
                if(!whole_checkBox.isChecked())    ingUnit = in_ing_unit.getEditText().getText().toString().trim();

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

                    //add to recipe object and dp
                    recipe.setName(recName);
                    recipe.setSteps(steps)  ;

                    boolean success = sqlDraftsDbHelper.addOne(recipe);
                    Log.d("parse", "save_rec() -- recipe.getIngredient = \n" + recipe.getIngredients());

                    // show everything in db atm in toast
                    List<Recipe> allRecipes = sqlDraftsDbHelper.getAll();
                    //Toast.makeText(getActivity(), allRecipes.toString(), Toast.LENGTH_LONG).show();

                    // final draft saved
                    Toast.makeText(getActivity(),"Draft Saved!", Toast.LENGTH_SHORT).show();

                    in_rec_name.getEditText().getText().clear();
                    in_directions.getEditText().getText().clear();
                    in_ing_name.getEditText().getText().clear();
                    in_ing_unit.getEditText().getText().clear();
                    in_ing_amt.getEditText().getText().clear();
                    whole_checkBox.setChecked(false);
                    in_ing_unit.setEnabled(true);

                    //show list
                    draftsDbHelper = new SQLDraftsDbHelper(getActivity());
                    List<Recipe> drafts = draftsDbHelper.getAll();

                    ArrayAdapter draftsArrayAdapter = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, drafts);
                    rv_drafts.setAdapter(draftsArrayAdapter);

                    // else keep telling user there is an error
                } else {
                    if(ingName.isEmpty()) {
                        in_ing_name.setError("Enter name");
                        in_ing_name.setErrorEnabled(true);
                        emptyCount++;
                    } else {
//                        in_ing_name.setError(null);
                        in_ing_name.setErrorEnabled(false);
                    }

                    if(ingAmount.isEmpty()) {
                        in_ing_amt.setError("Enter amount");
                        in_ing_amt.setErrorEnabled(true);
                        emptyCount++;
                    } else {
//                        in_ing_amt.setError(null);
                        in_ing_amt.setErrorEnabled(false);
                    }

                    if(ingUnit.isEmpty() && !whole_checkBox.isChecked()) {
                        in_ing_unit.setError("Enter unit");
                        in_ing_unit.setErrorEnabled(true);
                        emptyCount++;
                    }else {
//                        in_ing_unit.setError(null);
                        in_ing_unit.setErrorEnabled(false);
                    }

                    Toast.makeText(getActivity(),"Draft Incomplete (Ingredients Not Added)!", Toast.LENGTH_SHORT).show();

                    // show everything in db atm in toast
                    // List<Recipe> allRecipes = sqlDraftsDbHelper.getAll();
                    // Toast.makeText(getActivity(), allRecipes.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


        btn_view_drafts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                draftsDbHelper = new SQLDraftsDbHelper(getActivity());
                List<Recipe> drafts = draftsDbHelper.getAll();

                ArrayAdapter draftsArrayAdapter = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, drafts);
                rv_drafts.setAdapter(draftsArrayAdapter);

            }
        });

        rv_drafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                //popupwindow
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_pop, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                // define buttons
                btn_cancel = (Button) popupView.findViewById(R.id.btn_dr_cancel);
                btn_post = (Button) popupView.findViewById(R.id.btn_dr_post);
                btn_delete = (Button) popupView.findViewById(R.id.btn_dr_delete);

                // finally show up your popwindow
                popupWindow.showAsDropDown(popupView, 0, 0);

                final Recipe clickedRecipe = (Recipe) parent.getItemAtPosition(position);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete from list
                        draftsDbHelper.deleteOne(clickedRecipe);

                        //display new list
                        List<Recipe> drafts = draftsDbHelper.getAll();
                        ArrayAdapter draftsArrayAdapter = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, drafts);
                        rv_drafts.setAdapter(draftsArrayAdapter);

                        //toast
                        Toast.makeText(getActivity(), "Deleted " + clickedRecipe.toString(), Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                btn_post.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //adding this item in list to firebase
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final FirebaseAuth auth = FirebaseAuth.getInstance();
                        final String userID = auth.getCurrentUser().getUid();


                        //set up db map
                        final Map<String, Object> rec = new HashMap<>();
                        rec.put("name", clickedRecipe.getName());
                        rec.put("steps", clickedRecipe.getSteps());

                        //parse the ingredient string before put in Firebase
//                        Log.d("parseIng", clickedRecipe.getIng());
                        Map<String, ArrayList<String>> fb_ingredients = new HashMap<>();//Map<String, ArrayList<String>>();
//                        {
//                            @Override
//                            public int size() {
//                                return 0;
//                            }
//
//                            @Override
//                            public boolean isEmpty() {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean containsKey(@Nullable Object key) {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean containsValue(@Nullable Object value) {
//                                return false;
//                            }
//
//                            @Nullable
//                            @Override
//                            public ArrayList<String> get(@Nullable Object key) {
//                                return null;
//                            }
//
//                            @Nullable
//                            @Override
//                            public ArrayList<String> put(String key, ArrayList<String> value) {
//                                return null;
//                            }
//
//                            @Nullable
//                            @Override
//                            public ArrayList<String> remove(@Nullable Object key) {
//                                return null;
//                            }
//
//                            @Override
//                            public void putAll(@NonNull Map<? extends String, ? extends ArrayList<String>> m) {
//
//                            }
//
//                            @Override
//                            public void clear() {
//
//                            }
//
//                            @NonNull
//                            @Override
//                            public Set<String> keySet() {
//                                return null;
//                            }
//
//                            @NonNull
//                            @Override
//                            public Collection<ArrayList<String>> values() {
//                                return null;
//                            }
//
//                            @NonNull
//                            @Override
//                            public Set<Entry<String, ArrayList<String>>> entrySet() {
//                                return null;
//                            }
//                        };
                        StringBuffer all_ingredients = new StringBuffer(clickedRecipe.getIng());
                        Log.d("parIng", "allIng == " + all_ingredients);

                        int next_ing = 1;
                        while(next_ing > 0){
                            //get first quote
                            String temp_name = all_ingredients.substring(all_ingredients.indexOf("(")+1, all_ingredients.indexOf(")"));
                            all_ingredients.replace(all_ingredients.indexOf("("), all_ingredients.indexOf(")")+1, "");
                            Log.d("parIng", "allIng_after_name == " + all_ingredients);

                            String temp_unit = all_ingredients.substring(all_ingredients.indexOf("(")+1, all_ingredients.indexOf(")"));
                            all_ingredients.replace(all_ingredients.indexOf("("), all_ingredients.indexOf(")")+1, "");

                            String temp_amt = all_ingredients.substring(all_ingredients.indexOf("(")+1, all_ingredients.indexOf(")"));
                            all_ingredients.replace(all_ingredients.indexOf("("), all_ingredients.indexOf(")")+1, "");

                            Log.d("parIng"," -- name amt unit -- " + temp_name + temp_amt + temp_unit + "\n");

                            //add to map to send to firebsae
                            ArrayList<String> temp_ing_info = new ArrayList<String>();
                            temp_ing_info.add(temp_amt);
                            temp_ing_info.add(temp_unit);
                            fb_ingredients.put(temp_name, temp_ing_info);

                            //get next one
                            next_ing = all_ingredients.indexOf("name=(");
                            Log.d("fb_rec", temp_ing_info.get(0));

                        }

                        rec.put("ingredients", fb_ingredients);
//                        rec.put("ingredients", clickedRecipe.getIng());

                        //put the recipe into firebase
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
                                        } catch (ArithmeticException e) {
                                            System.out.println("Couldn't save to drafts");
                                        }
                                    }
                                });

                        //delete from list and display new one
                        draftsDbHelper.deleteOne(clickedRecipe);
                        List<Recipe> drafts = draftsDbHelper.getAll();
                        ArrayAdapter draftsArrayAdapter = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, drafts);
                        rv_drafts.setAdapter(draftsArrayAdapter);

                        //dismiss window
                        Toast.makeText(getActivity(), "Posted " + clickedRecipe.toString(), Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

            }
        });

        whole_checkBox.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(whole_checkBox.isChecked()) {
                    in_ing_unit.getEditText().setEnabled(false);
                    ingUnit = "whole";
                } else in_ing_unit.getEditText().setEnabled(true);
            }
        });

        return root;
    }
}