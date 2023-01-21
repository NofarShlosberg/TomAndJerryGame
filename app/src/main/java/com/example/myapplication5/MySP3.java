package com.example.myapplication5;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MySP3 {
        private static final String DB_FILE = "DB_FILE";

        private static MySP3 instance = null;
        private SharedPreferences preferences;
        private static SharedPreferences.Editor editor;
        private ArrayList<Player> allPlayers ;

        private MySP3(Context context){
            preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
            this.allPlayers = new ArrayList<>();
        }

        public static void init(Context context){
            if (instance == null)
                instance = new MySP3(context);
        }

        public ArrayList<Player> getAllPlayers() {
            String allPlayersObject = preferences.getString(DB_FILE, null);
            if (allPlayersObject != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Player>>() {
                }.getType();
                allPlayers = gson.fromJson(allPlayersObject, type);

            }
            return allPlayers;
        }

        public void setAllPlayers(List<Player> allPlayers) {
            Gson gson = new Gson();
            String json = gson.toJson(allPlayers);
            editor = preferences.edit();
            editor.putString(DB_FILE, json);
            editor.commit();
            //this.allPlayers = allPlayers;
        }

        public static MySP3 getInstance() {
            return instance;
        }

        public void putInt(String key, int value) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }

        public int getInt(String key, int defValue) {
            return preferences.getInt(key, defValue);
        }

        public void putString(String key, String value) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }

        public String getString(String key, String defValue) {
            return preferences.getString(key, defValue);
        }

}
