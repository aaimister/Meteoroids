package com.fatdolphingames.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {

    private GameWorld world;

    private SpriteBatch batcher;
    private ShapeRenderer shapeRenderer;

    public GameRenderer(GameWorld world, SpriteBatch batcher, ShapeRenderer shapeRenderer) {
        this.world = world;
        this.batcher = batcher;
        this.shapeRenderer = shapeRenderer;
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        world.drawShapeRenderer(shapeRenderer, runTime);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batcher.begin();
        world.drawBatcher(batcher, runTime);
        batcher.end();
    }
}
