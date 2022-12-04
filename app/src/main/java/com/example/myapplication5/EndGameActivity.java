package com.example.myapplication5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class EndGameActivity extends AppCompatActivity {

    private MaterialButton end_BTN_backtomain;
    private MaterialButton end_BTN_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        end_BTN_backtomain = findViewById(R.id.end_BTN_backtomain);
        end_BTN_restart = findViewById(R.id.end_BTN_restart);

        end_BTN_backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        end_BTN_restart.setOnClickListener(new View.OnClickListener() {
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

    private void openMainActivity() {
        Intent myintent = new Intent(this,MainActivity.class);
        startActivity(myintent);
    }


}


