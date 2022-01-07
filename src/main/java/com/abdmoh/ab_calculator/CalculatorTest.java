package com.abdmoh.ab_calculator;

import com.abdmoh.ab_calculator.unit_conversion.Currency;

public class CalculatorTest {
    public static void main(String[] args) {
        Currency currency1 = new Currency("GBP", new double[]{5, 3, 2.4});
        System.out.println("Currency 1 (" + currency1.getType() + "): " + currency1.getValues()); // should be GBP values

        currency1.convert("USD"); //converts to USD
        System.out.println("Currency 1 (" + currency1.getType() + "): " + currency1.getValues()); //should be US values

        try {
            currency1.convert("FAKE"); //should crash
        }
        catch (Exception e) {
            System.out.println("Program crashed as expected - " + e);
        }
    }
}
