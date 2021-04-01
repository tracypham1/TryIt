package com.example.tryit.models;

public class Ingredient {
    public String name;
    public String unit;
    public double amount;
    private String Thumbnail;
    private boolean selected;

    public Ingredient(String name, String _unit,double _amount,String thumbnail) {
        this.name = name;
        Thumbnail = "https://spoonacular.com/cdn/ingredients_100x100/" + thumbnail;
        selected = false;
        amount = _amount;
        unit=_unit;
    }

//    public Ingredient() {}

    @Override
    public String toString() {
        return "\nIngredient{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected() {
        selected = !selected;
    }
}

