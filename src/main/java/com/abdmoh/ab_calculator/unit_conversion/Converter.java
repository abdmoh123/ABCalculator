package com.abdmoh.ab_calculator.unit_conversion;

import java.util.List;

public interface Converter {
    void convert(String new_type);
    void addValues(double[] values);

    void setType(String type);
    void setValues(List<Double> values);
    String getType();
    String getValues();
}
