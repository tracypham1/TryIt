package com.example.tryit.models;

public class Ingredient {
    public String name;
    public String unit;
    public double amount;
    private int id;

    public Ingredient(String _name, String _unit, double _amount) {
        name = _name;
        unit = _unit;
        amount = _amount;
    }

    public Ingredient() {}
    public Ingredient(String _name, int _id){
        name = _name;
        id = _id;
    }


    @Override
    public String toString() {
//        return "\nIngredient{" +
//                "name='" + name + '\'' +
//                ", unit='" + unit + '\'' +
//                ", amount=" + amount +
//                '}';
        return name;
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
}
