package com.example.javaassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblRole;
    @FXML
    private Label lblDateTime;
    @FXML
    private VBox layout;
    @FXML
    private Button btnManageShowings;
    @FXML
    private Button btnSellTickets;

    private Stage stage;
    private SalesDatabase salesDatabase;
    private MovieDatabase movieDatabase;

    public DashboardController(MovieDatabase movieDatabase, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.salesDatabase = salesDatabase;
    }


    public SalesDatabase getSalesDatabase() {
        return salesDatabase;
    }

    public void setDashboardInformation(User user) {
        if (user.getAccessLevel() == AccessLevel.Sales) {
            btnManageShowings.setDisable(true);
            btnSellTickets.setDisable(true);
        }
        lblWelcome.setText("Welcome, " + user.getUserName());
        lblRole.setText("You are logged in as " + user.getAccessLevel());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lblDateTime.setText("The current date and time is: " + now.format(formatter));
    }

    public void initialize(Stage currentStage) {
        this.stage = currentStage;
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setController(controller);
            Pane newPane = fxmlLoader.load();

            if (layout.getChildren().size() > 1) {
                layout.getChildren().remove(1);
            }
            layout.getChildren().add(newPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToManageShowingsScene() throws IOException {
        ManageShowingsController manageShowingsController = new ManageShowingsController(movieDatabase, this, salesDatabase);
        loadScene("ManageShowings-Scene.fxml", manageShowingsController);
    }

    public void goToSalesHistoryScene() throws IOException {
        loadScene("SalesHistory-Scene.fxml", new SalesHistoryController(salesDatabase));
    }

    public void goToSellTicketsScene() throws IOException {
        loadScene("SellTickets-Scene.fxml", new sellTicketsController(movieDatabase, this, salesDatabase));
    }
}
