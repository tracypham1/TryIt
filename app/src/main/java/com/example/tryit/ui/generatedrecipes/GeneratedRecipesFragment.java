package com.example.tryit.ui.generatedrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tryit.R;
import com.example.tryit.SearchResultsActivity;
import com.example.tryit.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

// http://users.csc.calpoly.edu/~djanzen/android/shoppinglist.html

public class GeneratedRecipesFragment extends Fragment {

    private List<Ingredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_generatedrecipes, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_search);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbarContact);
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecyclerViewAdapterIngredient myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);

        Button searchIngredients = RootView.findViewById(R.id.ingredients_search);
        searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tmp = RecyclerViewAdapterIngredient.ingredientsList;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), "You must select at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent searchResultsIntent = new Intent(getActivity(), SearchResultsActivity.class);
                    startActivity(searchResultsIntent);
                }
            }
        });


        return RootView;
    }

    private void initializeIngredients() {
        lstIngredient.add(new Ingredient("Beef",NULL,0,"beef-cubes-raw.png"));
        lstIngredient.add(new Ingredient("Fish",NULL,0, "fish-fillet.jpg"));
        lstIngredient.add(new Ingredient("Chicken",NULL,0, "chicken-breasts.png"));
        lstIngredient.add(new Ingredient("Tuna", NULL,0,"canned-tuna.png"));
        lstIngredient.add(new Ingredient("Flour", NULL,0,"flour.png"));
        lstIngredient.add(new Ingredient("Rice", NULL,0,"uncooked-white-rice.png"));
        lstIngredient.add(new Ingredient("Pasta", NULL,0,"fusilli.jpg"));
        lstIngredient.add(new Ingredient("Cheese", NULL,0,"cheddar-cheese.png"));
        lstIngredient.add(new Ingredient("Butter", NULL,0,"butter.png"));
        lstIngredient.add(new Ingredient("Bread", NULL,0,"white-bread.jpg"));
        lstIngredient.add(new Ingredient("Onion", NULL,0,"brown-onion.png"));
        lstIngredient.add(new Ingredient("Garlic", NULL,0,"garlic.jpg"));
        lstIngredient.add(new Ingredient("Milk", NULL,0,"milk.png"));
        lstIngredient.add(new Ingredient("Eggs", NULL,0,"egg.png"));
        lstIngredient.add(new Ingredient("Oil", NULL,0,"vegetable-oil.jpg"));
        lstIngredient.add(new Ingredient("Yogurt", NULL,0,"plain-yogurt.jpg"));
        lstIngredient.add(new Ingredient("Salt", NULL,0,"salt.jpg"));
        lstIngredient.add(new Ingredient("Sugar", NULL,0,"sugar-in-bowl.png"));
        lstIngredient.add(new Ingredient("Pepper", NULL,0,"pepper.jpg"));
        lstIngredient.add(new Ingredient("Water", NULL,0,"water.jpg"));
        lstIngredient.add(new Ingredient("Parsley", NULL,0,"parsley.jpg"));
        lstIngredient.add(new Ingredient("Basil", NULL,0,"basil.jpg"));
        lstIngredient.add(new Ingredient("Chocolate", NULL,0,"milk-chocolate.jpg"));
        lstIngredient.add(new Ingredient("Nuts", NULL,0,"hazelnuts.png"));
        lstIngredient.add(new Ingredient("Tomato", NULL,0,"tomato.png"));
        lstIngredient.add(new Ingredient("Cucumber", NULL,0,"cucumber.jpg"));
        lstIngredient.add(new Ingredient("Bell pepper",NULL,0, "red-bell-pepper.jpg"));
        lstIngredient.add(new Ingredient("Mushrooms", NULL,0,"portabello-mushrooms.jpg"));
        lstIngredient.add(new Ingredient("Lemon", NULL,0,"lemon.jpg"));
        lstIngredient.add(new Ingredient("Orange",NULL,0, "orange.jpg"));
        lstIngredient.add(new Ingredient("Banana",NULL,0, "bananas.jpg"));
        lstIngredient.add(new Ingredient("Wine", NULL,0,"red-wine.jpg"));
        lstIngredient.add(new Ingredient("Apple",NULL,0, "apple.jpg"));
        lstIngredient.add(new Ingredient("Berries", NULL,0,"berries-mixed.jpg"));
        lstIngredient.add(new Ingredient("Biscuits", NULL,0,"buttermilk-biscuits.jpg"));
        lstIngredient.add(new Ingredient("Pineapple",NULL,0, "pineapple.jpg"));
    }


}