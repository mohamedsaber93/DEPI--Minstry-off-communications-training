package com.example.x_o;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ai_Activity extends AppCompatActivity {

    ConstraintLayout level1,level2,level3;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ai);

        level1 = findViewById(R.id.ai_level1);
        level2 = findViewById(R.id.ai_level2);
        level3 = findViewById(R.id.ai_level3);
        selectLevel(level1,1);
        selectLevel(level2,2);
        selectLevel(level3,3);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

    }
    private void selectLevel(ConstraintLayout level,int id){
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                editor.putInt("level",id);
                editor.putString("myname",getString(R.string.you));
                editor.putString("opponent","Ai-level"+id);
                editor.apply();
                Intent intent = new Intent(Ai_Activity.this,Pick_Side.class);
                startActivity(intent);
                finish();
            }
        });
    }
}