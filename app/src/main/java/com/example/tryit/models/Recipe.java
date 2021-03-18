package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    public String name;
    public ArrayList<Ingredient> ingredients;
    public String steps;

    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps) {
        name = _name;
        ingredients = _ingredients;
        steps = _steps;
    }

    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }

    public void addIngredient(Ingredient _ingredient) {
        ingredients.add(_ingredient);
    }

//    public String getName() {
//        return this.name;
//    }
//
//    public ArrayList<Ingredient> getIngredients() {
//        return this.ingredients;
//    }
//
//    public ArrayList<String> steps() {
//        return this.steps;
//    }
}
