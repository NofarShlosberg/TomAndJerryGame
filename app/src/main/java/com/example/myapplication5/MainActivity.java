package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton main_BTN_startgame;
    private ToggleButton main_BTN_level;
    private ToggleButton main_BTN_mode;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        playMusic();
        activateButtons();
        permissionGPS();

    }

    private void playMusic() {
        MediaPlayer mainmusic = MediaPlayer.create(MainActivity.this, R.raw.mainmusic);
        mainmusic.start();
    }

    private void findViews() {
        main_BTN_startgame = findViewById(R.id.main_BTN_startgame);
        main_BTN_level = findViewById(R.id.main_BTN_level);
        main_BTN_mode = findViewById(R.id.main_BTN_mode);
        name = findViewById(R.id.main_LBL_name);

    }

    private void activateButtons() {
        main_BTN_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    private boolean nameExist() {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Name is necessary");
            return false;
        }
        return true;
    }

    private void openGameActivity() {
        if (nameExist()) {
            Intent myintent = new Intent(this, GameActivity.class);
            myintent.putExtra(GameActivity.LEVEL, main_BTN_level.getText());
            myintent.putExtra(GameActivity.NAME, name.getText().toString());
            myintent.putExtra(GameActivity.MODE, main_BTN_mode.getText());
            startActivity(myintent);
        }
    }

    private void permissionGPS(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }
}

