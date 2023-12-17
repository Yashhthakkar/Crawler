package com.example.crawler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import java.util.Date;

public class Room3 extends AppCompatActivity {

    private Tile[][] tiles;
    private Player player;
    private Handler handler = new Handler();
    private TextView scoreTextView;
    private ImageView playerSpriteImageView;
    private final float glideDistance = 10.0f;
    private float screenWidth;
    private float screenHeight;
    private int score;
    private boolean scoreAdded = false;

    private ImageView lightningPowerUpImageView1;
    private ImageView lightningPowerUpImageView2;
    private ImageView heartPowerUpImageView1;
    private ImageView heartPowerUpImageView2;

    private ImageView spearImageView; // ImageView for the spear
    private ImageView swordImageView; // ImageView for the sword
    private boolean collidedWithWeapon = false;

    private Enemy enemy1;
    private Enemy enemy2;
    private ImageView enemy1ImageView;
    private ImageView enemy2ImageView;
    private TextView hpTextView;
    private Handler enemyMovementHandler = new Handler();

    private PowerUp lightning1 = new LightningPowerUp(player);
    private PowerUp heart1 = new HeartPowerUp(player);
    private PowerUp lightning2 = new LightningPowerUp(player);
    private PowerUp heart2 = new HeartPowerUp(player);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room3);
        scoreTextView = findViewById(R.id.scoreTextViewRoom3);
        playerSpriteImageView = findViewById(R.id.spriteRoom3);
        Intent intent = getIntent();
        int characterSpriteResId = intent.getIntExtra("characterSprite", 0);
        String playerName = intent.getStringExtra("playerName");
        String difficulty = intent.getStringExtra("difficulty");
        score = intent.getIntExtra("score", 0);
        int playerHP = intent.getIntExtra("HP", 1000);
        scoreTextView.setText("" + score);
        TextView nameTV = findViewById(R.id.playerNameRoom3);
        nameTV.setText(playerName);
        TextView diffTV = findViewById(R.id.difficultyRoom3);
        diffTV.setText(difficulty);
        playerSpriteImageView.setImageResource(characterSpriteResId);
        updateScoreDisplay();
        player = new Player();
        player.setName(playerName);
        player.setHealth(playerHP);
        player.reset(); // Reset player's state
        Leaderboard.getLeaderboard().resetLeaderboard();
        hpTextView = findViewById(R.id.textView9);
        updateHpDisplay();

        String[] room3Map = {
            "WWWWWWWWWW",
            "WS.......E",
            "WW.WWWWWWW",
            "WW.......W",
            "WWWWWWWWWW"
        };
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        initializeTiles(room3Map);

        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);

        enemy1ImageView = findViewById(R.id.enemy1ImageView);
        enemy2ImageView = findViewById(R.id.enemy2ImageView);

        spearImageView = findViewById(R.id.spearImageView);
        swordImageView = findViewById(R.id.swordImageView);

        hpTextView = findViewById(R.id.textView9);

        EnemyFactory enemyFactory = new EnemyFactory();
        enemy1 = enemyFactory.createEnemy("FAST", player, difficulty);
        enemy2 = enemyFactory.createEnemy("SLOW", player, difficulty);

        if (enemy1 != null) {
            enemy1.setX((int) enemy1ImageView.getX());
            enemy1.setSpeed(2);
            enemy1.setDirection(1);
            player.addObserver(enemy1);
        }

        if (enemy2 != null) {
            enemy2.setX((int) enemy2ImageView.getX());
            enemy2.setSpeed(2);
            enemy2.setDirection(1);
            player.addObserver(enemy2);
        }

        enemy1.setSpeed(2);
        enemy1.setDirection(1);
        enemy2.setSpeed(1);
        enemy2.setDirection(-1);

        startEnemyMovement();

        int speedFactor = intent.getIntExtra("speedFactor", 5);

        View.OnTouchListener arrowButtonListener = new View.OnTouchListener() {
            private Handler repeatUpdateHandler = new Handler();
            private boolean mAutoIncrement;
            private boolean mAutoDecrement;
            private int dx;
            private int dy;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (v.getId() == R.id.upButton) {
                        mAutoDecrement = true;
                        dx = 0;
                        dy = -speedFactor;
                    } else if (v.getId() == R.id.downButton) {
                        mAutoIncrement = true;
                        dx = 0;
                        dy = speedFactor;
                    } else if (v.getId() == R.id.leftButton) {
                        mAutoDecrement = true;
                        dx = -speedFactor;
                        dy = 0;
                    } else if (v.getId() == R.id.rightButton) {
                        mAutoIncrement = true;
                        dx = speedFactor;
                        dy = 0;
                    }

                    repeatUpdateHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAutoIncrement) {
                                movePlayer(dx, dy, difficulty, characterSpriteResId);
                                repeatUpdateHandler.postDelayed(this, 100);
                            } else if (mAutoDecrement) {
                                movePlayer(dx, dy, difficulty, characterSpriteResId);
                                repeatUpdateHandler.postDelayed(this, 100);
                            }
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mAutoIncrement = false;
                    mAutoDecrement = false;
                    repeatUpdateHandler.removeCallbacksAndMessages(null);
                }

                return false;
            }
        };

        upButton.setOnTouchListener(arrowButtonListener);
        downButton.setOnTouchListener(arrowButtonListener);
        leftButton.setOnTouchListener(arrowButtonListener);
        rightButton.setOnTouchListener(arrowButtonListener);

        heartPowerUpImageView1 = findViewById(R.id.heartPowerUpImageView1);
        lightningPowerUpImageView1 = findViewById(R.id.lightningPowerUpImageView1);
        lightningPowerUpImageView2 = findViewById(R.id.lightningPowerUpImageView2);
        heartPowerUpImageView2 = findViewById(R.id.heartPowerUpImageView2);

        lightningPowerUpImageView1.setVisibility(View.VISIBLE);
        heartPowerUpImageView1.setVisibility(View.VISIBLE);
        lightningPowerUpImageView2.setVisibility(View.VISIBLE);
        heartPowerUpImageView2.setVisibility(View.VISIBLE);

        startScoreCountdown();
        startCollisionCheck();
    }

    public PowerUp getLightning1() {
        return lightning1;
    }

    public PowerUp getHeart1() {
        return heart1;
    }

    public PowerUp getLightning2() {
        return lightning2;
    }

    public PowerUp getHeart2() {
        return heart2;
    }

    public void setLightning1(PowerUp lightning1) {
        this.lightning1 = lightning1;
    }

    public void setHeart1(PowerUp heart1) {
        this.heart1 = heart1;
    }

    public void setLightning2(PowerUp lightning2) {
        this.lightning2 = lightning2;
    }

    public void setHeart2(PowerUp heart2) {
        this.heart2 = heart2;
    }


    private void startCollisionCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkCollision();
                handler.postDelayed(this, 200); // Adjust the frequency as necessary
            }
        }, 200);
    }

    // Check for collisions with enemies or weapons
    private void checkCollision() {
        if (isCollision(playerSpriteImageView, spearImageView)) {
            attachWeaponToPlayer(spearImageView);
        }
        if (isCollision(playerSpriteImageView, swordImageView)) {
            attachWeaponToPlayer(swordImageView);
        }

        if (enemy1.isActive() && isPlayerEnemyCollision(playerSpriteImageView, enemy1ImageView)) {
            handleEnemyCollision(enemy1, enemy1ImageView);
        }
        if (enemy2.isActive() && isPlayerEnemyCollision(playerSpriteImageView, enemy2ImageView)) {
            handleEnemyCollision(enemy2, enemy2ImageView);
        }
    }

    private void handleEnemyCollision(Enemy enemy, ImageView enemyImageView) {
        if (collidedWithWeapon) {
            enemy.setHP(0); // Enemy is destroyed
            enemy.deactivate(); // Deactivate the enemy
            enemyImageView.setVisibility(View.GONE); // Hide the specific enemy image
            score += 150; // Add points for defeating an enemy with a weapon
            updateScoreDisplay();
            collidedWithWeapon = false; // Reset the flag
        } else {
            // Handle the collision without a weapon
            handleCollision(enemy);
        }
    }

    private boolean isPlayerEnemyCollision(ImageView playerImageView, ImageView enemyImageView) {
        if (playerImageView == null
                || enemyImageView == null || enemyImageView.getVisibility() != View.VISIBLE) {
            return false;
        }

        int[] playerPos = new int[2];
        int[] enemyPos = new int[2];

        playerImageView.getLocationOnScreen(playerPos);
        enemyImageView.getLocationOnScreen(enemyPos);

        int playerLeft = playerPos[0];
        int playerTop = playerPos[1];
        int playerRight = playerLeft + playerImageView.getWidth();
        int playerBottom = playerTop + playerImageView.getHeight();

        int enemyLeft = enemyPos[0];
        int enemyTop = enemyPos[1];
        int enemyRight = enemyLeft + enemyImageView.getWidth();
        int enemyBottom = enemyTop + enemyImageView.getHeight();

        return !(playerRight < enemyLeft
                || playerLeft > enemyRight || playerBottom < enemyTop || playerTop > enemyBottom);
    }

    private void attachWeaponToPlayer(ImageView weaponImageView) {
        collidedWithWeapon = true; // Player collided with a weapon
        weaponImageView.setVisibility(View.GONE); // Hide the weapon
    }

    private void startEnemyMovement() {
        enemyMovementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveEnemy(enemy1, enemy1ImageView);
                moveEnemy(enemy2, enemy2ImageView);
                enemyMovementHandler.postDelayed(this, 100);
            }
        }, 100);
    }

    private void moveEnemy(Enemy enemy, ImageView enemyImageView) {
        if (enemy == null || enemyImageView == null) {
            return;
        }

        int newX = (int) enemyImageView.getX() + enemy.getSpeed() * enemy.getDirection();

        // Check boundaries and set new position or change direction
        if (newX <= 0 || newX >= screenWidth - enemyImageView.getWidth()) {
            enemy.setDirection(-enemy.getDirection());
        } else {
            enemyImageView.setX(newX);
        }

        // Check for collision with the player
        if (Math.abs(enemyImageView.getX() - playerSpriteImageView.getX()) < 300
                && Math.abs(enemyImageView.getY() - playerSpriteImageView.getY()) < 300) {
            handleCollision(enemy);
        }
        enemyMovementHandler.postDelayed(()
                -> moveEnemy(enemy, enemyImageView), 150); // Schedule the next move
    }
    private void handleCollision(Enemy enemy) {
        enemy.update(player.getX(), player.getY()); // Get the damage based on the enemy type
        // Reduce player's HP
        if (player.getHealth() <= 0) {
            System.out.println("Health:" + player.getHealth());
            navigateToGameOver();
            return;
        }
        updateHpDisplay();
        updateScoreDisplay();
    }
    private void navigateToGameOver() {
        // Add player's score to the leaderboard
        Leaderboard.getLeaderboard().addScore(new ScoreEntry(player.getName(), score, new Date()));

        // Create an intent to start EndScreen activity
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra("score", score); // Pass player's score to EndScreen
        startActivity(intent);
        finish();
    }


    private void updateHpDisplay() {
        hpTextView.setText("HP: " + player.getHealth());
    }

    private void movePlayer(int dx, int dy, String diff, int sId) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        // Calculate the potential new positions
        float newScreenX = playerSpriteImageView.getTranslationX() + (glideDistance * dx);
        float newScreenY = playerSpriteImageView.getTranslationY() + (glideDistance * dy);

        // Check the boundaries to keep the sprite within the screen
        if (newScreenX >= 0 && newScreenX <= (screenWidth - playerSpriteImageView.getWidth())
                && newScreenY >= 0 && newScreenY
                <= (screenHeight - playerSpriteImageView.getHeight())) {

            if (isValidMove(newX, newY)) {
                player.setX(newX);
                player.setY(newY);
                if (newY > 160) {
                    Leaderboard.getLeaderboard().addScore(
                            new ScoreEntry(player.getName(), score, new Date()));
                    Intent intent = new Intent(Room3.this, EndScreen.class);
                    intent.putExtra("playerName", player.getName());
                    intent.putExtra("difficulty", diff);
                    intent.putExtra("characterSprite", sId);
                    intent.putExtra("score", score);
                    intent.putExtra("HP", player.getHealth());
                    startActivity(intent);
                }

                playerSpriteImageView.setTranslationX(newScreenX);
                playerSpriteImageView.setTranslationY(newScreenY);
            }
        }
        checkPowerUpCollision();
    }


    private void checkPowerUpCollision() {
        if (isCollision(playerSpriteImageView, lightningPowerUpImageView1)) {
            handlePowerUpCollision(lightningPowerUpImageView1, "lightning", lightning1);
        }
        if (isCollision(playerSpriteImageView, heartPowerUpImageView1)) {
            handlePowerUpCollision(heartPowerUpImageView1, "heart", heart1);
        }
        // Repeat for the second set of power-ups
        if (isCollision(playerSpriteImageView, lightningPowerUpImageView2)) {
            handlePowerUpCollision(lightningPowerUpImageView2, "lightning", lightning2);
        }
        if (isCollision(playerSpriteImageView, heartPowerUpImageView2)) {
            handlePowerUpCollision(heartPowerUpImageView2, "heart", heart2);
        }
    }


    private boolean isCollision(ImageView sprite, ImageView powerUp) {
        if (sprite == null || powerUp == null || powerUp.getVisibility() != View.VISIBLE) {
            return false;
        }

        int[] spritePos = new int[2];
        int[] powerUpPos = new int[2];

        sprite.getLocationOnScreen(spritePos);
        powerUp.getLocationOnScreen(powerUpPos);

        int spriteLeft = spritePos[0];
        int spriteTop = spritePos[1];
        int spriteRight = spriteLeft + sprite.getWidth();
        int spriteBottom = spriteTop + sprite.getHeight();

        int powerUpLeft = powerUpPos[0];
        int powerUpTop = powerUpPos[1];
        int powerUpRight = powerUpLeft + powerUp.getWidth();
        int powerUpBottom = powerUpTop + powerUp.getHeight();

        return !(spriteRight < powerUpLeft || spriteLeft > powerUpRight
                || spriteBottom < powerUpTop || spriteTop > powerUpBottom);
    }


    private void handlePowerUpCollision(ImageView powerUp, String type, PowerUp powerup) {
        powerup.usePowerUp(player);
        updateHpDisplay();
        powerUp.setVisibility(View.GONE); // Make power-up disappear
    }

    private void increasePlayerSpeed() {
        int currentSpeedFactor = player.getSpeedFactor();
        player.setSpeedFactor(currentSpeedFactor + 2);
    }



    private void initializeTiles(String[] map) {
        int height = map.length;
        int width = map[0].length();
        tiles = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char tileChar = map[y].charAt(x);
                tiles[x][y] = new Tile(tileChar != 'W');
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        return true;  // Allowing unrestricted movement
    }

    private void startScoreCountdown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (score > 0) {
                    score--;
                    updateScoreDisplay();
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void updateScoreDisplay() {
        scoreTextView.setText("Score: " + score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}