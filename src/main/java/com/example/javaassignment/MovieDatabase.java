package com.example.javaassignment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Movie> movies;

    public MovieDatabase() {
        movies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void deleteMovie(Movie movie) {
        movies.remove(movie);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
