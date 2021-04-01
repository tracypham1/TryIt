package com.example.tryit.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String Thumbnail;
    private ArrayList<Ingredient> ingredients;
    private String steps;
    private String ing;
    private String id;

    //constructor
//    public Recipe(String _name, ArrayList<Ingredient> _ingredients, String _steps) {
//        name = _name;
////        ingredients = _ingredients;
//        steps = _steps;
//    }

    public Recipe(String _name, String _ingredients, String _steps, String _id, String _thumbnail) {
        name = _name;
        ing = _ingredients;
        steps = _steps;
        id = _id;
        Thumbnail=_thumbnail;
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


    public String getId() {
        return id;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    //other methods
    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }
    public void addIngredient(Ingredient _ingredient) {
        this.ingredients.add(_ingredient);
    }
 //   public String toString() {
 //       return getName();
  //  }

}
