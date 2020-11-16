package com.fba.cardbase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fba.cardboard.CardBoard;

public class BoardScreen extends ScreenAdapter {

    CardBaseGame game;
    public Stage stage;
    CardBoard board;

    public BoardScreen(CardBaseGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Image portada = new Image(this.game.portada);
        portada.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage.addActor(portada);
        board = new CardBoard(this.game);
        board.setAtlas("atlas.txt","discard.png");
        board.setDiscard("discard.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                    return false;
                }
                return true;
            }
        });
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

        board.update();
        board.draw();

    }
}
