package com.example.myapplication5;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private static final int TOM_MATRIX_ROW = 7;
    private static final int TOM_MATRIX_COL = 5;
    private static final long DELAY = 1000;
    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;
    private ShapeableImageView game_IMG_background;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[] game_IMG_jerryPos;
    private ShapeableImageView[][] game_IMG_tomPos;
    private Jerry jerry;
    private ArrayList<Tom> allToms;
    private Timer timer;
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                            createTom();
                            dropTom();
                    }
                });
            }
        }, DELAY, DELAY);
    }


    private static final String TAG = "jerry pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        initViews();

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
                showRealJerry();
            }

        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
        initVisibility();
        jerry = new Jerry();
        allToms = new ArrayList<Tom>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void findViews() {
        game_IMG_background = findViewById(R.id.game_background);
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);

        game_IMG_hearts = new ShapeableImageView[] {
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_jerryPos = new ShapeableImageView[] {
                findViewById(R.id.game_IMG_jerryPos0),
                findViewById(R.id.game_IMG_jerryPos1),
                findViewById(R.id.game_IMG_jerryPos2),
                findViewById(R.id.game_IMG_jerryPos3),
                findViewById(R.id.game_IMG_jerryPos4)
        };

        game_IMG_tomPos = new ShapeableImageView[][] {
                { findViewById(R.id.game_IMG_TomPos00), findViewById(R.id.game_IMG_TomPos01),
                        findViewById(R.id.game_IMG_TomPos02), findViewById(R.id.game_IMG_TomPos03),
                        findViewById(R.id.game_IMG_TomPos04) },
                { findViewById(R.id.game_IMG_TomPos10), findViewById(R.id.game_IMG_TomPos11),
                        findViewById(R.id.game_IMG_TomPos12), findViewById(R.id.game_IMG_TomPos13),
                        findViewById(R.id.game_IMG_TomPos14) },
                { findViewById(R.id.game_IMG_TomPos20), findViewById(R.id.game_IMG_TomPos21),
                        findViewById(R.id.game_IMG_TomPos22), findViewById(R.id.game_IMG_TomPos23),
                        findViewById(R.id.game_IMG_TomPos24) },
                { findViewById(R.id.game_IMG_TomPos30), findViewById(R.id.game_IMG_TomPos31),
                        findViewById(R.id.game_IMG_TomPos32), findViewById(R.id.game_IMG_TomPos33),
                        findViewById(R.id.game_IMG_TomPos34) },
                { findViewById(R.id.game_IMG_TomPos40), findViewById(R.id.game_IMG_TomPos41),
                        findViewById(R.id.game_IMG_TomPos42), findViewById(R.id.game_IMG_TomPos43),
                        findViewById(R.id.game_IMG_TomPos44) },
                { findViewById(R.id.game_IMG_TomPos50), findViewById(R.id.game_IMG_TomPos51),
                        findViewById(R.id.game_IMG_TomPos52), findViewById(R.id.game_IMG_TomPos53),
                        findViewById(R.id.game_IMG_TomPos54) },
                { findViewById(R.id.game_IMG_TomPos60), findViewById(R.id.game_IMG_TomPos61),
                        findViewById(R.id.game_IMG_TomPos62), findViewById(R.id.game_IMG_TomPos63),
                        findViewById(R.id.game_IMG_TomPos64) }

        };

    }

    private void initViews() {

        // set life IMG
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            Glide
                    .with(this)
                    .load(R.drawable.ic_heart)
                    .into(game_IMG_hearts[i]);
        }

        // set Jerry IMG
        for (int i = 0; i < game_IMG_jerryPos.length; i++) {
            Glide
                    .with(this)
                    .load(R.drawable.ic_jerry)
                    .centerInside()
                    .into(game_IMG_jerryPos[i]);
        }

    }

    private void initVisibility() {
        // set Jerry default Visibility
        game_IMG_jerryPos[0].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[1].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[2].setVisibility(View.VISIBLE);
        game_IMG_jerryPos[3].setVisibility(View.INVISIBLE);
        game_IMG_jerryPos[4].setVisibility(View.INVISIBLE);

        // set hearts default Visibility
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

        // set Tom default Visibility
        for (int i = 0; i < TOM_MATRIX_ROW; i++) {
            for (int j = 0; j < TOM_MATRIX_COL; j++) {
                game_IMG_tomPos[i][j].setVisibility(View.INVISIBLE);
            }
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
        int currentPos = jerry.getPos();
        for (int i = 0; i < game_IMG_jerryPos.length; i++) {
            if (currentPos == i) {
                game_IMG_jerryPos[i].setVisibility(View.VISIBLE);
            } else {
                game_IMG_jerryPos[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void createTom() {
        Tom tom = new Tom();
        allToms.add(tom);
    }

    private void dropTom() {
        for (int i = 0; i < allToms.size(); i++) {
            allToms.get(i).setNextRow();
            if (allToms.get(i).getRow() <= TOM_MATRIX_ROW) {
                ShowRealTom(allToms.get(i));
                if (allToms.get(i).getRow() == TOM_MATRIX_ROW) { // Private case "the hit"
                    checkHit(allToms.get(i));
                    if (jerry.getNumOfLife() == 0)
                        gameOver();     // stop dropping tom because game over
                    allToms.remove(i);
                    i--;
                }
            }
        }
    }

    private void ShowRealTom(Tom thisTom) {
        int rowTom = thisTom.getRow();
        int colTom = thisTom.getCOL();
        if (rowTom != 0)
            game_IMG_tomPos[rowTom-1][colTom].setVisibility(View.INVISIBLE);

        if (rowTom < TOM_MATRIX_ROW) {
            game_IMG_tomPos[rowTom][colTom].setVisibility(View.VISIBLE);
            game_IMG_tomPos[rowTom][colTom].setImageResource(R.drawable.ic_tom);
        }

    }

    private void checkHit(Tom tom) {

        if (tom.getCOL() == jerry.getPos() ) {  // tom catch Jerry
                    jerry.hitFromTom();
                    toVibrate();
                    updateHearts();
            }
    }

    private void toVibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void updateHearts() {
        game_IMG_hearts[jerry.getNumOfLife()].setVisibility(View.INVISIBLE);
    }
    private void gameOver() {
        Intent myintent = new Intent(this,EndGameActivity.class);
        startActivity(myintent);
        finish();
    }

}