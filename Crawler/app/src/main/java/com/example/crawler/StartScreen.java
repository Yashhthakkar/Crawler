package com.example.crawler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import com.google.android.material.textfield.TextInputLayout;

public class StartScreen extends AppCompatActivity {

    private String selectedDifficulty;
    private int selectedSpriteResId;
    private ImageView selectedSpriteImageView;

    private void selectSprite(int spriteResId, ImageView imageView) {
        if (selectedSpriteImageView != null) {
            selectedSpriteImageView.clearColorFilter();
        }
        imageView.setColorFilter(Color.argb(100, 0, 255, 0));
        selectedSpriteResId = spriteResId;
        selectedSpriteImageView = imageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        TextInputLayout playerNameTextInputLayout = findViewById(R.id.textInputLayout2);
        EditText playerNameEditText = playerNameTextInputLayout.getEditText();
        RadioButton easyRadioButton = findViewById(R.id.radioButton);
        RadioButton mediumRadioButton = findViewById(R.id.radioButton2);
        RadioButton hardRadioButton = findViewById(R.id.radioButton3);
        ImageView sprite1ImageView = findViewById(R.id.imageView);
        ImageView sprite2ImageView = findViewById(R.id.imageView2);
        ImageView sprite3ImageView = findViewById(R.id.imageView3);
        Button continueButton = findViewById(R.id.button);

        easyRadioButton.setOnClickListener(v -> {
            mediumRadioButton.setChecked(false);
            hardRadioButton.setChecked(false);
            selectedDifficulty = "Easy";
        });

        mediumRadioButton.setOnClickListener(v -> {
            easyRadioButton.setChecked(false);
            hardRadioButton.setChecked(false);
            selectedDifficulty = "Medium";
        });

        hardRadioButton.setOnClickListener(v -> {
            easyRadioButton.setChecked(false);
            mediumRadioButton.setChecked(false);
            selectedDifficulty = "Hard";
        });

        sprite1ImageView.setOnClickListener(v ->
                selectSprite(R.drawable.sprite1, sprite1ImageView));
        sprite2ImageView.setOnClickListener(v
                -> selectSprite(R.drawable.sprite2, sprite2ImageView));
        sprite3ImageView.setOnClickListener(v
                -> selectSprite(R.drawable.sprite3, sprite3ImageView));

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = playerNameEditText.getText().toString().trim();
                if (playerName.isEmpty()) {
                    Toast.makeText(StartScreen.this,
                            "Please enter a valid name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedDifficulty == null) {
                    Toast.makeText(StartScreen.this,
                            "Please select a difficulty level!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedSpriteResId == 0) {
                    Toast.makeText(StartScreen.this,
                            "Please select a character sprite!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(StartScreen.this, Room1.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("difficulty", selectedDifficulty);
                intent.putExtra("characterSprite", selectedSpriteResId);
                startActivity(intent);
            }
        });
    }
}
