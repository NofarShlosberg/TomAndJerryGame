package com.example.myapplication5;

public class Cheese extends Obstacle{

    private int score = 0;

    public Cheese(int picId) {
        super(picId);
        this.score = 10;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
