package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Ship extends SpriteObject {

    private ChargeBar chargeBar;

    private boolean dead;
    private boolean dodge;
    private boolean sideRoll;

    private long sideRollTime;
    private long sideRollTimer;

    private float gameWidth;
    private float fingerX[];

    private int fullWidth;

    public Ship(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        gameWidth = world.getGameWidth();
        fingerX = new float[]{-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
        fullWidth = width;
        chargeBar = new ChargeBar(world, gameWidth - 35.0f, world.getGameHeight() - 15, 24, 4, 15, 6000);
        sideRollTime = 200;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {
        tweenManager.killTarget(this);
        dead = false;
        chargeBar.reset();
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        if (!dead) {
            if (pointer > 1 && chargeBar.dodge()) {
                Tween.to(this, SpriteAccessor.SCALE, 0.5f).target(0.5f, 0.5f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 1.0f).start(tweenManager);
            }
            chargeBar.updateTimer();

            float speed = (sideRoll = (pointer == 1 && System.currentTimeMillis() <= sideRollTimer)) ? 200.0f : 100.0f;
            screenX = screenX <= gameWidth / 2.0f ? 0.0f : gameWidth - fullWidth;
            fingerX[pointer - 1] = screenX;

            stopMovement();
            Tween.to(this, SpriteAccessor.POSITION, calcDistance(screenX, getY()) / speed).target(screenX, getY()).ease(TweenEquations.easeInOutQuad).setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    sideRoll = false;
                }
            }).start(tweenManager);
            sideRollTimer = System.currentTimeMillis() + sideRollTime;
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
            if (!sideRoll) {
                stopMovement();
            }
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
        if (!dead && dodge) {

        }
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX() + (getWidth() - getWidth() * getScaleX()) / 2.0f, getY() + (getHeight() - getHeight() * getScaleY()) / 2.0f, getWidth() * getScaleX(), getHeight() * getScaleY());
        shapeRenderer.end();

        batcher.begin();
        batcher.setColor(Color.WHITE);
        batcher.draw(AssetLoader.meteoroids[0][0], getX(), getY());
        batcher.end();

        chargeBar.draw(batcher, shapeRenderer, font, outline, runTime);
    }
}
