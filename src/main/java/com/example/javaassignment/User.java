package com.example.javaassignment;

public class User {
    private String Name;
    private String UserName;
    private String Password;
    private AccessLevel accessLevel;


    public User(String Name, String UserName, String Password , AccessLevel accessLevel) {
        this.Name = Name;
        this.UserName = UserName;
        this.Password = Password;
        this.accessLevel = accessLevel;
    }



    public String getUserName() {
        return UserName;
    }


    public String getPassword() {
        return Password;
    }


    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

}
