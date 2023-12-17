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


public class Room1 extends AppCompatActivity {

    private Tile[][] tiles;
    private Player player;
    private int score = 100;
    private Handler handler = new Handler();
    private TextView scoreTextView;
    private ImageView playerSpriteImageView;
    private final float glideDistance = 10.0f;
    private float screenHeight;
    private float screenWidth;
    private Enemy enemy1;
    private Enemy enemy2;
    private ImageView enemy1ImageView;
    private ImageView enemy2ImageView;
    private TextView hPTextView;
    private ImageView firePowerUpImageView;

    private ImageView firePowerUpImageView2;
    private ImageView lightningPowerUpImageView2;

    private ImageView lightningPowerUpImageView;

    private ImageView clubImageView;
    private ImageView spearImageView;
    private boolean collidedWithWeapon = false;
    private Handler enemyMovementHandler = new Handler();

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    private PowerUp fire1 = new FirePowerUp(player);
    private PowerUp lightning1 = new LightningPowerUp(player);
    private PowerUp fire2 = new FirePowerUp(player);
    private PowerUp lightning2 = new LightningPowerUp(player);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room1);

        firePowerUpImageView = findViewById(R.id.firePowerUpImageView1);
        lightningPowerUpImageView = findViewById(R.id.lightningPowerUpImageView1);
        firePowerUpImageView2 = findViewById(R.id.firePowerUpImageView2);
        lightningPowerUpImageView2 = findViewById(R.id.lightningPowerUpImageView2);
        clubImageView = findViewById(R.id.clubImageView);
        spearImageView = findViewById(R.id.spearImageView);
        scoreTextView = findViewById(R.id.scoreTextViewRoom1);
        playerSpriteImageView = findViewById(R.id.spriteRoom1);
        Intent intent = getIntent();
        int characterSpriteResId = intent.getIntExtra("characterSprite", 0);
        String playerName = intent.getStringExtra("playerName");
        String difficulty = intent.getStringExtra("difficulty");

        TextView nameTV = findViewById(R.id.playerNameRoom1);
        nameTV.setText(playerName);
        TextView diffTV = findViewById(R.id.difficultyRoom1);
        diffTV.setText(difficulty);
        playerSpriteImageView.setImageResource(characterSpriteResId);
        updateScoreDisplay();
        player = new Player();
        player.setName(playerName);
        player.reset();
        Leaderboard.getLeaderboard().resetLeaderboard();
        String[] room1Map = {
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
        initializeTiles(room1Map);
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        // Getting references to the ImageView elements
        enemy1ImageView = findViewById(R.id.enemy1ImageView);
        enemy2ImageView = findViewById(R.id.enemy2ImageView);
        hPTextView = findViewById(R.id.textView9);
        // Creating enemies using the EnemyFactory and associating them with the ImageViews
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
                int speedFactor = player.getSpeedFactor();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (v.getId() == R.id.upButton) {
                        mAutoDecrement = true;
                        dx = 0;
                        dy = -speedFactor; // Use speed factor
                    } else if (v.getId() == R.id.downButton) {
                        mAutoIncrement = true;
                        dx = 0;
                        dy = speedFactor; // Use speed factor
                    } else if (v.getId() == R.id.leftButton) {
                        mAutoDecrement = true;
                        dx = -speedFactor; // Use speed factor
                        dy = 0;
                    } else if (v.getId() == R.id.rightButton) {
                        mAutoIncrement = true;
                        dx = speedFactor; // Use speed factor
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

        if (difficulty.equals("Hard")) {
            player.setHealth(200);
        } else if (difficulty.equals("Medium")) {
            player.setHealth(400);
        }
        updateHpDisplay();
        startScoreCountdown();
        startCollisionCheck();

        firePowerUpImageView.setVisibility(View.VISIBLE);
        lightningPowerUpImageView.setVisibility(View.VISIBLE);
        firePowerUpImageView2.setVisibility(View.VISIBLE);
        lightningPowerUpImageView2.setVisibility(View.VISIBLE);


        clubImageView.setVisibility(View.VISIBLE);
        spearImageView.setVisibility(View.VISIBLE);


    }

    // Method to start checking for collisions
    private void startCollisionCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkCollision();
                handler.postDelayed(this, 200); // Adjust the collision check frequency as necessary
            }
        }, 200);
    }

    private PowerUp getFire1() {
        return fire1;
    }

    // Getter for lightning1
    private PowerUp getLightning1() {
        return lightning1;
    }

    // Getter for fire2
    private PowerUp getFire2() {
        return fire2;
    }

    // Getter for lightning2
    private PowerUp getLightning2() {
        return lightning2;
    }

    public void setFire1(PowerUp fire1) {
        this.fire1 = fire1;
    }

    public void setLightning1(PowerUp lightning1) {
        this.lightning1 = lightning1;
    }

    public void setFire2(PowerUp fire2) {
        this.fire2 = fire2;
    }

    public void setLightning2(PowerUp lightning2) {
        this.lightning2 = lightning2;
    }


    // Method to check for collisions between the player and enemies
    private void checkCollision() {
        if (enemy1.isActive() && isPlayerEnemyCollision(playerSpriteImageView, enemy1ImageView)) {
            handleCollision(player, enemy1, enemy1ImageView);
        }
        if (enemy2.isActive() && isPlayerEnemyCollision(playerSpriteImageView, enemy2ImageView)) {
            handleCollision(player, enemy2, enemy2ImageView);
        }
    }


    // Method to handle collision effects
    private void handleCollision(Player player, Enemy enemy, ImageView enemyImageView) {
        if (collidedWithWeapon) {
            enemy.setHP(0); // Enemy is destroyed
            enemy.deactivate(); // Deactivate the enemy
            enemyImageView.setVisibility(View.GONE); // Hide the specific enemy image
            score += 150; // Add points for defeating an enemy with a weapon
            updateScoreDisplay();
            collidedWithWeapon = false; // Reset the flag
        } else {
            int damage = enemy.getDamage();
            int updatedHealth = player.getHealth() - damage;
            player.setHealth(updatedHealth);
            if (updatedHealth <= 0) {
                navigateToGameOver();
            }
            updateHpDisplay();
        }
    }


    private void navigateToGameOver() {
        Intent intent = new Intent(this, EndScreen.class);
        startActivity(intent);
        finish(); // Optional: finish Room1 activity
    }

    // Method to update the displayed HP
    private void updateHpDisplay() {
        hPTextView.setText("HP: " + player.getHealth());
    }

    // Method to determine if there is a collision between the player and an enemy
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



    private void startEnemyMovement() {
        enemyMovementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (enemy1.isActive()) {
                    moveEnemy(enemy1, enemy1ImageView);
                }
                if (enemy2.isActive()) {
                    moveEnemy(enemy2, enemy2ImageView);
                }
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
        enemy.update(player.getX(), player.getY());
        player.setHealth(player.getHealth()); // Reduce player's HP
        if (player.getHealth() <= 0) {
            System.out.println("Health:" + player.getHealth());
            navigateToGameOver();
            return;
        }
        updateHpDisplay();
        updateScoreDisplay();
    }

    private void attachWeaponToPlayer(ImageView weaponImageView) {
        collidedWithWeapon = true;
        if (weaponImageView == clubImageView) {
            player.equipWeapon(new Club());
        } else if (weaponImageView == spearImageView) {
            player.equipWeapon(new Spear());
        }
        weaponImageView.setVisibility(View.GONE); // Make the weapon invisible in the room
    }




    private void movePlayer(int dx, int dy, String diff, int sId) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        // Calculate the potential new positions
        float newScreenX = playerSpriteImageView.getTranslationX() + (glideDistance * dx);
        float newScreenY = playerSpriteImageView.getTranslationY() + (glideDistance * dy);

        if (isCollision(playerSpriteImageView, clubImageView)) {
            attachWeaponToPlayer(clubImageView);
        }
        if (isCollision(playerSpriteImageView, spearImageView)) {
            attachWeaponToPlayer(spearImageView);
        }

        // Check the boundaries to keep the sprite within the screen
        if (newScreenX >= 0 && newScreenX <= (screenWidth - playerSpriteImageView.getWidth())
                && newScreenY >= 0 && newScreenY
                <= (screenHeight - playerSpriteImageView.getHeight())) {

            if (isValidMove(newX, newY)) {
                player.setX(newX);
                player.setY(newY);
                if (newY > 160) {
                    Intent intent = new Intent(Room1.this, Room2.class);
                    intent.putExtra("playerName", player.getName());
                    intent.putExtra("difficulty", diff);
                    intent.putExtra("characterSprite", sId);
                    intent.putExtra("score", score);
                    intent.putExtra("HP", player.getHealth());
                    intent.putExtra("speedFactor", player.getSpeedFactor());
                    Log.d("Room1", "Speed Factor: " + player.getSpeedFactor());
                    startActivity(intent);
                }

                playerSpriteImageView.setTranslationX(newScreenX);
                playerSpriteImageView.setTranslationY(newScreenY);
            }
        }

        checkPowerUpCollision();
    }


    private void checkPowerUpCollision() {
        if (isCollision(playerSpriteImageView, firePowerUpImageView)) {
            handlePowerUpCollision(firePowerUpImageView, "fire", fire1);
        }
        if (isCollision(playerSpriteImageView, lightningPowerUpImageView)) {
            handlePowerUpCollision(lightningPowerUpImageView, "lightning", lightning1);
        }
        // Repeat for the second set of power-ups
        if (isCollision(playerSpriteImageView, firePowerUpImageView2)) {
            handlePowerUpCollision(firePowerUpImageView2, "fire", fire2);
        }
        if (isCollision(playerSpriteImageView, lightningPowerUpImageView2)) {
            handlePowerUpCollision(lightningPowerUpImageView2, "lightning", lightning2);
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
        player.setScore(score);
        powerup.usePowerUp(player);
        if (type.equals("fire")) {
            score += 50;
            updateScoreDisplay();
        }
        powerUp.setVisibility(View.GONE);
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
        player.removeObserver(enemy1); // Unregister enemy1
        player.removeObserver(enemy2); // Unregister enemy2
        handler.removeCallbacksAndMessages(null);
    }
}

