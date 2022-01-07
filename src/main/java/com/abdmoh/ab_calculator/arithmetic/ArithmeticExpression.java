package com.abdmoh.ab_calculator.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArithmeticExpression {
    //can only be accessed through get command (outside this class)
    private String content;

    public ArithmeticExpression(String content) {
        setContent(content);
    }

    public void evaluate() {
        //prepares the expression content for processing
        initExpression();

        //while loop ensures all brackets are simplified first
        while (getContent().indexOf(Brackets.NORMAL.getOpenBracket()) != -1 || getContent().indexOf(Brackets.NORMAL.getClosedBracket()) != -1) {
             simplifyBrackets(Brackets.NORMAL);
        }

        //while loop ensures all modulus parts are done first
        while (getContent().indexOf(Brackets.ABS.getOpenBracket()) != -1) {
            simplifyBrackets(Brackets.ABS);
        }

        //calculates the powers and roots before other operations
        if (getContent().indexOf('^') < getContent().indexOf('√') && getContent().indexOf('^') != -1) {
            simplify('^');
            simplify('√');
        }
        else {
            simplify('√');
            simplify('^');
        }
        //the following command are executed in a specific order to follow BIDMAS/BODMAS
        if (getContent().indexOf('/') < getContent().indexOf('*') && getContent().indexOf('/') != -1) {
            if (getContent().indexOf('%') < getContent().indexOf('/') && getContent().indexOf('%') != -1) {
                simplify('%');
                simplify('/');
                simplify('*');
            }
            else {
                simplify('/');
                simplify('%');
                simplify('*');
            }
        }
        else {
            if (getContent().indexOf('%') < getContent().indexOf('*') && getContent().indexOf('%') != -1) {
                simplify('%');
                simplify('*');
                simplify('/');
            }
            else {
                simplify('*');
                simplify('%');
                simplify('/');
            }
        }
        //subtraction is not required because they are treated as negative numbers
        simplify('+');
    }

    private void initExpression() {
        //converts pi values into float
        convertConstants();
        //removes all space characters
        setContent(getContent().replace(" ", ""));
        //automatically adds plus signs before negative numbers
        fixMinus();
    }

    //converts constants from text form to float format
    private void convertConstants() {
        //converts pi from text to float
        String input = getContent().replaceAll("pi", Double.toString(Math.PI));
        //converts e from text to float
        input = input.replaceAll("e", Double.toString(Math.E));
        //applies the changes to the expression's content
        setContent(input);
    }

    private void fixMinus() {
        //undoes all previous fixing to avoid duplicate plus signs
        String input = getContent().replace("+-", "-");
        input = input.replace("-", "+-");

        if (input.charAt(0) == '+') {
            setContent(input.substring(1));
        }
        else {
            setContent(input);
        }
    }

    private void simplifyBrackets(Brackets brackets) {
        //gets content of expression and assigns it to a string variable
        String input = getContent();

        //initialises index values for outputting the correct string
        int start_index = input.indexOf(brackets.getOpenBracket());
        int end_index = input.indexOf(brackets.getClosedBracket());

        //required because ABS brackets are identical
        if (brackets == Brackets.ABS) {

            //a list holding the locations of the brackets is made
            List<Integer> bracket_indexes = new ArrayList<>();
            bracket_indexes.add(start_index); //first location was already found

            //loop puts the locations of all brackets into a list
            for (int i = start_index; i < getContent().length(); i += 0) {
                int temp_index = input.indexOf(brackets.getOpenBracket(), i + 1);

                if (temp_index == -1) {
                    break;
                }
                bracket_indexes.add(temp_index);
                i = temp_index;
            }
            //there must be an even amount of brackets
            if ((bracket_indexes.size() & 1) == 0) {
                int index = (bracket_indexes.size() / 2) - 1; //reduced by 1 because indexes start at 0
                //gets the innermost brackets (nested brackets need to be done first)
                start_index = bracket_indexes.get(index);
                end_index = bracket_indexes.get(index + 1);
            }
            else {
                //expressions must have an even number of brackets in order to close them all
                throw new IllegalArgumentException("Missing bracket(s)");
            }
        }
        else {
            //while loop ensures that the correct brackets are selected
            while (true) {
                //gets next occurrence of open bracket
                int next_index = input.indexOf(brackets.getOpenBracket(), start_index + 1);
                if (next_index > end_index || next_index == -1) {
                    break;
                } else {
                    start_index = next_index;
                }
            }
        }

        //uses recursion to even simplify nested brackets in the inputted expression
        String content_section = input.substring(start_index + 1, end_index);
        ArithmeticExpression e = new ArithmeticExpression(content_section);
        e.evaluate();

        //gets the absolute value if the brackets were used
        if (brackets == Brackets.ABS) {
            float result = Math.abs(e.getResult());
            e.setContent(Float.toString(result));
        }

        //if statement is responsible for ensuring the correct output is returned
        if (start_index == 0) {
            if (end_index == input.length() - 1) {
                setContent(e.getContent());
            }
            else {
                setContent(e.getContent() + input.substring(end_index + 1));
            }
        }
        else {
            if (end_index == input.length() - 1) {
                setContent(input.substring(0, start_index) + e.getContent());
            }
            else {
                setContent(input.substring(0, start_index) + e.getContent() + input.substring(end_index + 1));
            }
        }
    }

    private void simplify(char operator) {
        //gets content of expression and assigns it to a string variable
        String input = getContent();

        //input is checked for existence of operator to prevent crash
        if (input.indexOf(operator) != -1) {
            int start_index = input.indexOf(operator); //holds index of first float included in calculation
            int temp_index = start_index; //used to hold temporary index values (including the end index)

            //for loop finds the index of the first number in the calculation
            for (int i = start_index - 1; i >= 0; --i) {
                char c = input.charAt(i);

                if (ArithmeticFunctions.isFloat(c)) {
                    start_index = i;
                }
                else {
                    break;
                }
            }
            //for loop finds where the operator changes (e.g. to separate addition from multiplication)
            for (int i = temp_index + 1; i < input.length(); ++i) {
                char c = input.charAt(i);

                if (!ArithmeticFunctions.isFloat(c) && c != operator) {
                    temp_index = i;
                    break;
                }
                else if (i == input.length() - 1) {
                    temp_index = i + 1;
                }
            }

            String substring = input.substring(start_index, temp_index);
            //splits substring using inputted operator
            String[] split_string = substring.split(Pattern.quote(Character.toString(operator)));

            float[] array = new float[split_string.length];
            //converts string array into float data type
            for (int j = 0; j < split_string.length; ++j) {
                array[j] = Float.parseFloat(split_string[j]);
            }
            //evaluates the array contents (depends on operator)
            float result = calculate(array, operator);

            //converts input string to a newer and simpler expression (after calculation)
            String output = input.substring(0, start_index) + result + input.substring(temp_index);
            //updates the expression's content after one simplification
            setContent(output);
            //recursive if statement means that the entire expression will be simplified with less redundancy
            if (output.indexOf(operator) != -1) {
                simplify(operator);
            }
        }
    }

    //automatically performs correct calculation depending on inputted operator
    private float calculate(float[] array, char operator) {
        float result;
        switch (operator) {
            case '+':
                //can also subtract by adding negative numbers
                result = ArithmeticFunctions.add(array);
                break;
            case '*':
                result = ArithmeticFunctions.multiply(array);
                break;
            case '/':
                result = ArithmeticFunctions.altDivide(array);
                break;
            case '^':
                result = ArithmeticFunctions.power(array);
                break;
            case '√':
                result = ArithmeticFunctions.root(array);
                break;
            case '%':
                result = ArithmeticFunctions.modulus(array);
                break;
            default:
                //throws exception when there is an invalid operator used
                throw new IllegalArgumentException("Invalid arithmetic calculation");
        }
        return result;
    }

    //returns expression content as float
    public float getResult() {
        return Float.parseFloat(getContent());
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
