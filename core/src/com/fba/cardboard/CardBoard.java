package com.fba.cardboard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/*
CardBoard.java
Stores the stage and a collection of stacks of cards
 */
public class CardBoard {
    private final Game game;
    private boolean dragging = false;
    Stage stage;
    TextureAtlas atlas;

    private Stack discard, stack, board, onAir;

    private Card actualCard = null;


    public CardBoard(Game game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.atlas = new TextureAtlas();
    }

    public Stack setAtlas(String filename, String base) {
        atlas = new TextureAtlas(filename);
        stack = new Stack(base, stage);
        stack.setPosition(0, 0);
        board = new Stack(stage);//For the whole board
        onAir = new Stack(stage); // to manage the dragged cards
        int x = 0, y = 0;

        //Initialize the card's deck
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            final Actor actor = new Image(atlas.findRegion(region.name));
            Card card = new Card(this, actor, region.name);
            stack.addCard(card);
            card.update(x, y);
            stage.addActor(card.actor);
        }
        stack.reverseStack();
        return board;
    }

    public Stack setDiscard(String filename) {
        discard = new Stack(filename, stage, true);
        return discard;
    }

    public void update() {
        if (Gdx.input.isTouched()) {
            Vector2 pos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (!dragging) {
                if (stack.touchStack(pos)) {
                    actualCard = stack.pickTop();
                    if (actualCard != null) {
                        actualCard.update(pos);
                        dragging = true;
                        onAir.addCard(actualCard);
                    }
                }
                if (!dragging) {
                    for (Card card : board.getCards()) {
                        if (card.contains(pos)) {
                            board.dropCard(card);
                            actualCard = card;
                            card.update(pos);
                            onAir.addCard(actualCard);
                            dragging = true;
                            break;
                        }
                    }
                }
            } else {
                if (actualCard != null) actualCard.update(pos);
            }
        } else {
            dragging = false;
            if (actualCard != null) {
                //Verify if touch the discard stack
                if (discard.touchStack(actualCard.getBounds())) {
                    board.dropCard(actualCard);
                    discard.addCard(actualCard);
                } else {
                    //Then drop the card in the board
                    actualCard.drop();
                    onAir.dropCard(actualCard);
                    board.addCard(actualCard);
                    stack.dropCard(actualCard);
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
