package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;

public class Pad extends SpriteObject {

    private boolean left;
    private boolean draw;

    private float gameWidth;

    public Pad(GameWorld world, float x, float y, int width, int height, boolean left) {
        super(world, x, y, width, height);
        gameWidth = world.getGameWidth();

        this.left = left;

        setColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        tweenManager.killTarget(this);
        setAlpha(0.0f);
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        tweenManager.killTarget(this);
        if (draw && !(left ? screenX <= gameWidth / 2.0f : screenX > gameWidth / 2.0f)) {
            setAlpha(0.0f);
        } else if (draw = left ? screenX <= gameWidth / 2.0f : screenX > gameWidth / 2.0f) {
            Tween.to(this, SpriteAccessor.ALPHA, 0.2f).target(0.12f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
        }
    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
        tweenManager.killTarget(this);
        if (left ? screenX <= gameWidth / 2.0f : screenX > gameWidth / 2.0f) {
            draw = false;
            setAlpha(0.0f);
        } else {
            Tween.to(this, SpriteAccessor.ALPHA, 0.2f).target(0.12f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
        }
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        // Do nothing.
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        if (draw) {
            if (left) {
                // Left Button
                shapeRenderer.setColor(getColor());
                shapeRenderer.rect(getX(), getY(), getWidth() * 0.8f, getHeight());
                shapeRenderer.arc(getX() + (getWidth() * 0.8f), getY() + (getHeight() * 0.1f),  getWidth() * 0.2f, 270.0f, 90.0f);
                shapeRenderer.arc(getX() + (getWidth() * 0.8f), getY() + getHeight() - (getHeight() * 0.1f), getWidth() * 0.2f, 0.0f, 90.0f);
                shapeRenderer.rect(getX() + (getWidth() * 0.8f), getY() + (getHeight() * 0.1f), getWidth() * 0.2f, getHeight() * 0.8f);
            } else {
                // Right Button
                shapeRenderer.setColor(getColor());
                shapeRenderer.rect(getX(), getY(), getWidth() * 0.8f, getHeight());
                shapeRenderer.arc(getX(), getY() + (getHeight() * 0.1f), getWidth() * 0.2f, 180.0f, 90.0f);
                shapeRenderer.arc(getX(), getY() + getHeight() - (getHeight() * 0.1f), getWidth() * 0.2f, 90.0f, 90.0f);
                shapeRenderer.rect(getX() - (getWidth() * 0.2f), getY() + (getHeight() * 0.1f), getWidth() * 0.2f, 80.0f);
            }
        }
    }
}
