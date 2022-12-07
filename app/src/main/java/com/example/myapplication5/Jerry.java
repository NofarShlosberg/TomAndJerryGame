package com.example.myapplication5;

public class Jerry {

    private int pos;
    private int numOfLife;


    public Jerry() {
        pos = 2;
        numOfLife = 3;
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

