package com.fba.cardboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Stack {

    private Group group;
    private Rectangle bounds;
    private Image baseImage;
    private ArrayList<Card> stack;
    private Actor actor;
    private Stage stage;
    private boolean endStack = false;

    //This fields will manage when a Stack store the cards like a dropdown
    private boolean waterfall = false;
    private Vector2 waterfallEnd;
    private int waterfallStep=20;

    //TODO:Change to Build Pattern to use different parameters and values
    public Stack(Stage stage) {
        baseImage = null;
        stack = new ArrayList<Card>();
        bounds = new Rectangle(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        group = new Group();
        this.stage = stage;
        actor = new Actor();
        this.stage.addActor(group);
    }

    public Stack(String filename, Stage stage) {
        this(filename, stage, false);
    }

    public Stack(String filename, Stage stage, boolean endStack) {
        this.stage = stage;
        baseImage = new Image(new Texture(filename));
        stack = new ArrayList<Card>();
        actor = baseImage;
        actor.setPosition(500, 300);
        bounds = new Rectangle(
                actor.getX(),
                actor.getY(),
                actor.getWidth(),
                actor.getHeight());
        //Bottom image of the stack
        this.stage.addActor(actor);
        group = new Group();
        this.stage.addActor(group);
        this.endStack = endStack;
    }

    public void setPosition(float x, float y) {
        actor.setPosition(x, y);
        bounds = new Rectangle(
                actor.getX(),
                actor.getY(),
                actor.getWidth(),
                actor.getHeight());
    }

    public void addCard(Card card) {
        if (!stack.contains(card)) {
            if (endStack) {
                card.actor.setPosition(actor.getX(), actor.getY());
            }
            if(waterfall){
                if(waterfallEnd==null){
                    waterfallEnd=new Vector2(actor.getX(),actor.getY());
                }
                waterfallEnd.y-=waterfallStep;
                card.actor.setPosition(waterfallEnd.x,waterfallEnd.y);
            }
            stage.addActor(card.actor);
            group.addActor(card);

            stack.add(card);
        } else {
            System.out.println("The card was here");
            group.findActor(card.getName()).toFront();

        }
    }

    public void dropCard(Card card) {
        if (stack.contains(card)) {
            stack.remove(card);
            card.actor.remove();
            if(waterfall){
                if(waterfallEnd==null){
                    waterfallEnd=new Vector2(actor.getX(),actor.getY());
                }
                if(waterfallEnd.y>actor.getY()) waterfallEnd.y+=waterfallStep;
            }
            sortStack();
            System.out.println("Cards in this deck dropping:");
            for (int i = 0; i < group.getChildren().size; i++) {
                System.out.println(group.getChildren().get(i).getName() + " " + group.getChildren().get(i).getZIndex());
            }
        }
    }

    public void reverseStack() {
        Collections.reverse(stack);
    }

    public void sortStack() {
        Collections.sort(Arrays.asList(group.getChildren().toArray()), new CardComparator());
    }

    public ArrayList<Card> getCards() {
        //TODO:Try to fix this
        ArrayList<Card> temp = new ArrayList<Card>(stack);
        Collections.reverse(temp);
        return temp;
    }

    public Card pickTop() {
        if (!stack.isEmpty()) {
            stack.get(0).actor.remove();
            return stack.remove(0);
        }
        return null;
    }

    public boolean touchStack(Vector2 pos) {
        return bounds.contains(pos);
    }

    public boolean touchStack(Rectangle pos) {
        return bounds.overlaps(pos);
    }

    public void setWaterfall(boolean waterfall){
        this.waterfall=waterfall;
    }
}
