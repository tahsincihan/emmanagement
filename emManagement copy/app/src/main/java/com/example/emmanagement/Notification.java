package com.example.emmanagement;

public class Notification {
    private String title;
    private String message;
    private String date;

    // Constructor
    public Notification(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
