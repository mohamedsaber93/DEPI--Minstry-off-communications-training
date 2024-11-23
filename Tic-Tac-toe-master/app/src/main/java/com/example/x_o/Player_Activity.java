package com.example.x_o;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.function.Predicate;

public class Player_Activity extends AppCompatActivity {

    EditText name,playerName;
    Button cont;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);

        name = findViewById(R.id.player_name);
        playerName = findViewById(R.id.player_friend);
        cont = findViewById(R.id.player_playing_btn);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myname,fr_name;
                myname = name.getText().toString();
                fr_name = playerName.getText().toString();
                if(myname.isEmpty()||fr_name.isEmpty()){
                    Toast.makeText(Player_Activity.this,"Enter a valid names",Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("myname",myname);
                    editor.putString("opponent",fr_name);
                    editor.apply();
                    Intent intent = new Intent(Player_Activity.this,Pick_Side.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}