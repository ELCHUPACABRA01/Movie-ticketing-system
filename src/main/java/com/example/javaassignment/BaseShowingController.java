package com.example.javaassignment;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class BaseShowingController {
    protected MovieDatabase movieDatabase;
    protected DashboardController dashboardController;
    protected SalesDatabase salesDatabase;

    // Constructor
    public BaseShowingController(MovieDatabase movieDatabase, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.dashboardController = dashboardController;
        this.salesDatabase = salesDatabase;
    }

    protected boolean validateAndSetupMovie(String title, LocalDate startDate, LocalDate endDate, String startTimeText, String endTimeText, Label lblMessage) {
        if (title == null || title.trim().isEmpty()) {
            lblMessage.setText("Title cannot be empty.");
            return false;
        }

        if (startDate == null || endDate == null) {
            lblMessage.setText("Please select valid start and end dates.");
            return false;
        }

        LocalTime startTime, endTime;
        try {
            startTime = LocalTime.parse(startTimeText);
        } catch (DateTimeParseException e) {
            lblMessage.setText("Invalid start time format. Use HH:mm.");
            return false;
        }
        try {
            endTime = LocalTime.parse(endTimeText);
        } catch (DateTimeParseException e) {
            lblMessage.setText("Invalid end time format. Use HH:mm.");
            return false;
        }

        if (endDate.isBefore(startDate) || (endDate.isEqual(startDate) && endTime.isBefore(startTime))) {
            lblMessage.setText("End time must be after start time.");
            return false;
        }

        // If all validations pass, return true
        return true;
    }

    // Shared method to save movie data
    protected void saveMovieData() {
        GetDatabases data = new GetDatabases(movieDatabase, salesDatabase);
        DataPersistence.saveToFile(data, "cinema_data.dat");
    }
}
