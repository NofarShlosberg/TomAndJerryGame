package com.example.myapplication5;

public class Jerry {

    private int pos;
    private int numOfLife;
    private int gameScore = 0;


    public Jerry() {
        pos = 2;
        numOfLife = 3;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public int getNumOfLife() {
        return numOfLife;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void hitFromTom() {
        --numOfLife;
    }

}

