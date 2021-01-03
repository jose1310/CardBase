package com.fba.cardbase;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fba.cardboard.CardBoard;
import com.fba.cardboard.Stack;

public class BoardScreen extends ScreenAdapter {

    CardBaseGame game;
    Stage stage;
    CardBoard board;
    Stack source;
    Stack discard;
    Stack temp;
    InputMultiplexer multiplexer;

    public BoardScreen(CardBaseGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);

        Image background = new Image(this.game.background);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage.addActor(background);
        board = new CardBoard(this.game,multiplexer);
        source = board.setAtlas("atlas.txt", "discard.png");
        board.setOnAir();

        discard = board.setDiscard("discard.png");
        discard.setWaterfall(true);

        temp = board.setDiscard("discard.png");
        temp.setWaterfall(true);
        temp.setPosition(200,200);
    }

    @Override
    public void show() {
        /*
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                    return false;
                }
                return true;
            }
        });*/
        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        stage.act();
        stage.draw();
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Click the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);

        game.batch.end();

        board.draw();
        board.update();


    }
}
