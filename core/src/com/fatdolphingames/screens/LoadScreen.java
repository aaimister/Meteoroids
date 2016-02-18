package com.fatdolphingames.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.meteoroids.MGame;

public class LoadScreen implements Screen {

    private MGame game;
    private SpriteBatch batcher;
    private TweenManager tweenManager;

    private float width;
    private float height;
    private float scale;

    public LoadScreen(MGame game, TweenManager tweenManager, SpriteBatch batcher, float width, float height) {
        this.game = game;
        this.tweenManager = tweenManager;
        this.batcher = batcher;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);
        batcher.begin();

        batcher.end();

        if (Gdx.input.justTouched()) {
            tweenManager.killAll();
            LoadScreen.this.game.setScreen(new TutorialScreen(game, tweenManager));
        }
    }

    @Override
    public void show() {
        // Do nothing.
    }

    @Override
    public void resize(int width, int height) {
        // Do nothing.
    }

    @Override
    public void pause() {
        // Do nothing.
    }

    @Override
    public void resume() {
        // Do nothing.
    }

    @Override
    public void hide() {
        // Do nothing.
    }

    @Override
    public void dispose() {
        // Do nothing.
    }
}
