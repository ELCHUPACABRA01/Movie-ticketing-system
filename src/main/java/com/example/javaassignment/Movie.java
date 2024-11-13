package com.example.javaassignment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private int seatsLeft;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private List<String> soldSeats;
    private boolean ageCheckRequired;

    public Movie(String title, int seatsLeft, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, boolean ageCheckRequired) {
        this.title = title;
        this.seatsLeft = seatsLeft;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.soldSeats = new ArrayList<>();
        this.ageCheckRequired = ageCheckRequired;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public List<String> getSoldSeats() {
        return soldSeats;
    }

    public void addSoldSeat(String seat) {
        soldSeats.add(seat);
    }

    public boolean isSeatSold(String seat) {
        return soldSeats.contains(seat);
    }
    public String getStartDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return startDate.format(dateFormatter) + " " + startTime.format(timeFormatter);
    }

    public String getEndDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return endDate.format(dateFormatter) + " " + endTime.format(timeFormatter);
    }

    public void setSoldSeats(List<String> soldSeats) {
        this.soldSeats = soldSeats;
    }
    public boolean isAgeCheckRequired() {
        return ageCheckRequired;
    }

    public void setAgeCheckRequired(boolean ageCheckRequired) {
        this.ageCheckRequired = ageCheckRequired;
    }
}
