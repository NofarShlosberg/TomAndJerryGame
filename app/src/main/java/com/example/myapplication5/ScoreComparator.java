package com.example.myapplication5;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o1.compareTo(o2);
    }
}
