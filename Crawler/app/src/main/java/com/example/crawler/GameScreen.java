package com.example.crawler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen);

        // Find views by ID
        ImageView characterSprite = findViewById(R.id.imageView4);
        TextView playerNameTextView = findViewById(R.id.textView3);
        TextView difficultyTextView = findViewById(R.id.textView6);
        TextView healthTextView = findViewById(R.id.textView5);
        Button continueButton = findViewById(R.id.button4);

        // Retrieve player information from the Intent
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("playerName");
        String difficulty = intent.getStringExtra("difficulty");
        int characterSpriteResId = intent.getIntExtra("characterSprite", 0); // Default value is 0

        // Calculate starting health based on chosen difficulty
        int startingHealth;
        switch (difficulty) {
        case "Hard":
            startingHealth = 100;
            break;
        case "Medium":
            startingHealth = 200;
            break;
        default: // "Easy" and any other cases
            startingHealth = 300;
            break;
        }

        // Set the views with the retrieved player information
        playerNameTextView.setText(playerName);
        difficultyTextView.setText(difficulty);
        healthTextView.setText("Health: " + startingHealth + "/" + startingHealth);
        characterSprite.setImageResource(characterSpriteResId);

        // Set OnClickListener for the Continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Room1
                Intent room1Intent = new Intent(GameScreen.this, Room1.class);
                room1Intent.putExtra("playerName", playerName);
                room1Intent.putExtra("difficulty", difficulty);
                room1Intent.putExtra("characterSprite", characterSpriteResId);
                room1Intent.putExtra("startingHealth", startingHealth);
                startActivity(room1Intent);
            }
        });
    }
}
