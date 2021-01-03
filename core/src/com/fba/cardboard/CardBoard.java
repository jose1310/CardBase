package com.fba.cardboard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/*
CardBoard.java
Stores the stage and a collection of stacks of cards
 */
public class CardBoard {
    private final Game game;
    private boolean dragging = false;
    Stage stage;
    TextureAtlas atlas;
    public InputMultiplexer multiplexer;

    //private Stack discard;
    ArrayList<Stack> discardList=new ArrayList<Stack>();
    //private ArrayList<Stack> stackList=new ArrayList<Stack>();
    private Stack stack;
    private Stack board;
    private Stack onAir;

    private Card actualCard = null;
    private Vector2 lastPosition = null;
    private Stack movingStack;


    public CardBoard(Game game, InputMultiplexer multiplexer) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.atlas = new TextureAtlas();
        this.multiplexer=multiplexer;
    }

    public Stack setAtlas(String filename, String base) {
        atlas = new TextureAtlas(filename);
        //stackList.add(new Stack(base, stage));
        stack = new Stack(base, stage);
        //stack.setPosition(0, 0);
        //Stack stack = stackList.get(stackList.size()-1);
        //int pos = stackList.size()-1;
        board = new Stack(stage);//For the whole board
        //onAir = new Stack(stage); // to manage the dragged cards
        int x = 0;
        int y = 0;

        //Initialize the card's deck
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            final Actor actor = new Image(atlas.findRegion(region.name));
            Card card = new Card(this, actor, region.name);
            board.addCard(card);
            //stackList.get(pos).addCard(card);
            card.update(x, y);
            stage.addActor(card.actor);
        }
        //stackList.get(pos).reverseStack();
        //board.reverseStack();
        return board;
    }

    public Stack setDiscard(String filename) {
        discardList.add(new Stack(filename, stage, true));
        return discardList.get(discardList.size()-1);
    }

    public Stack setOnAir() {
        onAir = new Stack(stage);
        return onAir;
    }

    public Stack setStack(String filename) {
        return new Stack(filename, stage, false);
    }

    public void update() {
        if (Gdx.input.isTouched()) {
            lastPosition = new Vector2(Gdx.input.getX(), (float)Gdx.graphics.getHeight() - Gdx.input.getY());
            if (!dragging) {
                    if (board.touchStack(lastPosition)) {
                        actualCard = stack.pickTop();
                        if (actualCard != null) {
                            actualCard.update(lastPosition);
                            dragging = true;
                            onAir.addCard(actualCard);
                            //movingStack=stack;
                        }
                    }
                if (!dragging) {
                    for (Card card : board.getCards()) {
                        if (card.contains(lastPosition)) {
                            board.dropCard(card);
                            actualCard = card;
                            card.update(lastPosition);
                            onAir.addCard(actualCard);
                            dragging = true;
                            break;
                        }
                    }
                }
            } else {
                if (actualCard != null) actualCard.update(lastPosition);
            }
        } else {
            dragging = false;
            if (actualCard != null) {
                //Verify if touch the discard stack list
                boolean droped=false;
                for (Stack discard:discardList) {
                    if (discard.touchStack(lastPosition)) {
                        board.dropCard(actualCard);
                        discard.addCard(actualCard);
                        droped=true;
                        break;
                    }
                }
                if (!droped) {
                    //Then drop the card in the board
                    actualCard.drop();
                    onAir.dropCard(actualCard);
                    board.addCard(actualCard);
                    stack.dropCard(actualCard);
                    movingStack = null;
                }
            }
            actualCard = null;
        }
    }

    public void draw() {
        stage.act();
        stage.draw();
    }
}
