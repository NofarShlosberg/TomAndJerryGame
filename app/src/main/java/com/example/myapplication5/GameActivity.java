package com.example.myapplication5;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity {

    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[]  game_IMG_jerryPos;
    Jerry jerry = new Jerry();
    private static final String TAG = "jerry pos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        initViews();
        initVisibility();

        game_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveJerry("left");
                showRealJerry();
            }
        });
        game_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveJerry("right");
            }

        });
    }

    private void findViews() {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);

        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_jerryPos = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_jerryPos0),
                findViewById(R.id.game_IMG_jerryPos1),
                findViewById(R.id.game_IMG_jerryPos2),
                findViewById(R.id.game_IMG_jerryPos3),
                findViewById(R.id.game_IMG_jerryPos4)
        };

    }
    private void initViews() {

        //set life IMG
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            Glide
                    .with(this)
                    .load(R.drawable.ic_heart)
                    .into(game_IMG_hearts[i]);
        }


        //set Jerry IMG
        for (int i = 0; i < game_IMG_jerryPos.length; i++) {
            Glide
                    .with(this)
                    .load(R.drawable.ic_jerry)
                    .centerInside()
                    .into(game_IMG_jerryPos[i]);
        }

    }

    private void initVisibility() {
        //set Jerry default Visibility
        game_IMG_jerryPos[0].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[1].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[2].setVisibility(View.VISIBLE);
        game_IMG_jerryPos[3].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[4].setVisibility(View.INVISIBLE);

        //set hearts default Visibility
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

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

    private void showRealJerry() {

    }

}