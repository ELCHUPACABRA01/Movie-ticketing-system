package com.example.javaassignment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManageShowingsController {
    @FXML
    private TableView<Movie> showingTable;
    @FXML
    private Button btnDeleteShowing;
    @FXML
    private Button btnEditShowing;
    @FXML
    private Label lblMessage;
    @FXML
    private TextArea txtSearch;
    @FXML
    private Button btnExportShowing;


    private DashboardController dashboardController;
    private SalesDatabase salesDatabase;

    private MovieDatabase movieDatabase;
    private ObservableList<Movie> movieList;
    private FilteredList<Movie> filteredShowings;

    public ManageShowingsController(MovieDatabase movieDatabase, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.dashboardController = dashboardController;
        this.salesDatabase = salesDatabase;
    }

    @FXML
    public void initialize() {
        // Initialize showingList with movies from movieDatabase
        movieList = FXCollections.observableArrayList(movieDatabase.getMovies());

        // Create a filtered list that wraps the showingList
        filteredShowings = new FilteredList<>(movieList, p -> true); // initially show all

        // Set up the TableView columns
        setupTableColumns();

        // Bind the filtered list to the table
        showingTable.setItems(filteredShowings);

        // Add listener to the search text area
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterShowings(newValue);
        });

        // Disable buttons when no movie is selected
        showingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnDeleteShowing.setDisable(false);
                btnEditShowing.setDisable(false);
            } else {
                btnDeleteShowing.setDisable(true);
                btnEditShowing.setDisable(true);
            }
        });
    }

    private void filterShowings(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            // If the search text is empty, show all showings
            filteredShowings.setPredicate(showing -> true);
        } else {
            // Convert the search text to lowercase for case-insensitive matching
            String lowerCaseFilter = searchText.toLowerCase();

            // Update the predicate for filtering
            filteredShowings.setPredicate(movie -> {
                // Check if the movie title contains the search text
                return movie.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        }
    }

    private void setupTableColumns() {
        TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Movie, String> startDateTimeColumn = new TableColumn<>("Start");
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));

        TableColumn<Movie, String> endDateTimeColumn = new TableColumn<>("End");
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));

        TableColumn<Movie, Integer> seatsLeftColumn = new TableColumn<>("Seats Left");
        seatsLeftColumn.setCellValueFactory(new PropertyValueFactory<>("seatsLeft"));

        showingTable.getColumns().addAll(titleColumn, startDateTimeColumn, endDateTimeColumn, seatsLeftColumn);
    }
    public void deleteMovie() {
        Movie selectedMovie = showingTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieList.remove(selectedMovie);
            movieDatabase.deleteMovie(selectedMovie);

            salesDatabase.deleteSalesByMovie(selectedMovie.getTitle());

            GetDatabases data = new GetDatabases(movieDatabase, salesDatabase);
            DataPersistence.saveToFile(data, "cinema_data.dat");

            lblMessage.setText("Movie and associated sales deleted successfully.");
        } else {
            lblMessage.setText("Please select a movie to delete.");
        }
    }

    public void goToAddShowingScene(){
        dashboardController.loadScene("AddShowing-Scene.fxml", new AddShowingController(movieDatabase, dashboardController, salesDatabase));
    }
    public void goToEditShowingScene(){
        Movie selectedMovie = showingTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            dashboardController.loadScene("EditShowing-Scene.fxml", new EditShowingController(movieDatabase, selectedMovie, dashboardController, salesDatabase));
        } else {
            lblMessage.setText("No movie selected for editing.");
        }
    }

    @FXML
    public void exportToCSV() {
        // Create FileChooser to let the user select where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Showings as CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show save file dialog
        File file = fileChooser.showSaveDialog(btnExportShowing.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write the header
                writer.write("start datetime,end datetime,movie title,seats left\n");

                // Write the data for each showing
                for (Movie movie : movieList) {
                    writer.write(movie.getStartDateTime() + "," +
                            movie.getEndDateTime() + "," +
                            movie.getTitle() + "," +
                            movie.getSeatsLeft() + "\n");
                }

                lblMessage.setText("Showings exported successfully to " + file.getPath());
            } catch (IOException e) {
                lblMessage.setText("Error exporting showings: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}