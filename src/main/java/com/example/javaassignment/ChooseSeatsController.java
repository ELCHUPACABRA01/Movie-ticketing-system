package com.example.javaassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChooseSeatsController {

    @FXML
    private GridPane seatGrid;
    @FXML
    private ListView<String> selectedSeatsList;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblSelectedShowing;

    private final int rows = 6;
    private final int cols = 12;
    private List<Button> seatButtons = new ArrayList<>();
    private ObservableList<String> selectedSeats = FXCollections.observableArrayList();

    private Movie selectedMovie;
    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;
    private DashboardController dashboardController;

    public ChooseSeatsController(Movie selectedMovie, MovieDatabase movieDatabase, DashboardController dashboardController, SalesDatabase salesDatabase) {
        this.selectedMovie = selectedMovie;
        this.movieDatabase = movieDatabase;
        this.dashboardController = dashboardController;
        this.salesDatabase = salesDatabase;
    }

    @FXML
    public void initialize() {
        lblSelectedShowing.setText("Selected showing: " + selectedMovie.getTitle());
        selectedSeatsList.setItems(selectedSeats);
        generateSeatGrid();
    }

    private void generateSeatGrid() {
        System.out.println("Sold seats for movie: " + selectedMovie.getSoldSeats());
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button seatButton = createSeatButton(row, col);
                String seatLabel = "Row " + (row + 1) + " Seat " + (col + 1);

                if (selectedMovie.isSeatSold(seatLabel)) {
                    seatButton.setStyle("-fx-background-color: #FF0000");
                    seatButton.setDisable(true);
                } else {
                    seatButton.setStyle("-fx-background-color: #D3D3D3");
                }

                seatButtons.add(seatButton);
                seatGrid.add(seatButton, col, row);
            }
        }
    }

    private Button createSeatButton(int row, int col) {
        Button seatButton = new Button(String.valueOf(col + 1));
        seatButton.setMinWidth(40);
        seatButton.setMinHeight(40);
        String seatLabel = "Row " + (row + 1) + " Seat " + (col + 1);

        seatButton.setOnAction(e -> {
            if (seatButton.getStyle().contains("#D3D3D3")) {
                seatButton.setStyle("-fx-background-color: #4CAF50");
                selectedSeats.add(seatLabel);
            } else if (seatButton.getStyle().contains("#4CAF50")) {
                seatButton.setStyle("-fx-background-color: #D3D3D3");
                selectedSeats.remove(seatLabel);
            }
        });

        return seatButton;
    }

    @FXML
    public void sellTickets() {
        String customerName = txtCustomerName.getText();
        if (customerName == null || customerName.isEmpty()) {
            lblMessage.setText("Customer name is required.");
            return;
        }

        if (selectedSeats.isEmpty()) {
            lblMessage.setText("Please select at least one seat.");
            return;
        }
        System.out.println(selectedMovie.isAgeCheckRequired());
        if (selectedMovie.isAgeCheckRequired()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AgeCheck-Scene.fxml"));
                Parent dialogRoot = loader.load();
                AgeCheckController ageCheckController = loader.getController();
                ageCheckController.setMovieInformation(
                        selectedMovie.getTitle(),
                        selectedMovie.getStartDateTime(),
                        selectedSeats.size(),
                        customerName
                );

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Age Check Confirmation");
                dialogStage.setScene(new Scene(dialogRoot));
                dialogStage.showAndWait();  // This will block until the dialog is closed

                if (!ageCheckController.isConfirmed()) {
                    lblMessage.setText("Age check is required and was not confirmed.");
                    return;  // If the age check is not confirmed, cancel the sale
                }

            } catch (IOException e) {
                e.printStackTrace();
                lblMessage.setText("Error showing age check dialog.");
                return;
            }
        }

        int seatsSold = selectedSeats.size();
        selectedMovie.setSeatsLeft(selectedMovie.getSeatsLeft() - seatsSold);

        for (String seat : selectedSeats) {
            selectedMovie.addSoldSeat(seat);
        }

        Sale sale = new Sale(LocalDateTime.now(), seatsSold, customerName, selectedMovie.getTitle());
        salesDatabase.addSale(sale);

        GetDatabases data = new GetDatabases(movieDatabase, salesDatabase);
        DataPersistence.saveToFile(data, "cinema_data.dat");

        selectedSeats.clear();
        clearForm();

        lblMessage.setText("Sold " + seatsSold + " tickets to " + customerName);
    }

    @FXML
    public void cancelSelling() {
        dashboardController.loadScene("ManageShowings-Scene.fxml", new ManageShowingsController(movieDatabase, dashboardController, salesDatabase));
    }

    private void clearForm() {
        txtCustomerName.clear();
        for (Button button : seatButtons) {
            button.setStyle("-fx-background-color: #D3D3D3");
        }
    }
}
