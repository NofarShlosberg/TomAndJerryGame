package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class EndGameActivity extends AppCompatActivity {

    private MaterialButton end_BTN_backtomain;
    private MaterialButton end_BTN_exitgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        end_BTN_backtomain = findViewById(R.id.end_BTN_backtomain);
        end_BTN_exitgame = findViewById(R.id.end_BTN_exitgame);

        end_BTN_backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        end_BTN_exitgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    private void openMainActivity() {
        Intent myintent = new Intent(this,MainActivity.class);
        startActivity(myintent);
    }


}


