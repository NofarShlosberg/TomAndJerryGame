package com.example.myapplication5;
import java.util.Random;

public  class Obstacle {

        private int row;
        private final int COL;
        private int picId;

        public Obstacle(){
            Random rand = new Random();
            COL = rand.nextInt(5);
        }

        public Obstacle(int picId) {
            Random rand = new Random();
            COL = rand.nextInt(5);
            row = 0;
            this.picId = picId;
        }

        public Obstacle(int row, int COL, int picId) {
            this.row = row;
            this.COL = COL;
            this.picId = picId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getRow() {
            return row;
        }

        public int getCOL() {
            return COL;
        }

        public void setNextRow() {
            row++;
        }

    }

