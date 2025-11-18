package dev;

import java.io.*;
import java.nio.file.*;

public class TravelHandler {
    public static final long serialVersionUID = 1L;
    
    // Use data/ folder — clean & organized
    public static final Path FILE_PATH = Paths.get("data", "Travel.dat");

    /**
     * Saves the TravelManager to data/Travel.dat
     */
    public void save(TravelManager manager) {
        ensureDirectoryExists();  // Create data/ if missing

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH.toFile()))) {
            oos.writeObject(manager);
            System.out.println("Travel data saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to save travel data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads TravelManager from data/Travel.dat
     * Returns new instance if file doesn't exist
     */
    public TravelManager load() {
        if (!Files.exists(FILE_PATH)) {
            System.out.println("No saved data found. Starting fresh.");
            return new TravelManager();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_PATH.toFile()))) {
            TravelManager manager = (TravelManager) ois.readObject();
            System.out.println("Travel data loaded successfully from " + FILE_PATH);
            return manager;
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. Starting fresh.");
            return new TravelManager();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return new TravelManager();
        }
    }

    /**
     * Ensures the 'data' directory exists
     */
    private void ensureDirectoryExists() {
        try {
            Files.createDirectories(FILE_PATH.getParent());
        } catch (IOException e) {
            System.err.println("Could not create data directory: " + e.getMessage());
        }
    }
}