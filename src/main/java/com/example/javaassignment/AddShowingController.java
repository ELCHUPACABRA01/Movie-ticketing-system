package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AddShowingController {
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
    @FXML
    private ChoiceBox cbAgeRestriction;


    MovieDatabase movieDatabase;
    private DashboardController dashboardController;
    private SalesDatabase salesDatabase;

    public AddShowingController(MovieDatabase movieDatabase, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.dashboardController = dashboardController;
        this.salesDatabase = salesDatabase;
    }

        private void populateChoiceBox() {
            cbAgeRestriction.getItems().addAll("Yes", "No");
            cbAgeRestriction.setValue("No");  // Set default value to "No"
        }

        @FXML
        public void initialize() {
        populateChoiceBox();
        }

    @FXML
    public void addShowing() {
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

            Boolean ageRestricted = false;
            if (cbAgeRestriction.getValue() == null) {
                lblMessage.setText("Please select whether the movie is age-restricted.");
                return;
            }

            ageRestricted = cbAgeRestriction.getValue().equals("Yes");
            System.out.println(ageRestricted);

            int seatsLeft = 72;

            Movie movie = new Movie(title, seatsLeft, startDate, endDate, startTime, endTime, ageRestricted);
            movieDatabase.addMovie(movie);
            System.out.println("Added movie: " + movie.getTitle());
            System.out.println("Current movie count: " + movieDatabase.getMovies().size());
            GetDatabases data = new GetDatabases(movieDatabase, dashboardController.getSalesDatabase());
            DataPersistence.saveToFile(data, "cinema_data.dat");

            clearFields();
            lblMessage.setText("Showing added successfully.");
        } catch (Exception e) {
            lblMessage.setText("An error occurred while adding the showing.");
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    @FXML
    public void cancelAddShowing() {
        dashboardController.loadScene("ManageShowings-Scene.fxml", new ManageShowingsController(movieDatabase, dashboardController, salesDatabase));
    }

    private void clearFields() {
        txtTitle.clear();
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
        txtStartTime.clear();
        txtEndTime.clear();
    }
}
