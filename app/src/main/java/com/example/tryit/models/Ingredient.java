package com.example.tryit.models;

public class Ingredient {
    public String name;
    public String unit;
    public double amount;

    public Ingredient(String _name, String _unit, double _amount) {
        name = _name;
        unit = _unit;
        amount = _amount;
    }

    public Ingredient() {}

    @Override
    public String toString() {
        return "\nIngredient{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                '}';
    }
}
