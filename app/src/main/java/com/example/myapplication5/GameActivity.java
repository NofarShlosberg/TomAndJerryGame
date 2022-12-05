package com.example.myapplication5;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity{

    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;
    private ShapeableImageView[] game_IMG_hearts;
    Jerry jerry = new Jerry();
    private static final String TAG ="jerry pos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);

        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveJerry("left");
            }
        });
        game_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               moveJerry("right");
            }

        });
    }

    public void moveJerry(String direction) {
        int currentPos = jerry.getPos();
        if (direction == "right") {
            if (currentPos != 4) {
                jerry.setPos(currentPos + 1);
            }
            Log.d(TAG, "jerry pos is " + jerry.getPos());
        }
        if (direction == "left") {
            if (currentPos != 0) {
                jerry.setPos(currentPos - 1);
            }
            Log.d(TAG, "jerry pos is " + jerry.getPos());
        }
    }


}
