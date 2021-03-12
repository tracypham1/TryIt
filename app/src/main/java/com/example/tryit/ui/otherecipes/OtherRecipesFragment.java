package com.example.tryit.ui.otherecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.R;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class OtherRecipesFragment extends Fragment {

    private OtherRecipesViewModel otherRecipesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        otherRecipesViewModel =
                ViewModelProviders.of(this).get(OtherRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_otherecipes, container, false);
        final TextView textView = root.findViewById(R.id.text_otherrecipes);
        otherRecipesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}