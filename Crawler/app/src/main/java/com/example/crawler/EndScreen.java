package com.example.crawler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.widget.ListView;
import java.util.List;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endingscreen);

        ListView leaderboardListView = findViewById(R.id.leaderboardListView);
        List<ScoreEntry> topScores = Leaderboard.getLeaderboard().getTopScores(5);
        LeaderboardAdapter adapter = new LeaderboardAdapter(
                this, R.layout.leaderboard_entry, topScores);
        leaderboardListView.setAdapter(adapter);
        Intent intent = getIntent();
        int playerScore = intent.getIntExtra("score", 0);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Your Score: " + playerScore);

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndScreen.this, StartScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        showPopupMessage(playerScore);
    }

    private void showPopupMessage(int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = (score > 0) ? "Congrats, you won!" : "Sorry, you lost!";
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
