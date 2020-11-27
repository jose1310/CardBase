package com.fba.cardbase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CardBaseGame extends Game {
    public SpriteBatch batch;
    Texture background;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("badlogic.jpg");

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new BoardScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
