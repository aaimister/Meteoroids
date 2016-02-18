package com.fatdolphingames.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Screen;
import com.fatdolphingames.gameworld.GameRenderer;
import com.fatdolphingames.gameworld.GameWorld;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    private float runTime;

    public GameScreen(TweenManager tweenManager, float gameWidth, float gameHeight, float midPointY) {
        runTime = 5.0f;

        world = new GameWorld(tweenManager, gameWidth, gameHeight, midPointY);
        renderer = new GameRenderer(world);

      //  Gdx.input.setInputProcessor();
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
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
        // TODO save here
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
