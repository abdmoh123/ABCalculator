package com.abdmoh.ab_calculator.unit_conversion;

import java.util.*;

public class Currency implements Converter {
    //attributes
    private String type;
    private List<Double> values = new ArrayList<>();

    //holds currency conversion rates (relative to GBP) in an immutable map
    private final Map<String, Double> CURRENCIES = Map.of(
    "GBP", 1.0, // Great British Pound
    "USD", 1.35, // US Dollar
    "EURO", 1.2, // Euro
    "AUD", 1.89, // Aussie bucks
    "LYD", 6.22, // Libyan dinar
    "JPY", 156.92, // Japanese Yen
    "INR", 100.72, // Indian Rupee
    "CNY", 8.64 // Chinese Yuan
    );

    //constructors
    public Currency(String type, double[] values) {
        //ensures that the currency type is valid
        if (CURRENCIES.get(type) != null) {
            setType(type);
            addValues(values);
        }
        else {
            throw new IllegalArgumentException("Invalid currency type");
        }
    }
    public Currency(String type, double value) {
        //ensures that the currency type is valid
        if (CURRENCIES.get(type) != null) {
            setType(type);
            addValues(new double[]{value});
        }
        else {
            throw new IllegalArgumentException("Invalid currency type");
        }
    }

    // TODO: make method return a currency object
    //converts the values list from one currency to another
    public void convert(String new_type) {
        //ensures that the currency type is valid
        if (CURRENCIES.get(new_type) != null) {
            double rate1 = CURRENCIES.get(getType());
            double rate2 = CURRENCIES.get(new_type);
            //multiplying the values by the rate below will convert them to the new currency
            double conversion_rate = rate2 / rate1;

            setType(new_type);
            List<Double> temp_list = new ArrayList<>();
            for (double value : this.values) {
                temp_list.add(value * conversion_rate);
            }
            //updates the values list with new currency
            setValues(temp_list);
        }
        else {
            throw new IllegalArgumentException("Invalid currency type");
        }
    }

    //allows more currency values to be added to the list
    public void addValues(double[] values) {
        for (double value : values) {
            this.values.add(value);
        }
    }

    //sets values list to a new list
    public void setValues(List<Double> values) {
        this.values = values;
    }

    //sets the type of currency (e.g. GBP, US dollar)
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getValues() {
        return this.values.toString();
    }
}
