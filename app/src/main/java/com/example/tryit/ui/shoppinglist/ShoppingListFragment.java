package com.example.tryit.ui.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.R;

import java.util.ArrayList;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        // list view stuff in fragment_shoppoinglist.xml
        ArrayList<String> data = new ArrayList<>();
        data.add("Indredient 1");
        data.add("Indredient 2");
        data.add("Indredient 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                data);
        ListView lvData = (ListView) root.findViewById(R.id.shoppinglist);
        lvData.setAdapter(adapter);

        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        final TextView textView = root.findViewById(R.id.text_shoppinglist);
        shoppingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}