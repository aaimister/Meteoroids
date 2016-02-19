package com.fatdolphingames.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.meteoroids.MGame;

public class TutorialScreen implements Screen {

    private MGame game;
    private SpriteBatch batcher;
    private TweenManager tweenManager;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;

    public TutorialScreen(MGame game, TweenManager tweenManager) {
        this.game = game;
        this.tweenManager = tweenManager;

        gameWidth = 136.0f;
        gameHeight = ((float) Gdx.graphics.getHeight()) / (((float) Gdx.graphics.getWidth()) / gameWidth);
        midPointY = gameHeight / 2.0f;

        batcher = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batcher.begin();

        batcher.end();
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

    private void update(float delta) {
        tweenManager.update(delta);
        onTouch();
    }

    private void onTouch() {
        if (Gdx.input.justTouched()) {
       //     float scaleX = ((float) Gdx.graphics.getWidth()) / cam.viewportWidth;
       //     float scaleY = ((float) Gdx.graphics.getHeight()) / cam.viewportHeight;

        //    int screenX = (int) (((float) Gdx.input.getX()) / scaleX);
        //    int screenY = (int) (((float) Gdx.input.getY()) / scaleY);

            if (Gdx.input.justTouched()) {
                tweenManager.killAll();
                TutorialScreen.this.game.setScreen(new GameScreen(tweenManager, gameWidth, gameHeight, midPointY));
            }
        }
    }
}
