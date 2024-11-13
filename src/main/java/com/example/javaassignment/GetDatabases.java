package com.example.javaassignment;
import java.io.Serializable;

public class GetDatabases implements Serializable{
    private static final long serialVersionUID = 1L;
    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;

    public GetDatabases(MovieDatabase movieDatabase, SalesDatabase salesDatabase) {
        this.movieDatabase = movieDatabase;
        this.salesDatabase = salesDatabase;
    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

    public SalesDatabase getSalesDatabase() {
        return salesDatabase;
    }

    public void setMovieDatabase(MovieDatabase movieDatabase) {
        this.movieDatabase = movieDatabase;
    }

    public void setSalesDatabase(SalesDatabase salesDatabase) {
        this.salesDatabase = salesDatabase;
    }
}
