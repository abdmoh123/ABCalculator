package com.abdmoh.ab_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*
TODO LIST:
    - Create functions for simple arithmetic (DONE)
    - Allow inputted arithmetic expressions to be calculated correctly (DONE)
    - Make simplifying method recursive and clean up code (DONE)
    - Implement support for parentheses (DONE)
        - Implement support for nested brackets (DONE)
    - Use indexOf instead of iteration for simplifyBrackets method (DONE)
    - Use indexOf instead of iteration for simplify method? (DONE)
    - Fix bug with negative numbers (DONE)
    - Add GUI (DONE)
    - Reduce parameters in expression methods (DONE)
    - Optimise brackets function?
    - Add LaTeX notation?
    - Add other operators
        - Modulus that finds remainder (DONE)
        - Absolute modulus that removes sign (DONE)
        - Powers (DONE)
        - Roots (DONE)
        - Logs
        - ln
        - sin() cos() and tan() functions
            - inverted versions
    - Add special constants (DONE)
        - pi (DONE)
        - e (DONE)
    - Add implicit multiplication support
    - Expand GUI
        - Add more buttons
            - Constants (DONE)
                - pi (DONE)
                - e (DONE)
            - Powers (DONE)
            - Roots (DONE)
            - Exp or *10^x
            - Modulus and/or abs
            - Log and ln
            - Shortcut buttons?
                - 10^x (DONE)
                - e^x (DONE)
                - x^2 (DONE)
                - Square root (DONE)
        - Custom themes
        - Scales with window
    - Add algebra support
    - Add unit converters
        - Currency (DONE)
        - Weight
        - Length/Area/Volume
*/

public class CalculatorApp extends Application {
    //method responsible for the application's GUI
    @Override
    public void start(Stage stage) throws IOException {
        //loader is required to use FXML
        FXMLLoader f_loader = new FXMLLoader(getClass().getResource("calculator.fxml"));

        //sets up the scene using FXML file
        Parent root = f_loader.load();
        Scene calculator_scene = new Scene(root);
        //styles the scene using a custom stylesheet
        calculator_scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //creates a stage (window) with the following properties
        Stage calculator_stage = new Stage();
        calculator_stage.initModality(Modality.WINDOW_MODAL);
        calculator_stage.setTitle("Calculator");
        //assigns the scene (calculator UI) to the stage
        calculator_stage.setScene(calculator_scene);
        //displays the window to the user
        calculator_stage.show();
    }

    //main method
    public static void main(String[] args) {
        launch(args);
    }
}