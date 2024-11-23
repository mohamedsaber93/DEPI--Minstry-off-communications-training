package com.example.x_o;


import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class Pick_Side extends AppCompatActivity {

    RadioButton x,o;
    ImageView img_x,img_o;
    Button play;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pick_side);

        x = findViewById(R.id.pick_x);
        o = findViewById(R.id.pick_o);
        play = findViewById(R.id.pick_btn_continue);
        img_x = findViewById(R.id.pick_img_x);
        img_o = findViewById(R.id.pick_img_o);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        editor.putString("player","x");
        editor.putInt("score1",0);
        editor.putInt("score2",0);
        editor.apply();

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("player","x");
                editor.apply();
            }
        });

        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("player","o");
                editor.apply();
            }
        });

        img_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                x.performClick();
            }
        });

        img_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                o.performClick();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                Intent intent = new Intent(Pick_Side.this,Game_Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}