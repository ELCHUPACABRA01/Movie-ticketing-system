package com.example.javaassignment;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {
    private static final long serialVersionUID = 1L;

    public List<User> getUsers() {
        return users;
    }

    private List<User> users;
    public EmployeeDatabase() {
        users = new ArrayList<>();
        User jack = new User("Jack Sparrow", "Jacks","password1",AccessLevel.Sales);
        User indiana = new User("Indiana Jones", "Indiej","password12",AccessLevel.Manager);
        users.add(jack);
        users.add(indiana);
    }
}