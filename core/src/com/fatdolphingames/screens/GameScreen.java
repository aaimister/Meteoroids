package com.fatdolphingames.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameRenderer;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    private float runTime;

    public GameScreen(GameWorld world, OrthographicCamera cam, SpriteBatch batcher, ShapeRenderer shapeRenderer) {
        runTime = 5.0f;

        this.world = world;
        renderer = new GameRenderer(world, cam, batcher, shapeRenderer);

        world.reset();
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
        AssetLoader.save();
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
