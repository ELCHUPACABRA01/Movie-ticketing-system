package com.example.javaassignment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime dateTime;
    private int numberOfTickets;
    private String customerName;
    private String movieTitle;

    public Sale(LocalDateTime dateTime, int numberOfTickets, String customerName, String movieTitle) {
        this.dateTime = dateTime;
        this.numberOfTickets = numberOfTickets;
        this.customerName = customerName;
        this.movieTitle = movieTitle;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}