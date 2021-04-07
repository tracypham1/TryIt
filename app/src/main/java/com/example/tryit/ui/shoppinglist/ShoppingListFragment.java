package com.example.tryit.ui.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.R;
import com.example.tryit.models.Ingredient;
import com.example.tryit.models.SQLDraftsDbHelper;

import java.util.List;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    SQLDraftsDbHelper shoppingListDbHelper;

    //reference to buttons and stoof
    Button btn_add;
    ListView lv_shoppinglist;
    EditText in_ingredient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        final TextView textView = root.findViewById(R.id.text_shoppinglist);
        shoppingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btn_add = root.findViewById(R.id.sl_add);
        lv_shoppinglist = root.findViewById(R.id.sl_display);
        in_ingredient = root.findViewById(R.id.sl_ingredient);

        //ingredient when page is opened
        final Ingredient ingredient = new Ingredient();

        //show ingedients list immediately and everytime u add
        shoppingListDbHelper = new SQLDraftsDbHelper((getActivity()));
        List<Ingredient> ings = shoppingListDbHelper.sl_getAll();

        ArrayAdapter slArrayAdapter = new ArrayAdapter<Ingredient>(getActivity(), android.R.layout.simple_list_item_1, ings);
        lv_shoppinglist.setAdapter(slArrayAdapter);

        //button listeners
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            shoppingListDbHelper = new SQLDraftsDbHelper(getActivity());
            String ing = in_ingredient.getEditableText().toString();

            // if the ingredient box is empty
            if(ing.isEmpty()){
                in_ingredient.setError("Enter Ingredient");

            // if the ingredient box is NOT empty
            } else {
                // get rid of the error and add name to ingredient object
                in_ingredient.setError(null);
                ingredient.setName(ing);

                Log.d("sl", "ingredient object" + ingredient.toString());
                // add to datbasse so u dont lose info when app closed
                shoppingListDbHelper.sl_addOne(ingredient);

                //immediate update the list on page tooshoppingListDbHelper = new SQLDraftsDbHelper((getActivity()));
                List<Ingredient> ings = shoppingListDbHelper.sl_getAll();

                ArrayAdapter slArrayAdapter = new ArrayAdapter<Ingredient>(getActivity(), android.R.layout.simple_list_item_1, ings);
                lv_shoppinglist.setAdapter(slArrayAdapter);

            }

            }
        });

        lv_shoppinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient clickedItem = (Ingredient) parent.getItemAtPosition(position);
                shoppingListDbHelper.sl_deleteOne(clickedItem);

                //display new list
                List<Ingredient> ings = shoppingListDbHelper.sl_getAll();

                ArrayAdapter slArrayAdapter = new ArrayAdapter<Ingredient>(getActivity(), android.R.layout.simple_list_item_1, ings);
                lv_shoppinglist.setAdapter(slArrayAdapter);

            }
        });



        return root;
    }
}