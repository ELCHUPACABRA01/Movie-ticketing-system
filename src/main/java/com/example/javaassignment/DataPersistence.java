package com.example.javaassignment;

import java.io.*;

public class DataPersistence {

    public static void saveToFile(Object object, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(object);
            System.out.println("Data saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static Object loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            System.out.println("Data loaded from file: " + filename);
            return in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
        return null;
    }
}
