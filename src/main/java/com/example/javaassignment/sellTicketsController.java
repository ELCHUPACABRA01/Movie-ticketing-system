package com.example.javaassignment;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class sellTicketsController {
    @FXML
    private TableView<Movie> showingTable;
    @FXML
    private Label lblSelectedMovie;
    @FXML
    private Label lblMessage;

    private ObservableList<Movie> movieList;
    MovieDatabase movieDatabase;
    DashboardController dashboardController;
    SalesDatabase salesDatabase;
    public sellTicketsController(MovieDatabase movieDatabase, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.dashboardController = dashboardController;
        this.salesDatabase = salesDatabase;
    }

    @FXML
    public void initialize() {
        movieList = FXCollections.observableArrayList(movieDatabase.getMovies());
        showingTable.setItems(movieList);

        showingTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {
                if (newValue != null) {
                    lblSelectedMovie.setText(
                            "Selected movie: " + newValue.getTitle() +
                                    " | Start: " + newValue.getStartDate() + " " + newValue.getStartTime() +
                                    " | Seats Left: " + newValue.getSeatsLeft()
                    );
                } else {
                    lblSelectedMovie.setText("No movie selected.");
                }
            }
        });
    }
    public void goToChooseSeats(){
        Movie selectedMovie = showingTable.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            lblMessage.setText("Please select a movie to proceed.");
        } else {
            dashboardController.loadScene("ChooseSeats-Scene.fxml", new ChooseSeatsController(selectedMovie, movieDatabase, dashboardController, salesDatabase));
        }
    }
}

