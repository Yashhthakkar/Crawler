package com.example.crawler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScoreEntry {
    private String playerName;
    private int score;
    private Date date;

    public ScoreEntry(String playerName, int score, Date date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    // Getter for player name
    public String getPlayerName() {
        return playerName;
    }

    // Setter for player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Setter for score
    public void setScore(int score) {
        this.score = score;
    }

    // Getter for date/time
    public Date getDate() {
        return date;
    }

    // Setter for date/time
    public void setDateTime(Date dateTime) {
        this.date = dateTime;
    }

    // Method to get a formatted date/time string
    public String getFormattedDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
}
