package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String steps;
    private String docID;
    private String ing;

    //constructor
    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps, String _docID) {
        name = _name;
        ingredients = _ingredients;
        steps = _steps;
        docID = _docID;
    }

    public Recipe(String _name, String _ingredients, String _steps) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
    }

    public Recipe(String _name, String _ingredients, String _steps, String _docID) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
        docID = _docID;
    }

    //toString necessary for printing object information
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps='" + steps + '\'' +
                ", docID='" + docID +
                '}';
    }

    //getters
    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public String getSteps() {
        return steps;
    }
    public String getDocID() { return docID; }

    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void setSteps(String steps) {
        this.steps = steps;
    }
    public void setDocID(String docID) { this.docID = docID; }

    //other methods
    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }
    public void addIngredient(Ingredient _ingredient) {
        this.ingredients.add(_ingredient);
    }

}
