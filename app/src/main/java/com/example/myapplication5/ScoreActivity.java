package com.example.myapplication5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ArrayList<Player> players = new ArrayList <>();
    private ScoreFragment scoreFragment;
    private MapFragment mapFragment;
    CalBackMaps calBackMaps = new CalBackMaps() {
        @Override
        public void Zoom(double lat, double lan) {
            showPlayeLoc(lat , lan);
        }
    };

    private void showPlayeLoc(double lat , double lan) {
        mapFragment.zoom(lat, lan);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // new
        setContentView(R.layout.activity_score);
        scoreFragment = new ScoreFragment();
        mapFragment = new MapFragment();
        scoreFragment.setCalBackMaps(calBackMaps);
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_topTen , scoreFragment).commit();
        getSupportFragmentManager()
                .beginTransaction().add(R.id.score_FRAME_map , mapFragment).commit();
    }




}
