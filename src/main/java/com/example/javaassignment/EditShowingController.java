package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class EditShowingController {
    @FXML
    private Button btnAddShowing;
    @FXML
    private Button btnCancelAddShowing;
    @FXML
    private TextField txtTitle;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtEndTime;
    @FXML
    private Label lblMessage;
    MovieDatabase movieDatabase;
    private Movie selectedMovie;
    private DashboardController dashboardController;
    private SalesDatabase salesDatabase;


    public EditShowingController(MovieDatabase movieDatabase, Movie selectedMovie, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.selectedMovie = selectedMovie;
        this.dashboardController = dashboardController;
    }

    @FXML
    public void initialize() {
        txtTitle.setText(selectedMovie.getTitle());
        dpStartDate.setValue(selectedMovie.getStartDate());
        dpEndDate.setValue(selectedMovie.getEndDate());
        txtStartTime.setText(selectedMovie.getStartTime().toString());
        txtEndTime.setText(selectedMovie.getEndTime().toString());
    }

    @FXML
    public void editMovie() {
        try {
            String title = txtTitle.getText();
            if (title == null || title.trim().isEmpty()) {
                lblMessage.setText("Title cannot be empty.");
                return;
            }

            LocalDate startDate = dpStartDate.getValue();
            LocalDate endDate = dpEndDate.getValue();
            if (startDate == null || endDate == null) {
                lblMessage.setText("Please select valid start and end dates.");
                return;
            }

            LocalTime startTime, endTime;
            try {
                startTime = LocalTime.parse(txtStartTime.getText());
            } catch (DateTimeParseException e) {
                lblMessage.setText("Invalid start time format. Use HH:mm.");
                return;
            }
            try {
                endTime = LocalTime.parse(txtEndTime.getText());
            } catch (DateTimeParseException e) {
                lblMessage.setText("Invalid end time format. Use HH:mm.");
                return;
            }

            if (endDate.isBefore(startDate) || (endDate.isEqual(startDate) && endTime.isBefore(startTime))) {
                lblMessage.setText("End time must be after start time.");
                return;
            }

            selectedMovie.setTitle(title);
            selectedMovie.setStartDate(startDate);
            selectedMovie.setStartTime(startTime);
            selectedMovie.setEndDate(endDate);
            selectedMovie.setEndTime(endTime);


            lblMessage.setText("Movie details updated successfully.");


        } catch (Exception e) {
            lblMessage.setText("An error occurred while updating the movie.");
        }
    }
    @FXML
    public void cancelAddShowing() {
        dashboardController.loadScene("ManageShowings-Scene.fxml", new ManageShowingsController(movieDatabase, dashboardController, salesDatabase));
    }

    @FXML
    private void clearFields() {
        txtTitle.clear();
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
        txtStartTime.clear();
        txtEndTime.clear();
        lblMessage.setText("");
    }
}
