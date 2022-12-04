package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton startgamebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startgamebutton = findViewById(R.id.startgamebutton);
        MediaPlayer mainmusic = MediaPlayer.create(MainActivity.this, R.raw.mainmusic);
        mainmusic.start();


        startgamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    private void openGameActivity() {
        Intent myintent = new Intent(this,GameActivity.class);
        startActivity(myintent);
    }
}

