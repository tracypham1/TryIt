package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String steps;
    private String ing;
    private int id;

    //constructor
//    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps) {
//        name = _name;
////        ingredients = _ingredients;
//        steps = _steps;
//    }

    public Recipe(String _name, String _ingredients, String _steps, int _id) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
        id = _id;
    }

    //toString necessary for printing object information
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ing +
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

    public String getIng() { return ing; }
    public String getIngredientsString() {
        String listString = "";
        for (Ingredient i : ingredients)
        {
            listString += i.toString() + "\n";
        }
        return listString;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //other methods
    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }
    public void addIngredient(Ingredient _ingredient) {
        this.ingredients.add(_ingredient);
    }

}
