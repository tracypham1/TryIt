package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String steps;
    private String docID;
    private String ing;
    private int id;

//    CONSTRUCTORS
    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps, String _docID) {
        name = _name;
        ingredients = _ingredients;
        steps = _steps;
        docID = _docID;
    }

    public Recipe(String _name, String _ingredients, String _steps, int _id) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
        id = _id;
    }

    public Recipe(String _name, String _ingredients, String _steps, String _docID) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
        docID = _docID;
    }

    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }

    //================================================

    // GETTERS
    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public int getId() {
        return id;
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
    public String getSteps() {
        return steps;
    }
    public String getDocID() { return docID; }

    //================================================

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setSteps(String steps) {
        this.steps = steps;
    }
    public void setDocID(String docID) { this.docID = docID; }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void setId(int id) {
        this.id = id;
    }

    //================================================

    // MEMBER FUNCTIONS
    //toString necessary for printing object information
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ing +
                ", steps='" + steps + '\'' +
                ", docID='" + docID +
                '}';
    }
    public void addIngredient(Ingredient _ingredient) {
        this.ingredients.add(_ingredient);
    }

}
