package com.example.myapplication5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player>  {
    private CallBackScore callBackScore;

        public PlayerAdapter(Context context, ArrayList<Player> playerArrayList) {
            super(context, 0, playerArrayList);
        }

    public void setCallBackScore(CallBackScore callBackScore) {
        this.callBackScore = callBackScore;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Player player = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_score, parent, false);
            }
            // Lookup view for data population
            TextView LV_name = (TextView) convertView.findViewById(R.id.LV_name);
            TextView LV_score = (TextView) convertView.findViewById(R.id.LV_score);
            // Populate the data into the template view using the data object
            LV_name.setText(player.getName());
            LV_score.setText(String.valueOf(player.getPlayerScore()));
            // Return the completed view to render on screen
            return convertView;
        }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Player player =(Player) parent.getItemAtPosition(position);
//        callBackScore.onPlayerclick(player);
//    }
}

