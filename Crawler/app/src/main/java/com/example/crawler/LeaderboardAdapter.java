package com.example.crawler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<ScoreEntry> {

    private List<ScoreEntry> leaderboardEntries;
    private Context context;

    public LeaderboardAdapter(Context context, int resource, List<ScoreEntry> entries) {
        super(context, resource, entries);
        this.context = context;
        this.leaderboardEntries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScoreEntry entry = leaderboardEntries.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.leaderboard_entry, parent, false);
        }

        TextView playerNameTextView = convertView.findViewById(R.id.playerNameTextView);
        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);
        TextView dateTimeTextView = convertView.findViewById(R.id.dateTimeTextView);

        playerNameTextView.setText(entry.getPlayerName());
        scoreTextView.setText("Score: " + entry.getScore());
        dateTimeTextView.setText(entry.getFormattedDateTime());

        return convertView;
    }
}