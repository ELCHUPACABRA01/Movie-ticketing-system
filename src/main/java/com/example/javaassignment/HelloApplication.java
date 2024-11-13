package com.example.javaassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class HelloApplication extends Application {
    private static final String CINEMA_DATA_FILE = "cinema_data.dat";

    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;

    @Override
    public void start(Stage stage) throws Exception {
        GetDatabases data = (GetDatabases) DataPersistence.loadFromFile(CINEMA_DATA_FILE);


        if (data != null) {
            movieDatabase = loadMovies();
            salesDatabase = loadSales();
        } else {
            movieDatabase = new MovieDatabase();
            salesDatabase = new SalesDatabase();
        }

        LoginController loginController = new LoginController(movieDatabase, salesDatabase);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-View.fxml"));
        loader.setController(loginController);
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.show();
    }

    public MovieDatabase loadMovies() {
        GetDatabases data = (GetDatabases) DataPersistence.loadFromFile("cinema_data.dat");
        if (data != null) {
            MovieDatabase movieDatabase = data.getMovieDatabase();
            for (Movie movie : movieDatabase.getMovies()) {
                if (movie.getSoldSeats() == null) {
                    movie.setSoldSeats(new ArrayList<>());
                }
            }
            return movieDatabase;
        }
        return new MovieDatabase();
    }
    public SalesDatabase loadSales() {
        GetDatabases data = (GetDatabases) DataPersistence.loadFromFile(CINEMA_DATA_FILE);

        if (data != null) {
            return data.getSalesDatabase();
        }

        return new SalesDatabase();
    }

    @Override
    public void stop() throws Exception {
        GetDatabases data = new GetDatabases(movieDatabase, salesDatabase);
        DataPersistence.saveToFile(data, CINEMA_DATA_FILE);
    }

    public static void main(String[] args) {
        launch();
    }
}