package com.abdmoh.ab_calculator.unit_conversion;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverter implements Converter {
    private String type = new String();
    private List<Double> values = new ArrayList<>();
}