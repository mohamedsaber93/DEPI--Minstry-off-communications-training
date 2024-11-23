package com.example.x_o;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Setting_Activity extends AppCompatActivity {

    private RadioButton en, ar, light, dark;
    private ImageView sound_img, us_img, eg_img, sun_img, moon_img,git_img,linked_img,facebook_img,whatsapp_img;
    private Switch sound;
    private Button reset;
    private Intent browserIntent;
    private final String mynumber ="+201226022955";
    private String message = "Hello bro, Tic tac toe";
    TextView contact;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initializeViews();
        setupSharedPreferences();
        setupLanguageSettings();
        setupSoundSettings();
        setupModeSettings();
        setupContact();
        setupReset();
    }

    private void initializeViews() {
        sound = findViewById(R.id.setting_sound_btn);
        en = findViewById(R.id.setting_en);
        ar = findViewById(R.id.setting_ar);
        light = findViewById(R.id.setting_light);
        dark = findViewById(R.id.setting_dark);
        sound_img = findViewById(R.id.setting_sound);
        us_img = findViewById(R.id.US);
        eg_img = findViewById(R.id.EG);
        sun_img = findViewById(R.id.sun);
        moon_img = findViewById(R.id.mon);
        reset = findViewById(R.id.setting_reset_btn);
        git_img = findViewById(R.id.share_github);
        linked_img = findViewById(R.id.share_linkedin);
        facebook_img = findViewById(R.id.share_facebook);
        whatsapp_img = findViewById(R.id.share_whatsapp);
        contact = findViewById(R.id.setting_contact);
    }

    private void setupSharedPreferences() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
    }

    private void setupLanguageSettings() {
        String currentLanguage = sp.getString("lang", "en");
        if (currentLanguage.equals("en")) {
            en.setChecked(true);
        } else {
            ar.setChecked(true);
        }

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("en");
            }
        });

        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage("ar");
            }
        });

        us_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                en.performClick();
            }
        });

        eg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                ar.performClick();
            }
        });
    }

    private void changeLanguage(String languageCode) {
        editor.putString("lang", languageCode);
        editor.apply();
        LocaleHelper.setLocale(Setting_Activity.this, languageCode);
        recreate();
    }

    private void setupSoundSettings() {
        boolean isSoundOn = sp.getBoolean("sound", true);
        sound.setChecked(isSoundOn);
        updateSoundIcon(isSoundOn);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = sound.isChecked();
                editor.putBoolean("sound", state);
                editor.apply();
                updateSoundIcon(state);
                handleSoundService(state);
            }
        });

        sound_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                sound.performClick();
            }
        });
    }

    private void updateSoundIcon(boolean isSoundOn) {
        if (isSoundOn) {
            sound_img.setImageResource(R.drawable.soundon);
        } else {
            sound_img.setImageResource(R.drawable.soundoff);
        }
    }

    private void handleSoundService(boolean startService) {
        Intent intent = new Intent(Setting_Activity.this, MediaPlayerService.class);
        if (startService) {
            startService(intent);
        } else {
            stopService(intent);
        }
    }

    private void setupModeSettings(){
        boolean lightMode = sp.getBoolean("light",true);
        if(lightMode){
            light.setChecked(true);
        }else{
            dark.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("light",true);
                editor.apply();
            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dark.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("light",false);
                editor.apply();
            }
        });

        sun_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                light.performClick();
            }
        });

        moon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                dark.performClick();
            }
        });
    }

    private void setupReset(){

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                editor.putBoolean("sound", true);
                editor.putString("lang", "en");
                editor.putBoolean("light",true);
                editor.apply();
                handleSoundService(true);
                changeLanguage("en");
                finish();
            }
        });
    }

    private  void setupContact(){
        contact.setText(R.string.contact);
        git_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Abdallah-Elsobky/Tic-Tac-toe"));
                startActivity(browserIntent);
            }
        });
        linked_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/abdallah-elsobky-5150701a6"));
                startActivity(browserIntent);
            }
        });
        facebook_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click.animateClick(view);

                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100014756283139"));
                startActivity(browserIntent);
            }
        });
        whatsapp_img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                Click.animateClick(view);
                try {
                    browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+mynumber+"&text="+Uri.encode(message)));
                    startActivity(browserIntent);
                }catch (Exception e){
                    Toast.makeText(Setting_Activity.this,"WhatsApp is not installed on this device",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
