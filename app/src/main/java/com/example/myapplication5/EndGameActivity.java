package com.example.myapplication5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class EndGameActivity extends AppCompatActivity {

    private MaterialButton end_BTN_backtomain;
    private MaterialButton end_BTN_exitgame;
    private MaterialTextView lbl_game_over;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        end_BTN_backtomain = findViewById(R.id.end_BTN_backtomain);
        end_BTN_exitgame = findViewById(R.id.end_BTN_exitgame);
        lbl_game_over = findViewById(R.id.lbl_game_over);




    }
}
