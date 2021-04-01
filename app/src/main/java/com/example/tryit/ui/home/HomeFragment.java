package com.example.tryit.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.android.gms.tasks.Task;

import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;

public class HomeFragment extends Fragment {
    //posts from DB
    private ArrayList<Recipe> posts = new ArrayList<Recipe>();
    private RecyclerView homeRecView;
    private RecipesAdapter adapter;

    //Firebase DB instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeRecView = root.findViewById(R.id.home_recyclerView);
        adapter = new RecipesAdapter();
        homeRecView.setAdapter(adapter);

        //get posts from db
        db.collection("recipes").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("Recipes retrieved");

                    //load posts into memory
                    loadPosts(task);

                    //create cards in home
                    adapter.submitList(posts);


                } else   Toast.makeText(getActivity(), "Error Occurred! Try again later.", Toast.LENGTH_SHORT).show();
                }
            });

        return root;
    }

    private void loadPosts(Task<QuerySnapshot> task) {
        for (QueryDocumentSnapshot document : task.getResult()) {
            //get data
            Map<String, Object> doc = document.getData();

            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
            Recipe rec = new Recipe();
            rec.setName(doc.get("name").toString()); //set rec name
            rec.setSteps(doc.get("steps").toString()); //set rec steps
            rec.setDocID(doc.get("docID").toString()); //set docID

            //get ingredients and cast as ingredient objects
            Map<String, ArrayList<String>> ingrFromDB = (Map<String, ArrayList<String>>) doc.get("ingredients");
            for(Map.Entry<String, ArrayList<String>> entry : ingrFromDB.entrySet()) {
                Ingredient ingredient = new Ingredient();
                ingredient.name = entry.getKey();
                ingredient.amount = Double.parseDouble(entry.getValue().get(0)); //cast to double
                ingredient.unit = entry.getValue().get(1);

                ingredients.add(ingredient);
            }
            //add ingredients to rec
            rec.setIngredients(ingredients);

            //add recipe to posts list
            posts.add(rec);

        }
    }

}