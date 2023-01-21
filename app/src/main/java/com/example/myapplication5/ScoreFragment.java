package com.example.myapplication5;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.callback.Callback;


public class ScoreFragment extends Fragment {
    
    private ListView score_List_TopTen;
    private PlayerAdapter adapter;
    ArrayList<Player> TopTen;
    private  CalBackMaps calBackMaps;

    public ScoreFragment() {
    }

    public void setCalBackMaps(CalBackMaps calBackMaps) {
        this.calBackMaps = calBackMaps;
    }

    public void findViews(View view){
        score_List_TopTen = view.findViewById(R.id.score_List_TopTen);
    }
    public PlayerAdapter getAdapter() {
        return adapter;
    }

    public ListView getScore_List_TopTen() {
        return score_List_TopTen;
    }

    public ArrayList<Player> getTopTen() {
        return TopTen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_score, container, false);
        findViews(view);
        MySP3.init(getContext());
        setScoreFromJson(view);
        return view;
    }


    public void setScoreFromJson(View v){

        TopTen = MySP3.getInstance().getAllPlayers();
        Collections.sort(TopTen , new ScoreComparator());
        TopTen = (ArrayList<Player>) TopTen.stream().limit(10).collect(Collectors.toList());
        adapter = new PlayerAdapter(getContext(), TopTen);
        Log.d("TopTen" ,TopTen.toString());
// Create the adapter to convert the array to views

// Attach the adapter to a ListView
        score_List_TopTen = (ListView) v.findViewById(R.id.score_List_TopTen);
        score_List_TopTen.setAdapter(adapter);
        score_List_TopTen.setOnItemClickListener((parent, view, position, id) ->{
            Player player = (Player) parent.getItemAtPosition(position);
            calBackMaps.Zoom(player.getLat(), player.getLan());
        });
    }
}