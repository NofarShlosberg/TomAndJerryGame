package com.example.myapplication5;
import java.util.Random;

public class Tom {

        private int row;
        private final int COL;


        public Tom() {
            Random rand = new Random();
            COL = rand.nextInt(5);
            row = 0;
        }

        public int getRow() {
            return row;
        }

        public int getCOL() {
            return COL;
        }

        public void setRowNextLevel() {
            row++;
        }

    }

