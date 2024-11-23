package com.example.x_o;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView freind,ai;
    ImageButton setting;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean applyMode = sp.getBoolean("light",true);
        if(applyMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        freind = findViewById(R.id.Main_play_with_friend);
        ai = findViewById(R.id.Main_play_with_ai);
        setting = findViewById(R.id.Main_setting);


        freind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                Intent intent = new Intent(MainActivity.this,Player_Activity.class);
                startActivity(intent);
            }
        });
        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                Intent intent = new Intent(MainActivity.this,Ai_Activity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                Intent intent = new Intent(MainActivity.this,Setting_Activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onStop(){
        super.onStop();

    }

    @Override
    public void onRestart(){
        super.onRestart();
        LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this));
        recreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}