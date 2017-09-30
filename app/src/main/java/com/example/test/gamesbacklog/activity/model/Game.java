package com.example.test.gamesbacklog.activity.model;
import java.io.Serializable;

/**
 * Created by Remy on 26-9-2017.
 */


public class Game implements Serializable {

    // Property help us to keep data
    private int id;
    private String title;
    private String platform;
    private String dateAdded; // String, since you cannot save date/time values in SQLite
    private String gameStatus;
    private String notes;

    public Game(int id, String title, String platform, String dateAdded, String gameStatus, String notes) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.dateAdded = dateAdded;
        this.gameStatus = gameStatus;
        this.notes = notes;
    }

    public Game() {

    }

    public long getId() {
        return (long) id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

