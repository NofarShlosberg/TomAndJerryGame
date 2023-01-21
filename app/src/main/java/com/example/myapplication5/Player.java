package com.example.myapplication5;

import java.util.Comparator;

import im.delight.android.location.SimpleLocation;

public class Player implements Comparable<Player> {
    private String name;
    private int playerScore;
    private double lan;
    private double lat;

    public Player(String name, int playerScore,double lan , double lat) {
        this.name = name;
        this.playerScore = playerScore;
        this.lan = lan;
        this.lat = lat;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public int compareTo(Player o) {
        if(this.playerScore > o.playerScore){
            return -1;
        }
        else if(this.playerScore < o.playerScore){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", playerScore=" + playerScore +
                ", lan=" + lan +
                ", lat=" + lat +
                '}';
    }


}
