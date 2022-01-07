package com.abdmoh.ab_calculator.arithmetic;

public class Brackets {
    //properties of the brackets class
    private char open_bracket;
    private char closed_bracket;

    //predetermined constant objects of this class are found below
    public static final Brackets NORMAL = new Brackets('(',')');
    public static final Brackets ABS = new Brackets('|','|');
    public static final Brackets SQUARE = new Brackets('[',']');
    public static final Brackets CURLY = new Brackets('{','}');

    public Brackets(char open_bracket, char closed_bracket) {
        this.open_bracket = open_bracket;
        this.closed_bracket = closed_bracket;
    }

    //method automatically checks if a character belongs within a certain 'family' of brackets
    public boolean matches(char bracket) {
        if (bracket == getOpenBracket() || bracket == getClosedBracket()) {
            return true;
        }
        else {
            return false;
        }
    }

    //lack of set methods make the objects immutable
    public char getOpenBracket() {
        return open_bracket;
    }
    public char getClosedBracket() {
        return closed_bracket;
    }
}
