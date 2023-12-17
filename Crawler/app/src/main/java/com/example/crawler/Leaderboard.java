
package com.example.crawler;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard {

    private List<ScoreEntry> entries = new ArrayList<>();
    private static Leaderboard leaderboard = new Leaderboard();

    private String[] names;
    private int[] scores;

    public Leaderboard() {
        names = new String[5];
        scores = new int[5];
    }


    public void addScore(ScoreEntry scoreEntry) {
        for (ScoreEntry entry : entries) {
            if (entry.getPlayerName().equals(scoreEntry.getPlayerName())
                    && entry.getScore() == scoreEntry.getScore()) {
                return;
            }
        }

        entries.add(scoreEntry);
        Collections.sort(entries, Comparator.comparingInt(ScoreEntry::getScore).reversed());

        while (entries.size() > 5) {
            entries.remove(entries.size() - 1);
        }
    }

    public void resetLeaderboard() {
        entries.clear();
    }


    public static Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public List<ScoreEntry> getTopScores(int count) {
        return entries;
    }

    public String getTopScoresAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            ScoreEntry entry = entries.get(i);
            sb.append((i + 1)).append(". ").append(entry.getPlayerName())
                    .append(": ").append(entry.getScore())
                    .append(" - ").append(entry.getFormattedDateTime()).append("\n");
        }
        return sb.toString();
    }
}