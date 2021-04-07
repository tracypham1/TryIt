package com.example.tryit.models;

public class Ingredient {
    public String name;
    public String unit;
    public double amount;
    private String Thumbnail;
    private boolean selected;
    private int id;

    public Ingredient(String name, String _unit,double _amount,String thumbnail) {
        this.name = name;
        Thumbnail = "https://spoonacular.com/cdn/ingredients_100x100/" + thumbnail;
        selected = false;
        amount = _amount;
        unit=_unit;
    }

    public Ingredient() {}
    public Ingredient(String _name, int _id){
        name = _name;
        id = _id;
    }

    @Override
    public String toString() {
        return "\nIngredient{" +
                "name=(" + name + ')' +
                ", unit=(" + unit + ')' +
                ", amount=(" + amount +
                ")}\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

