package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class GamePad extends SpriteObject {

    private boolean left;

    private float gameWidth;
    private float gameHeight;

    public GamePad(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();

        setColor(AssetLoader.getColor(29.0f, 29.0f, 29.0f, 0.0f));
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        setAlpha(0.0f);
    }

    @Override
    public void onTouch(float touchX, float touchY) {
        boolean changed = left;
        left = touchX < gameWidth / 2.0f;
        if (changed != left)
            setAlpha(0.0f);

        Tween.to(this, SpriteAccessor.ALPHA, 0.2f).target(1.0f).ease(TweenEquations.easeInOutQuad).start(getTweenManager());
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeType.Filled);

        if (left) {
            // Left Button
            shapeRenderer.setColor(getColor());
            shapeRenderer.rect(0.0f, gameHeight - 140.0f, 40.0f, 100.0f);
            shapeRenderer.arc(40.0f, gameHeight - 130.0f, 10.0f, 270.0f, 90.0f);
            shapeRenderer.arc(40.0f, gameHeight - 50.0f, 10.0f, 0.0f, 90.0f);
            shapeRenderer.rect(40.0f, gameHeight - 130.0f, 10.0f, 80.0f);
        } else {
            // Right Button
            shapeRenderer.setColor(getColor());
            shapeRenderer.rect(gameWidth - 40.0f, gameHeight - 140.0f, 40.0f, 100.0f);
            shapeRenderer.arc(gameWidth - 40.0f, gameHeight - 130.0f, 10.0f, 180.0f, 90.0f);
            shapeRenderer.arc(gameWidth - 40.0f, gameHeight - 50.0f, 10.0f, 90.0f, 90.0f);
            shapeRenderer.rect(gameWidth - 50.0f, gameHeight - 130.0f, 10.0f, 80.0f);
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
