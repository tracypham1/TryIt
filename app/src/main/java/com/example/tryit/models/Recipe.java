package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String steps;
    private String ing;

    //constructor
    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps) {
        name = _name;
        ingredients = _ingredients;
        steps = _steps;
    }

    public Recipe(String _name, String _ingredients, String _steps) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
    }

    //toString necessary for printing object information
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps='" + steps + '\'' +
                '}';
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public String getSteps() {
        return steps;
    }
    public void setSteps(String steps) {
        this.steps = steps;
    }

    //other methods
    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }
    public void addIngredient(Ingredient _ingredient) {
        this.ingredients.add(_ingredient);
    }

}
