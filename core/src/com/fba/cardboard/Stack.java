package com.fba.cardboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Stack {

    private HorizontalGroup group;
    private Rectangle bounds;
    private Image baseImage;
    private ArrayList stack;
    private Actor actor;
    private boolean temporal=true;
    private Stage stage;

    public Stack(Stage stage){
        baseImage = null;
        stack = new ArrayList<Card>();
        bounds = new Rectangle(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        group=new HorizontalGroup();
        this.stage=stage;
        this.stage.addActor(group);
    }

    public Stack(String filename, Stage stage){
        this.stage=stage;
        baseImage = new Image(new Texture(filename));
        stack = new ArrayList<Card>();
        actor = baseImage;
        actor.setPosition(500, 300);
        bounds = new Rectangle(
                actor.getX(),
                actor.getY(),
                actor.getWidth(),
                actor.getHeight());
        //Este es el fondo del stack
        this.stage.addActor(actor);
        group=new HorizontalGroup();
        this.stage.addActor(group);
    }

    public void setPosition(float x,float y){
        actor.setPosition(x,y);
        bounds = new Rectangle(
                actor.getX(),
                actor.getY(),
                actor.getWidth(),
                actor.getHeight());
    }

    public void addCard(Card card){
        if(!stack.contains(card)) {
            card.setZIndex(Constants.getInstance().getZ());


            group.addActor(card);

            stack.add(card);
        }
        else{
            System.out.println("La carta ya estaba aqui");
            group.findActor(card.getName()).toFront();

        }
    }

    public void dropCard(Card card){
        if(stack.contains(card)) {
            stack.remove(card);
            group.removeActor(card);
            //group.toFront();
            sortStack();
            System.out.println("Cartas en este deck Soltando:");
            for (int i = 0; i < group.getChildren().size; i++) {
                System.out.println(group.getChildren().get(i).getName() +" "+group.getChildren().get(i).getZIndex());
            }
        }
    }

    public void reverseStack(){
        Collections.reverse(stack);
    }

    public void sortStack(){

        //Collections.sort(Arrays.asList(stage.getActors().toArray()), new CardComparator());
        Collections.sort(Arrays.asList(group.getChildren().toArray()), new CardComparator());
        //Collections.reverse(stack);
    }

    public ArrayList<Card> getCards(){
        return stack;
    }

    public Card pickTop(){
        if(stack.size()>0)
            return (Card)stack.remove(0);
        return null;
        //return (Card)stack.get(0);
    }

    public boolean touchStack(Vector2 pos){
        /*
        System.out.println(pos);
        System.out.println(bounds);
        System.out.println(bounds.contains(pos));*/
        return bounds.contains(pos);
    }

    public void setTemporal(boolean temp){
        this.temporal=temp;
    }

    public boolean isTemporal(){return temporal;}
}
