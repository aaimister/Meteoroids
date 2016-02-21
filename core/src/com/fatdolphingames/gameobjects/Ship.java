package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;

public class Ship extends SpriteObject {

    private boolean dead;

    private float gameWidth;
    private float fingerX[];

    private int fullWidth;

    public Ship(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        gameWidth = world.getGameWidth();
        fingerX = new float[] { -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f };
        fullWidth = width;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {
        dead = false;
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        if (!dead) {
            screenX = screenX <= gameWidth / 2.0f ? 0.0f : gameWidth - fullWidth;
            fingerX[pointer - 1] = screenX;

            stopMovement();
            Tween.to(this, SpriteAccessor.POSITION, calcDistance(screenX, getY()) / 100).target(screenX, getY()).ease(TweenEquations.easeInOutQuad).start(tweenManager);
        }
    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
        if (pointer > 0) {
            screenX = screenX <= gameWidth / 2.0f ? 0.0f : gameWidth - fullWidth;
            if (fingerX[pointer] == screenX) {
                stopMovement();
                Tween.to(this, SpriteAccessor.POSITION, calcDistance(fingerX[pointer - 1], getY()) / 100).target(fingerX[pointer - 1], getY()).ease(TweenEquations.easeInOutQuad).start(tweenManager);
            } else {
                fingerX[pointer - 1] = fingerX[pointer];
                fingerX[pointer] = -1.0f;
            }
        } else {
            stopMovement();
        }
    }

    public void stopMovement() {
        tweenManager.killTarget(this, SpriteAccessor.POSITION);
    }

    private float calcDistance(float newX, float newY) {
        return (float) Math.sqrt(Math.pow(newX - getX(), 2) + Math.pow(newY - getY(), 2));
    }

    @Override
    public void collidedWith(SpriteObject so) {

    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();

    }
}
