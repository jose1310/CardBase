package com.fba.cardboard;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Comparator;

public class CardComparator implements Comparator<Actor> {
    @Override
    public int compare(Actor o1, Actor o2) {
        if (o1.getZIndex() < o2.getZIndex()) {
            return -1;
        } else if (o1.getZIndex() == o2.getZIndex()) {
            return 0;
        } else {
            return 1;
        }
    }
}
