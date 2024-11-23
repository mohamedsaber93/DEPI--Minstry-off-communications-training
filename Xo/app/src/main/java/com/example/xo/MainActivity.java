package com.example.xo;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button reset, playagain;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
    };
    private int rounds;
    private int playerOneScoreCount, playerTwoScoreCount;
    private boolean playerOneActive;
    private boolean isPvC;
    private String difficultyLevel;

    private RadioGroup radioGroupGameMode, musicControlGroup;
    private RadioButton radioPvP, radioPvC, radioPlayMusic, radioStopMusic;
    private Spinner spinnerDifficulty;
    private LinearLayout linearLayoutDifficulty;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MediaPlayer for background music
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Initialize UI components
        playerOneScore = findViewById(R.id.score_Player1);
        playerTwoScore = findViewById(R.id.score_Player2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playagain = findViewById(R.id.btn_play_again);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }

        // Initialize variables
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        playerOneActive = true;
        rounds = 0;

        // Initialize game mode selection
        radioGroupGameMode = findViewById(R.id.radioGroupGameMode);
        radioPvP = findViewById(R.id.radioPvP);
        radioPvC = findViewById(R.id.radioPvC);
        linearLayoutDifficulty = findViewById(R.id.linearLayoutDifficulty);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);

        radioGroupGameMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioPvC) {
                    isPvC = true;
                    linearLayoutDifficulty.setVisibility(View.VISIBLE);
                } else {
                    isPvC = false;
                    linearLayoutDifficulty.setVisibility(View.GONE);
                }
            }
        });

        spinnerDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultyLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                difficultyLevel = "Easy"; // Default value
            }
        });

        // Music Control RadioGroup
        musicControlGroup = findViewById(R.id.radioGroupMusicControl);
        radioPlayMusic = findViewById(R.id.radioPlayMusic);
        radioStopMusic = findViewById(R.id.radioStopMusic);

        musicControlGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioStopMusic) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                } else if (checkedId == R.id.radioPlayMusic) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                }
            }
        });

        // Reset and play again button listeners
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                updatePlayerScore();
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        } else if (checkWinner().length() > 0) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));

        if (isPvC) {
            playerMove(gameStatePointer, "X");
            if (!checkWinner().equals("Player-1") && rounds < 9) {
                computerMove();
            }
        } else {
            playerMove(gameStatePointer, playerOneActive ? "X" : "O");
        }

        String winner = checkWinner();
        if (winner.equals("Player-1")) {
            playerOneScoreCount++;
            updatePlayerScore();
            playerStatus.setText("Player-1 has won");
        } else if (winner.equals("Player-2")) {
            playerTwoScoreCount++;
            updatePlayerScore();
            playerStatus.setText("Player-2 has won");
        } else if (winner.equals("Computer")) {
            playerStatus.setText("Computer wins!");
        } else if (rounds == 9) {
            playerStatus.setText("No Winner");
        } else {
            playerOneActive = !playerOneActive;
        }

        rounds++;
    }

    private void playerMove(int gameStatePointer, String playerSymbol) {
        buttons[gameStatePointer].setText(playerSymbol);
        buttons[gameStatePointer].setTextColor(playerSymbol.equals("X") ? Color.parseColor("#ffc34a") : Color.parseColor("#70fc3a"));
        gameState[gameStatePointer] = playerSymbol.equals("X") ? 0 : 1;
    }

    private void computerMove() {
        int move = -1;

        if ("Easy".equals(difficultyLevel)) {
            move = getRandomMove();
        } else if ("Medium".equals(difficultyLevel)) {
            move = getBlockingMove();
            if (move == -1) {
                move = getRandomMove();
            }
        } else if ("Hard".equals(difficultyLevel)) {
            move = getWinningMove();
            if (move == -1) {
                move = getBlockingMove();
            }
            if (move == -1) {
                move = getRandomMove();
            }
        }

        if (move != -1) {
            Button selectedButton = buttons[move];
            selectedButton.setText("O");
            selectedButton.setTextColor(Color.parseColor("#70fc3a"));
            gameState[move] = 1;
            rounds++;
        }
    }

    private int getRandomMove() {
        ArrayList<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == 2) {
                availableMoves.add(i);
            }
        }

        if (!availableMoves.isEmpty()) {
            Random random = new Random();
            return availableMoves.get(random.nextInt(availableMoves.size()));
        }
        return -1;
    }

    private int getBlockingMove() {
        for (int[] winPosition : winningPositions) {
            if (gameState[winPosition[0]] == 0 && gameState[winPosition[1]] == 0 && gameState[winPosition[2]] == 2) {
                return winPosition[2];
            } else if (gameState[winPosition[1]] == 0 && gameState[winPosition[2]] == 0 && gameState[winPosition[0]] == 2) {
                return winPosition[0];
            } else if (gameState[winPosition[0]] == 0 && gameState[winPosition[2]] == 0 && gameState[winPosition[1]] == 2) {
                return winPosition[1];
            }
        }
        return -1;
    }

    private int getWinningMove() {
        for (int[] winPosition : winningPositions) {
            if (gameState[winPosition[0]] == 1 && gameState[winPosition[1]] == 1 && gameState[winPosition[2]] == 2) {
                return winPosition[2];
            } else if (gameState[winPosition[1]] == 1 && gameState[winPosition[2]] == 1 && gameState[winPosition[0]] == 2) {
                return winPosition[0];
            } else if (gameState[winPosition[0]] == 1 && gameState[winPosition[2]] == 1 && gameState[winPosition[1]] == 2) {
                return winPosition[1];
            }
        }
        return -1;
    }

    private String checkWinner() {
        for (int[] winPosition : winningPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                if (gameState[winPosition[0]] == 0) {
                    return "Player-1";
                } else {
                    return isPvC ? "Computer" : "Player-2";
                }
            }
        }
        return "";
    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    private void playAgain() {
        rounds = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerStatus.setText("Game On");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
