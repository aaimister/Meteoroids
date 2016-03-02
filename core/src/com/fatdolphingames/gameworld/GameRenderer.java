package com.fatdolphingames.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.helpers.AssetLoader;

public class GameRenderer {

    private GameWorld world;

    private SpriteBatch batcher;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private BitmapFont font;
    private BitmapFont outline;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;

    public GameRenderer(GameWorld world, OrthographicCamera cam, SpriteBatch batcher, ShapeRenderer shapeRenderer) {
        this.world = world;
        this.cam = cam;
        this.batcher = batcher;
        this.shapeRenderer = shapeRenderer;

        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();
        midPointY = world.getMidPointY();

        font = AssetLoader.font;
        outline = AssetLoader.outline;
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.draw(batcher, shapeRenderer, font, outline, runTime);

    }

    public OrthographicCamera getCamera() {
        return cam;
    }

}
