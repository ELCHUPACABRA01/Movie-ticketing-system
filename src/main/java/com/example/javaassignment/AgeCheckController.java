package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AgeCheckController {
    @FXML
    private CheckBox cbAgeConfirmed;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblMovieTitle;
    @FXML
    private Label lblDateAndTime;
    @FXML
    private Label lblNrOfTickets;
    @FXML
    private Label lblCustomerName;

    private boolean isConfirmed = false;

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setMovieInformation(String movieTitle, String dateAndTime, int numberOfTickets, String customerName) {
        lblMovieTitle.setText("Movie: " + movieTitle);
        lblDateAndTime.setText("Showtime: " + dateAndTime);
        lblNrOfTickets.setText("Tickets: " + numberOfTickets);
        lblCustomerName.setText("Customer: " + customerName);
    }

    @FXML
    private void confirmAgeCheck() {
        if (cbAgeConfirmed.isSelected()) {
            isConfirmed = true;
            Stage stage = (Stage) cbAgeConfirmed.getScene().getWindow();
            stage.close();
        } else {
            lblMessage.setText("You must confirm the age check.");
        }
    }

    @FXML
    private void cancelAgeCheck() {
        isConfirmed = false;  // Ensure it is not confirmed
        // Close the dialog
        Stage stage = (Stage) cbAgeConfirmed.getScene().getWindow();
        stage.close();
    }
}
