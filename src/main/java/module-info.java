module com.example.javaassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaassignment to javafx.fxml;
    exports com.example.javaassignment;
}