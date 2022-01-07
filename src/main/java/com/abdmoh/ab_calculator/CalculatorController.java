package com.abdmoh.ab_calculator;

import com.abdmoh.ab_calculator.arithmetic.ArithmeticExpression;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorController {
    public TextField text_field;

    @FXML
    public void calculate(Event event) {
        try {
            //gets inputted expression from text field
            String input = text_field.getText();

            //calculates inputted expression
            ArithmeticExpression expression = new ArithmeticExpression(input);
            expression.evaluate();

            //removes redundant zeroes
            String result = expression.getContent();

            //prints the result on the text field
            text_field.setText(result);
        }
        catch (Exception exception) {
            //catch clause allows error messages to be displayed to the user
            text_field.setText("Error");
        }
    }

    @FXML
    public void openWindow(Event event) throws IOException {
        //allows id value to select window to open
        Button button = ((Button) event.getSource());
        //loader is required to use FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(button.getId() + ".fxml"));

        //sets up the scene using FXML file
        Parent root = loader.load();
        Scene scene = new Scene(root);
        //styles the scene using the custom stylesheet
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //creates a stage (window) with the following properties
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(button.getText());
        //assigns the scene to the stage
        stage.setScene(scene);

        //closes the previous window before displaying the new one
        Stage previous_stage = (Stage) button.getScene().getWindow();
        previous_stage.close();

        //displays the window to the user
        stage.show();
    }

    //appends a number or operator depending on button pressed
    @FXML
    public void type(Event event) {
        Button button = (Button) event.getSource();
        //text field's content is appended using value of pressed button
        String text = text_field.getText() + button.getText();
        text_field.setText(text);
    }

    @FXML
    public void clear(Event event) {
        //empties the text field
        text_field.setText("");
    }

    //performs same function as pressing backspace on keyboard
    @FXML
    public void deleteCharacter(Event event) {
        String old_text = text_field.getText();
        //prevents index out of bounds exception
        if (old_text.length() > 0) {
            //removes last character (like pressing backspace)
            String new_text = old_text.substring(0, old_text.length() - 1);

            //replaces previous text with new one
            text_field.setText(new_text);
        }
    }
}
