package com.example.tryit.ui.postrecipe;

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

public class PostRecipesFragment extends Fragment {

    private PostRecipesViewModel postRecipesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postRecipesViewModel =
                ViewModelProviders.of(this).get(PostRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_postrecipes, container, false);
        final TextView textView = root.findViewById(R.id.text_postrecipes);
        postRecipesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}