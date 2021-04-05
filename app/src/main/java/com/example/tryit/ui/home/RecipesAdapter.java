package com.example.tryit.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.tryit.R;
import com.example.tryit.models.Ingredient;
import com.example.tryit.models.Recipe;

import android.widget.LinearLayout;

class RecipesAdapter extends ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder> {

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        public TextView recName, recIng, recSteps;
        public LinearLayout card;

        public RecipeViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            recName = itemView.findViewById(R.id.recipe_name_textView);
            recIng = itemView.findViewById(R.id.recipe_ingr_textView);
            recSteps = itemView.findViewById(R.id.recipe_steps_textView);
            card = itemView.findViewById(R.id.recipe_cardView);
            mContext = context;
        }

        public void bindTo(Recipe recipe) {
            recName.setText(recipe.getName());

            String ingredientsString = "";
            ArrayList<String> ingredientArr = new ArrayList<String>();
            int ing_size = recipe.getIngredients().size();

            for(int j = 0; j < ing_size; j++) {
                Ingredient ingredient = recipe.getIngredients().get(j);

                String i = Character.toUpperCase(ingredient.name.charAt(0)) + ingredient.name.substring(1);
//                if(j == (ing_size - 1)) ingredientsString += i;
                ingredientsString += i + " (" + ingredient.amount + " " + ingredient.unit + ")\n";
            }
            recIng.setText(ingredientsString);

            String steps = "Instructions:\n" + recipe.getSteps();
//            int stepNum = 1;
//            int step_size = recipe.getSteps().length();
//            String s = recipe.getSteps();
//            int first = 0;
//            int last = 0;
//            for(int i = 0; i < step_size; i++) {
//                String formatted = "";
//                if(s.charAt(i) == '\n') {
//                    last = i - 1;
//                    formatted = s.substring(first, last);
//
//                    steps += stepNum++ + ". " + formatted + "\n";
//                    first = i + 1;
//                }
//
//                if( i == (step_size - 1) && first == 0) steps += s;
//
//            }
            recSteps.setText(steps);
        }
    }

    public RecipesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_card_layout, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(parent.getContext(), itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Recipe> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Recipe>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Recipe oldRecipe, @NonNull Recipe newRecipe) {

                    if(oldRecipe.getDocID() == newRecipe.getDocID()) return true;
                    else return false;
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Recipe oldRecipe, @NonNull Recipe newRecipe) {
                    if(oldRecipe.getDocID().equals(newRecipe.getDocID())) return true;
                    else return false;
                }
            };
}