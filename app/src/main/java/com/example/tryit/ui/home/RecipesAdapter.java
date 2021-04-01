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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Color;

class RecipesAdapter extends ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder> {

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView recName, recIng;
        public ImageView recImage;
        public LinearLayout card;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recName = itemView.findViewById(R.id.recipe_name_textView);
            recIng = itemView.findViewById(R.id.recipe_ingr_textView);
            recImage = itemView.findViewById(R.id.recipe_imageView);
            card = itemView.findViewById(R.id.recipe_cardView);
        }

        public void bindTo(Recipe recipe) {
            recName.setText(recipe.getName());
            recName.setTextColor(Color.BLUE);

            String ingredientsString = "";
            ArrayList<String> ingredientArr = new ArrayList<String>();
            int ing_size = recipe.getIngredients().size();

            for(int j = 0; j < ing_size; j++) {
                Ingredient ingredient = recipe.getIngredients().get(j);
                String i = Character.toUpperCase(ingredient.name.charAt(0)) + ingredient.name.substring(1);
                if(j == (ing_size - 1)) ingredientsString += i;
                else ingredientsString += i + ", ";
            }
            recIng.setText(ingredientsString);
            recIng.setTextColor(Color.BLUE);
//            card.setOnClickListener((View item) -> {
//                Context context = item.getContext();
//                Intent i = new Intent(context, NoteActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//                i.putExtra("uuid", note.uuid); //need this to pass in recipe docID, change other func to store docID in recipe object
//                context.startActivity(i);
//            });
        }
    }

    public RecipesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipe_card_layout, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(itemView);
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

                    if(oldRecipe.getName() == newRecipe.getName()) return true;
                    else if(oldRecipe.getIngredients() == newRecipe.getIngredients()) return true;
                    else if(oldRecipe.getSteps() == newRecipe.getSteps()) return true;
                    else return false;
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Recipe oldRecipe, @NonNull Recipe newRecipe) {
                    if(oldRecipe.getName().equals(newRecipe.getName())) return true;
                    else if(oldRecipe.getIngredients().equals(newRecipe.getIngredients())) return true;
                    else if(oldRecipe.getSteps().equals(newRecipe.getSteps())) return true;
                    else return false;
                }
            };
}