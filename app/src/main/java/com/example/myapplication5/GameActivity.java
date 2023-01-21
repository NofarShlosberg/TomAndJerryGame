package com.example.myapplication5;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.os.Vibrator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import im.delight.android.location.SimpleLocation;

public class GameActivity extends AppCompatActivity {

    public static final String LEVEL="LEVEL";
    public static final String MODE="MODE";
    public static final String NAME="NAME";
    private static final int OBS_MATRIX_ROW = 7;
    private static final int OBS_MATRIX_COL = 5;
    private static final int DELAY_EASY = 1000;
    private static final int DELAY_HARD = 600;
    private static final String SP_KEY_JERRY = "SP_KEY_JERRY";
    private static final String SP_KEY_OBS = "SP_KEY_OBS";

    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;
    private ShapeableImageView game_IMG_background;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[] game_IMG_jerryPos;
    private ShapeableImageView[][] game_IMG_obsPos;
    private Jerry jerry = new Jerry();
    private ArrayList<Obstacle> allObstacle = new ArrayList<>() ;
    private Thread thread ;
    private Intent lastIntent;
    private TiltDetector tiltDetector;
    private int delayToChoose;
    private SimpleLocation simpleLoc;
    Handler handler = new Handler(Looper.myLooper());
    private Runnable runnable = new Runnable() {
    @Override
    public void run() {
        handler.postDelayed(this,delayToChoose);
        if (jerry.getNumOfLife() == 0) {
            return;
        }
        createObs();
        UpdateUi();
        dropObs();
    }
};
    private static final String TAG = "jerry pos"; // check

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        initViews();
        buttons();
        MySP3.init(this);
        lastIntent = getIntent();
        tiltDetector = new TiltDetector(this, callBack_moves);
        checkLevel();
        checkMode();
        String jerryJson = new Gson().toJson(jerry);
        String obsJson = new Gson().toJson(allObstacle);
        MySP3.getInstance().putString(SP_KEY_JERRY, jerryJson);
        MySP3.getInstance().putString(SP_KEY_OBS, obsJson);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initVisibility();
        allObstacle = new ArrayList<>();
    };

    @Override
    protected void onPause() {
        super.onPause();

        this.thread.interrupt();
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.thread = null ;

        simpleLoc.endUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.thread = new Thread(runnable);
        this.thread.run();
        simpleLoc = initLocation();
        simpleLoc.beginUpdates();

        String jerryAsJsonStringFromSP = MySP3.getInstance().getString(SP_KEY_JERRY,"");
        String obsAsJsonStringFromSP = MySP3.getInstance().getString(SP_KEY_OBS,"");
        Log.d("info", jerryAsJsonStringFromSP);
        Log.d("info", obsAsJsonStringFromSP);
        Jerry jerryFromSP = new Gson().fromJson(jerryAsJsonStringFromSP, Jerry.class);
        Obstacle[] obsFromSP = new Gson().fromJson(obsAsJsonStringFromSP, Obstacle[].class);
        allObstacle = new ArrayList<>();
        for (Obstacle ob :obsFromSP
             ) {
            allObstacle.add(ob);
        }
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

        game_IMG_obsPos = new ShapeableImageView[][] {
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

    private void checkMode(){
        String mode = lastIntent.getExtras().getString(MODE);
        if(mode.equalsIgnoreCase("sensors")){
            game_BTN_left.setVisibility(View.INVISIBLE);
            game_BTN_right.setVisibility(View.INVISIBLE);
            tiltDetector.start();
        }
        else{
            game_BTN_left.setVisibility(View.VISIBLE);
            game_BTN_right.setVisibility(View.VISIBLE);
        }
    }

    private void checkLevel(){
        String mode = lastIntent.getExtras().getString(LEVEL);
        if(mode.equalsIgnoreCase("hard")){
            delayToChoose = DELAY_HARD;
        }
        else{
            delayToChoose = DELAY_EASY;
        }

    }

    private void buttons(){ //Activate Buttons
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

    private TiltDetector.CallBack_moves callBack_moves = new TiltDetector.CallBack_moves() { //Activate Seneors
        @Override
        public void moveLeft() {
            moveJerry("left");
            showRealJerry();
        }

        @Override
        public void moveRight() {
            moveJerry("right");
            showRealJerry();
        }
    };



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

         //set Tom default Visibility
        for (int i = 0; i < OBS_MATRIX_ROW; i++) {
            for (int j = 0; j < OBS_MATRIX_COL; j++) {
                game_IMG_obsPos[i][j].setVisibility(View.INVISIBLE);
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

    private void createObs() {
        for (Obstacle obs :
                allObstacle) {
            obs.setNextRow();
        }
        Random rand = new Random();
        int type= rand.nextInt(2);
        if(type == 0){
            createTom();
        }
        if(type == 1) {
            createCheese();
        }
    }

    private void createCheese() {
        Cheese cheese = new Cheese(R.drawable.ic_cheese_48);
        allObstacle.add(0,cheese);
    }

    private void createTom() {
       Tom tom = new Tom(R.drawable.ic_tom);
       allObstacle.add(0,tom);
    }

    private void dropObs() {
        for (int i = 0; i < allObstacle.size(); i++) {
            if (allObstacle.get(i).getRow() <= OBS_MATRIX_ROW) {
                if (allObstacle.get(i).getRow() == OBS_MATRIX_ROW) { // Private case "the hit"
                    checkHit(allObstacle.get(i));
                    if (jerry.getNumOfLife() == 0)
                        gameOver();     // stop dropping obs because game over
                    allObstacle.remove(i);
                    i--;
                }
            }
        }
    }

    private void checkHit(Obstacle obs) {
        if (obs.getCOL() == jerry.getPos() ) {  // obs catch Jerry
            if (obs instanceof Tom) {
                jerry.hitFromTom();
                toVibrate();
                updateHearts();
            }
            if( obs instanceof Cheese){
                jerry.setGameScore(jerry.getGameScore()+((Cheese) obs).getScore());
            }
        }
    }
    private void updateHearts() {
        game_IMG_hearts[jerry.getNumOfLife()].setVisibility(View.INVISIBLE);
    }

    private void UpdateUi(){

        for(int i= 0; i <OBS_MATRIX_ROW; i++){
            for(int j=0; j < OBS_MATRIX_COL; j++){
                if( allObstacle.size() <= i ){
                    continue;
                }
                boolean temp = allObstacle.get(i).getCOL() == j;
                boolean temp2 = game_IMG_obsPos[i][j].getVisibility() == View.VISIBLE;
                if((temp) && (!temp2)){
                    game_IMG_obsPos[i][j].setVisibility(View.VISIBLE);
                    game_IMG_obsPos[i][j].setImageResource(allObstacle.get(i).getPicId());
                }
                if((!temp) && (temp2)){
                    game_IMG_obsPos[i][j].setVisibility(View.INVISIBLE);
                }

            }
        }
        ((TextView) findViewById(R.id.game_TXT_score)).setText("" + jerry.getGameScore());

    }

    private SimpleLocation initLocation(){
        SimpleLocation location = new SimpleLocation(this);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }
        return location;

    }

    private void gameOver() {
        Intent lastIntent = getIntent();
        String playerName = lastIntent.getStringExtra(NAME);
        Double lan = simpleLoc.getLongitude();
        Double lat = simpleLoc.getLatitude();

        ArrayList<Player> topPlayers = (ArrayList<Player>) MySP3.getInstance().getAllPlayers();
        topPlayers.add(new Player(playerName, jerry.getGameScore(),lan,lat));
        MySP3.getInstance().setAllPlayers(topPlayers);
//        Log.d( "getAllPlayers: ", MySP3.getInstance().getAllPlayers().toString());
        Intent myIntent = new Intent(this,EndGameActivity.class);
        startActivity(myIntent);
        finish();
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



}