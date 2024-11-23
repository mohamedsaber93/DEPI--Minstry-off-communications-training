package com.example.x_o;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game_Activity extends AppCompatActivity {

    private ImageView sound, mode, me, opp, opp_img,game_reset;
    private TextView myname, opponent, winner,player1_score,player2_score;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Intent mediaServiceIntent;
    private char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    private final int[] button_cells = {
            R.id.board_cell1, R.id.board_cell2, R.id.board_cell3, R.id.board_cell4,
            R.id.board_cell5, R.id.board_cell6, R.id.board_cell7, R.id.board_cell8,
            R.id.board_cell9
    };
    private ImageButton cell;
    private int move = 0;
    private Ai_Move Ai;
    private char player1, player2;
    private int score1=0,score2=0;
    private int level;
    private Ai_Move check;
    private boolean win = false;
    private String won_str;
    private MediaPlayer win_sound,lose_sound,draw_sound;
    View player1_shadow,player2_shadow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        initViews();
        initPreferences();
        updateActivity();

        sound.setOnClickListener(view -> {
            Click.animateClick(view);
            boolean soundState = !sp.getBoolean("sound", true);
            editor.putBoolean("sound", soundState).apply();
            updateActivity();
        });

        mode.setOnClickListener(view -> {
            Click.animateClick(view);
            boolean lightState = !sp.getBoolean("light", true);
            editor.putBoolean("light", lightState).apply();
            updateActivity();
        });

        game_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                reset_game();
            }
        });
    }

    private void initViews() {
        sound = findViewById(R.id.game_sound_btn);
        mode = findViewById(R.id.game_mode_btn);
        myname = findViewById(R.id.game_myname);
        opponent = findViewById(R.id.game_opponent);
        player1_score = findViewById(R.id.game_score1);
        player2_score = findViewById(R.id.game_score2);
        winner = findViewById(R.id.game_winner);
        me = findViewById(R.id.game_x);
        opp = findViewById(R.id.game_o);
        opp_img = findViewById(R.id.game_opp);
        game_reset = findViewById(R.id.game_reset);
        check = new Ai_Move(board, player2, player1);
        won_str = getString(R.string.won);
        win_sound = MediaPlayer.create(Game_Activity.this,R.raw.win);
        lose_sound = MediaPlayer.create(Game_Activity.this,R.raw.lose);
        draw_sound = MediaPlayer.create(Game_Activity.this,R.raw.draw);
        player1_shadow = findViewById(R.id.player1_shadow);
        player2_shadow = findViewById(R.id.player2_shadow);
    }

    private void initPreferences() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        mediaServiceIntent = new Intent(Game_Activity.this, MediaPlayerService.class);
    }

    private void updateActivity() {
        boolean soundOn = sp.getBoolean("sound", true);
        boolean lightOn = sp.getBoolean("light", true);
        String opponentName = sp.getString("opponent", "");
        score1 = sp.getInt("score1",0);
        score2 = sp.getInt("score2",0);
        player1_score.setText(String.valueOf(score1));
        player2_score.setText(String.valueOf(score2));
        myname.setText(sp.getString("myname", ""));
        opponent.setText(opponentName);

        if (soundOn) {
            sound.setImageResource(R.drawable.soundon);
            startService(mediaServiceIntent);
        } else {
            sound.setImageResource(R.drawable.soundoff);
            stopService(mediaServiceIntent);
        }

        if (lightOn) {
            mode.setImageResource(R.drawable.daymode);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            mode.setImageResource(R.drawable.darkmode);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        setupPlayers();
        setupOpponent(opponentName);
    }

    private void setupPlayers() {
        if (sp.getString("player", "").equals("x")) {
            me.setImageResource(R.drawable.cancel);
            opp.setImageResource(R.drawable.lettero);
            player1 = 'x';
            player2 = 'o';
        } else {
            opp.setImageResource(R.drawable.cancel);
            me.setImageResource(R.drawable.lettero);
            player1 = 'o';
            player2 = 'x';
        }
    }

    private void setupOpponent(String opponentName) {
        if (opponentName.startsWith("Ai")) {
            Ai = new Ai_Move(board, player2, player1);
            char levelChar = opponentName.charAt(opponentName.length() - 1);
            level = Character.getNumericValue(levelChar);
            opp_img.setImageResource(getAiImageResource(level));
            playWithAi();
        } else {
            opp_img.setImageResource(R.drawable.boy);
            playWithFriend();
        }
    }

    private int getAiImageResource(int level) {
        switch (level) {
            case 1:
                return R.drawable.robot1;
            case 2:
                return R.drawable.robot2;
            case 3:
                return R.drawable.robot3;
            default:
                return R.drawable.ai;
        }
    }

    private void playerPlay(char xo) {
        clickCell(xo);
        display_winner();
    }

    private void clickCell(char xo) {
        for (Integer i : button_cells ) {
            cell = findViewById(i);
            cell.setOnClickListener(view -> {
                String idName = getResources().getResourceEntryName(i);
                int cellIndex = Integer.parseInt(idName.substring(idName.length() - 1)) - 1;
                Click.animateClick(view);
                if (board[cellIndex] == ' ') {
                    board[cellIndex] = xo;
                    updateBoard();
                    move++;
                    if (xo == player1 && Ai != null) {
                        player2_shadow.setBackgroundResource(R.drawable.none_bk);
                        player1_shadow.setBackgroundResource(R.drawable.player_shadow);
                        playWithAi();
                    } else {
                        player1_shadow.setBackgroundResource(R.drawable.none_bk);
                        player2_shadow.setBackgroundResource(R.drawable.player_shadow);
                        playWithFriend();
                    }
                }else{
                    Toast.makeText(Game_Activity.this,"This button already played played",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void aiCell(int index) {
        board[index] = player2;
    }

    private void playWithAi() {
        if (move < 9) {
            if (move % 2 == 0 && !win) {
                player2_shadow.setBackgroundResource(R.drawable.player_shadow);
                player1_shadow.setBackgroundResource(R.drawable.none_bk);
                playerPlay(player1);
            } else if(!win){
                player2_shadow.setBackgroundResource(R.drawable.none_bk);
                player1_shadow.setBackgroundResource(R.drawable.player_shadow);
                setCells_state(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aiCell(Ai.getAIMove(String.valueOf(level), board));
                        move++;
                        setCells_state(true);
                        updateBoard();
                        playWithAi();
                    }
                },1500);
            }
        }
        display_winner();
    }

    private void playWithFriend() {
        if (move < 9) {
            if (move % 2 == 0) {
                player1_shadow.setBackgroundResource(R.drawable.none_bk);
                player2_shadow.setBackgroundResource(R.drawable.player_shadow);
                playerPlay(player1);
            } else {
                player1_shadow.setBackgroundResource(R.drawable.player_shadow);
                player2_shadow.setBackgroundResource(R.drawable.none_bk);
                playerPlay(player2);
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < 9; i++) {
            ImageButton im = findViewById(button_cells[i]);
            if (board[i] == 'x') {
                im.setImageResource(R.drawable.cancel);
            } else if (board[i] == 'o') {
                im.setImageResource(R.drawable.lettero);
            }else if(board[i] == '-'){
                im.setImageResource(R.drawable.dash);
            }
            else {
                im.setImageResource(R.drawable.none_bk);
            }
                im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if(check.checkWin(player1) && !win){
            win = true;
            winner.setText(myname.getText()+" "+won_str);

            score1++;
            player1_score.setText(String.valueOf(score1));
            win_sound.start();
            setCells_state(false);
        }else if(check.checkWin(player2) && !win){
            win = true;
            winner.setText(opponent.getText()+" "+won_str);
            score2++;
            player2_score.setText(String.valueOf(score2));
            if(opponent.getText().toString().startsWith("Ai")){
                lose_sound.start();
            }else{
                win_sound.start();
            }
            setCells_state(false);
        }
        editor.putInt("score1",score1);
        editor.putInt("score2",score2);
        editor.apply();
        if(check.isBoardFull(board)&&!win){
            winner.setText(R.string.draw);
            setCells_state(false);
            draw_sound.start();
        }
    }

    private void setCells_state(boolean state){
        ImageButton btn;
        for(int i : button_cells){
            btn = findViewById(i);
            btn.setEnabled(state);
        }
    }

    private void reset_game(){
        player2_shadow.setBackgroundResource(R.drawable.player_shadow);
        player1_shadow.setBackgroundResource(R.drawable.none_bk);
        setCells_state(true);
        for(int i=0;i<9;i++){
            board[i]=' ';
        }
        win = false;
        winner.setText("");
        move = 0;
        updateBoard();
    }

    private void display_winner(){
        if(check.checkWin(player1)){
            player1_shadow.setBackgroundResource(R.drawable.none_bk);
            player2_shadow.setBackgroundResource(R.drawable.player_shadow);
        }else if(check.checkWin(player2)){
            player1_shadow.setBackgroundResource(R.drawable.player_shadow);
            player2_shadow.setBackgroundResource(R.drawable.none_bk);
        }else if(check.isBoardFull(board)){
            player1_shadow.setBackgroundResource(R.drawable.none_bk);
            player2_shadow.setBackgroundResource(R.drawable.none_bk);
        }
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        updateActivity();
    }
}
