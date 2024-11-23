package com.example.x_o;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash_Activity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        sp = PreferenceManager.getDefaultSharedPreferences(Splash_Activity.this);
        editor = sp.edit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getBoolean("sound",true)){
                    Intent intent = new Intent(Splash_Activity.this, MediaPlayerService.class);
                    startService(intent);
                }
                Intent intent = new Intent(Splash_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}