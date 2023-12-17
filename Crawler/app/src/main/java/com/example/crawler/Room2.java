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
import android.util.Log;

public class Room2 extends AppCompatActivity {

    private Tile[][] tiles;
    private Player player;
    private Handler handler = new Handler();
    private TextView scoreTextView;
    private ImageView playerSpriteImageView;
    private final float glideDistance = 10.0f;
    private float screenWidth;
    private float screenHeight;
    private int score;
    private Enemy enemy1;
    private Enemy enemy2;
    private ImageView firePowerUpImageView1;
    private ImageView heartPowerUpImageView1;
    private ImageView firePowerUpImageView2;
    private ImageView heartPowerUpImageView2;
    private ImageView clubImageView; // ImageView for the club
    private ImageView swordImageView; // ImageView for the sword
    private boolean collidedWithWeapon = false;
    private ImageView enemy1ImageView;
    private ImageView enemy2ImageView;
    private TextView hPTextView;
    private Handler enemyMovementHandler = new Handler();

    private PowerUp fire1 = new FirePowerUp(player);
    private PowerUp heart1 = new HeartPowerUp(player);
    private PowerUp fire2 = new FirePowerUp(player);
    private PowerUp heart2 = new HeartPowerUp(player);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room2);
        scoreTextView = findViewById(R.id.scoreTextViewRoom2);
        playerSpriteImageView = findViewById(R.id.spriteRoom2);
        Intent intent = getIntent();
        int characterSpriteResId = intent.getIntExtra("characterSprite", 0);
        String playerName = intent.getStringExtra("playerName");
        System.out.println(playerName);
        String difficulty = intent.getStringExtra("difficulty");
        score = intent.getIntExtra("score", 0);
        int playerHP = intent.getIntExtra("HP", 1000);
        int speedFactor = intent.getIntExtra("speedFactor", 5);
        Log.d("Room2", "Received Speed Factor: " + speedFactor);
        scoreTextView.setText("" + score);
        TextView nameTV = findViewById(R.id.playerNameRoom2);
        nameTV.setText(playerName);
        TextView diffTV = findViewById(R.id.difficultyRoom2);
        diffTV.setText(difficulty);
        playerSpriteImageView.setImageResource(characterSpriteResId);
        updateScoreDisplay();
        player = new Player();
        player.setName(playerName);
        player.setHealth(playerHP);
        player.setSpeedFactor(speedFactor);
        player.reset(); // Reset player's state
        Leaderboard.getLeaderboard().resetLeaderboard();
        hPTextView = findViewById(R.id.textView9); // Initialize the HP TextView
        updateHpDisplay(); // Update the HP display with the current HP
        String[] room2Map = {
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

        initializeTiles(room2Map);

        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        enemy1ImageView = findViewById(R.id.enemy1ImageView);
        enemy2ImageView = findViewById(R.id.enemy2ImageView);

        clubImageView = findViewById(R.id.clubImageView);
        swordImageView = findViewById(R.id.swordImageView);

        hPTextView = findViewById(R.id.textView9);
        EnemyFactory enemyFactory = new EnemyFactory();
        enemy1 = enemyFactory.createEnemy("FAST", player, difficulty); // Creating the enemy
        if (enemy1 != null) {
            enemy1.setX((int) enemy1ImageView.getX()); // Initializing position
            enemy1.setSpeed(2); // Set speed
            enemy1.setDirection(1); // Set initial direction
            player.addObserver(enemy1); // Register enemy1 as an observer
        }

        enemy2 = enemyFactory.createEnemy("SLOW", player, difficulty); // Creating the enemy
        if (enemy2 != null) {
            enemy2.setX((int) enemy1ImageView.getX()); // Initializing position
            enemy2.setSpeed(2); // Set speed
            enemy2.setDirection(1); // Set initial direction
            player.addObserver(enemy2); // Register enemy2 as an observer
        }

        enemy1.setSpeed(2);
        enemy1.setDirection(1);

        enemy2.setSpeed(1);
        enemy2.setDirection(-1);

        // Start moving the enemies
        startEnemyMovement();

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


        firePowerUpImageView1 = findViewById(R.id.firePowerUpImageView1);
        heartPowerUpImageView1 = findViewById(R.id.heartPowerUpImageView1);
        firePowerUpImageView2 = findViewById(R.id.firePowerUpImageView2);
        heartPowerUpImageView2 = findViewById(R.id.heartPowerUpImageView2);

        firePowerUpImageView1.setVisibility(View.VISIBLE);
        heartPowerUpImageView1.setVisibility(View.VISIBLE);
        firePowerUpImageView2.setVisibility(View.VISIBLE);
        heartPowerUpImageView2.setVisibility(View.VISIBLE);

        startScoreCountdown();
        startCollisionCheck();
    }

    // Getter and Setter methods for Fire1
    public PowerUp getFire1() {
        return fire1;
    }

    public void setFire1(PowerUp fire1) {
        this.fire1 = fire1;
    }

    // Getter and Setter methods for Heart1
    public PowerUp getHeart1() {
        return heart1;
    }

    public void setHeart1(PowerUp heart1) {
        this.heart1 = heart1;
    }

    // Getter and Setter methods for Fire2
    public PowerUp getFire2() {
        return fire2;
    }

    public void setFire2(PowerUp fire2) {
        this.fire2 = fire2;
    }

    // Getter and Setter methods for Heart2
    public PowerUp getHeart2() {
        return heart2;
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
        if (isCollision(playerSpriteImageView, clubImageView)) {
            attachWeaponToPlayer(clubImageView);
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

        return !(playerRight < enemyLeft || playerLeft > enemyRight
                || playerBottom < enemyTop || playerTop > enemyBottom);
    }

    // Method to handle weapon collision
    private void attachWeaponToPlayer(ImageView weaponImageView) {
        collidedWithWeapon = true; // Player collided with a weapon
        weaponImageView.setVisibility(View.GONE); // Hide the weapon
    }

    // Method to handle enemy collision
    private void handleEnemyCollision(Enemy enemy, ImageView enemyImageView) {
        if (collidedWithWeapon) {
            // Destroy enemy and update score
            enemy.deactivate();
            enemyImageView.setVisibility(View.GONE);
            score += 150;
            updateScoreDisplay();
            collidedWithWeapon = false; // Reset the flag
        } else {
            // Regular enemy collision logic
            handleCollision(enemy); // Use your existing method
        }
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
        enemyMovementHandler.postDelayed(() -> moveEnemy(enemy, enemyImageView), 150);
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
        Intent intent = new Intent(this, EndScreen.class);
        startActivity(intent);
        finish(); // Optional: finish Room1 activity
    }


    private void handlePowerUpCollision(ImageView powerUp, String type, PowerUp powerup) {
        player.setScore(score);
        powerup.usePowerUp(player);
        if (type.equals("fire")) {
            score += 50; // Increase score for fire power-up
            updateScoreDisplay();
        }
        updateHpDisplay();
        powerUp.setVisibility(View.GONE); // Make power-up disappear
    }





    private void updateHpDisplay() {
        hPTextView.setText("HP: " + player.getHealth());
    }

    private void movePlayer(int dx, int dy, String diff, int sId) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        // Calculate the potential new positions
        float newScreenX = playerSpriteImageView.getTranslationX() + (glideDistance * dx);
        float newScreenY = playerSpriteImageView.getTranslationY() + (glideDistance * dy);

        // Check the boundaries to keep the sprite within the screen
        if (newScreenX >= 0 && newScreenX
                <= (screenWidth - playerSpriteImageView.getWidth())
                && newScreenY >= 0 && newScreenY
                <= (screenHeight - playerSpriteImageView.getHeight())) {

            if (isValidMove(newX, newY)) {
                player.setX(newX);
                player.setY(newY);

                if (newY > 160) { // Assuming this condition is to move to the next room
                    // Create an Intent to start Room3
                    Intent intent = new Intent(Room2.this, Room3.class);
                    intent.putExtra("playerName", player.getName());
                    intent.putExtra("difficulty", diff);
                    intent.putExtra("characterSprite", sId);
                    intent.putExtra("score", score);
                    intent.putExtra("HP", player.getHealth());
                    intent.putExtra("speedFactor", player.getSpeedFactor());
                    startActivity(intent);
                }

                playerSpriteImageView.setTranslationX(newScreenX);
                playerSpriteImageView.setTranslationY(newScreenY);
            }
        }
        checkPowerUpCollision();
    }

    private void checkPowerUpCollision() {
        if (isCollision(playerSpriteImageView, firePowerUpImageView1)) {
            handlePowerUpCollision(firePowerUpImageView1, "fire", fire1);
        }
        if (isCollision(playerSpriteImageView, heartPowerUpImageView1)) {
            handlePowerUpCollision(heartPowerUpImageView1, "heart", heart1);
        }
        // Repeat for the second set of power-ups
        if (isCollision(playerSpriteImageView, firePowerUpImageView2)) {
            handlePowerUpCollision(firePowerUpImageView2, "fire", fire2);
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