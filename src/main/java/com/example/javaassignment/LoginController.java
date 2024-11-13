package com.example.javaassignment;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label loginErrorMessage;
    @FXML

    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;


    public LoginController(MovieDatabase movieDatabase, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.salesDatabase = salesDatabase;
    }

    @FXML
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean loginSuccessful = false;
        EmployeeDatabase employeeDatabase = new EmployeeDatabase();

        for(User user : employeeDatabase.getUsers()) {
            if (username.equals(user.getUserName()) && password.equals(user.getPassword())) {
                try {
                    showDashboard(user);
                    Stage loginStage = (Stage) btnLogin.getScene().getWindow();
                    loginStage.close();
                } catch (IOException e) {
                    loginErrorMessage.setText(e.getMessage());

                }
                loginSuccessful = true;
                break;
            }
        }
        if (!loginSuccessful) {
            loginErrorMessage.setText("Invalid username or password.");
        }
    }
    void showDashboard(User user) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard-View.fxml"));
        DashboardController dashboardController = new DashboardController(movieDatabase, salesDatabase);
        loader.setController(dashboardController);

        Scene scene = new Scene(loader.load());
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard");
        dashboardStage.setScene(scene);
        dashboardStage.sizeToScene();
        dashboardStage.show();

        dashboardController.initialize(dashboardStage);
        dashboardController.setDashboardInformation(user);
    }
}