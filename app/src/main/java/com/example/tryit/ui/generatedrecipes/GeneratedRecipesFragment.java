package com.example.tryit.ui.generatedrecipes;

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
import com.example.tryit.ui.draftrecipe.DraftRecipeViewModel;

import java.util.ArrayList;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class GeneratedRecipesFragment extends Fragment {

    private GeneratedRecipesViewModel generatedRecipesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        generatedRecipesViewModel =
                ViewModelProviders.of(this).get(GeneratedRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_generatedrecipes, container, false);
        final TextView textView = root.findViewById(R.id.text_generatedrecipes);
        generatedRecipesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}