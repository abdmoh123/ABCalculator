package com.abdmoh.ab_calculator.arithmetic;

public class ArithmeticFunctions {

    //checks if character follows float format (integer or decimal point)
    public static boolean isFloat(char c) {
        //minus sign is included to allow floats to be negative
        return Character.isDigit(c) || c == '.' || c == '-';
    }

    //sign of numbers allow both addition and subtraction to be done using the same method
    public static float add(float[] nums) {
        float result = 0;
        //adds up all numbers in array
        for (float num : nums) {
            result += num;
        }
        return result;
    }

    //power method (kind of redundant will probably change/delete later)
    public static float power(float[] nums) {
        //the product of the powers can be calculated using the line below
        float mult_powers = multiply(nums) / nums[0];

        return (float) Math.pow(nums[0], mult_powers);
    }

    //method to root floats (supports any root power e.g. 3r8 = 2)
    public static float root(float[] nums) {
        float result = nums[0];
        //previous calculated root is used as the next power of root (e.g. 2r9r8 = 2)
        for (int i = 1; i < nums.length; ++i) {
            //power method is used to root a number (e.g. sqrt 2 = 2^1/2)
            result = (float) Math.pow(nums[i], 1 / result);
        }
        return result;
    }

    //bulk modulus method (finds remainder)
    public static float modulus(float[] nums) {
        //initial value must be set to the first value in array
        float result = nums[0];
        //index based for loop is required to skip first value in input array
        for (int i = 1; i < nums.length; ++i) {
            result = result % nums[i];
        }
        return result;
    }

    //method to automatically log an arithmetic expression
    public static float log(float base, String input) {
        //simplifies arithmetic expression in brackets into 1 float
        ArithmeticExpression e = new ArithmeticExpression(input);
        e.evaluate();

        //the equation below allows any base log to be used (including e)
        float result = (float)(Math.log(e.getResult())/Math.log(base));
        return result;
    }

    //shortcut method for log to the base of e
    public static float ln(String input) {
        return log((float) Math.E, input);
    }

    //multiplies all numbers in array
    public static float multiply(float[] nums) {
        //initial value must be 1 to allow multiplication
        float result = 1;
        for (float num : nums) {
            result *= num;
        }
        return result;
    }

    //fast dividing method using only 1 loop (both divide methods should output same result)
    public static float divide(float[] nums) {
        //first value is squared to take into account first calculation in loop
        float result = nums[0] * nums[0];
        for (float num : nums) {
            result /= num;
        }
        return result;
    }
    //dividing method making use of pre-existing multiplication code (faster but rounding errors can occur)
    public static float altDivide(float[] nums) {
        //all values except the first are flipped (e.g. dividing by 4 is the same as multiplying by 0.25)
        for (int i = 1; i < nums.length; ++i) {
            nums[i] = 1/nums[i];
        }
        //multiplying the flipped values is equivalent to dividing
        return multiply(nums);
    }
}
