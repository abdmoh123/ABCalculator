module com.abdmoh.ab_calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.abdmoh.ab_calculator to javafx.fxml;
    exports com.abdmoh.ab_calculator;
}