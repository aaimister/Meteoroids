package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Ship extends SpriteObject {

    private ChargeBar chargeBar;

    private boolean dead;
    private boolean sideRoll;
    private boolean teleporting;
    private boolean resetting;

    private long sideRollTime;
    private long sideRollTimer;
    private long deathTimer;

    private float gameWidth;
    private float fingerX[];
    private float lastScreenX;
    private float startX;
    private float startY;

    private int fullWidth;

    public Ship(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        startX = x;
        startY = y;
        gameWidth = world.getGameWidth();
        fingerX = new float[]{-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
        fullWidth = width;
        chargeBar = new ChargeBar(world, gameWidth - 35.0f, world.getGameHeight() - 15.0f, 24, 4, 2000, 20, 6000);
        sideRollTime = 215;
        reset();
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        tweenManager.killTarget(this);
        dead = false;
        resetting = true;
        for (int i = 0; i < fingerX.length; i++) {
            fingerX[i] = -1.0f;
        }
        chargeBar.reset();
        setPosition(startX, world.getGameHeight() + getHeight() + 1);
        setScale(0.5f, 0.5f);
        Timeline.createParallel()
                .push(Tween.to(this, SpriteAccessor.SCALE, 2.5f).target(1.0f, 1.0f).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(this, SpriteAccessor.POSITION, 2.5f).target(startX, startY).ease(TweenEquations.easeInOutQuad))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        resetting = false;
                    }
                }).start(tweenManager);
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        if (!dead && !teleporting && !resetting) {
            screenX = screenX <= gameWidth / 2.0f ? 0.0f : gameWidth - fullWidth;

            if (pointer > 1 && chargeBar.dodge()) {
                Timeline.createSequence()
                        .push(Tween.to(this, SpriteAccessor.SCALE, 0.5f).target(0.5f, 0.5f).ease(TweenEquations.easeInOutQuad).setCallback(new TweenCallback() {
                                @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                AssetLoader.dodgeDown.play(AssetLoader.volume);
                            }
                        }).setCallbackTriggers(TweenCallback.BEGIN))
                        .pushPause(1.0f)
                        .push(Tween.to(this, SpriteAccessor.SCALE, 0.5f).target(1.0f, 1.0f).ease(TweenEquations.easeInOutQuad).setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                AssetLoader.dodgeUp.play(AssetLoader.volume);
                            }
                        }).setCallbackTriggers(TweenCallback.BEGIN))
                        .start(tweenManager);

               // Tween.to(this, SpriteAccessor.SCALE, 0.5f).target(0.5f, 0.5f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 1.0f).start(tweenManager);
            }
            chargeBar.updateTimer();

            float speed = 100.0f;
            sideRoll = false;
            if (pointer == 1 && screenX == lastScreenX && System.currentTimeMillis() <= sideRollTimer) {
                float x = getX();
                if (x == 0 || x == (gameWidth - fullWidth)) {
                    teleport(x == 0);
                    return;
                } else {
                    speed = 200.0f;
                    sideRoll = true;
                }
            }

            lastScreenX = screenX;
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
        if (!dead && !resetting) {
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
    }

    private void teleport(final boolean left) {
        teleporting = true;
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 0.2f).target(0.0f).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(this, SpriteAccessor.POSITION, 0.2f).target(left ? -fullWidth : gameWidth, getY()).ease(TweenEquations.easeInOutQuad)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                setX(left ? gameWidth : -fullWidth);
                            }
                        }))
                .end()
                .beginParallel()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 0.2f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(this, SpriteAccessor.POSITION, 0.2f).target(left ? gameWidth - fullWidth : 0.0f, getY()).ease(TweenEquations.easeInOutQuad))
                .end()
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        teleporting = false;
                    }
                })
                .start(tweenManager);
    }

    public void stopMovement() {
        if (!teleporting) {
            tweenManager.killTarget(this, SpriteAccessor.POSITION);
        }
    }

    public boolean isResetting() {
        return resetting;
    }

    public boolean isSideRoll() {
        return sideRoll;
    }

    public boolean isTeleporting() {
        return teleporting;
    }

    public boolean respawn() {
        return System.currentTimeMillis() >= deathTimer;
    }

    private float calcDistance(float newX, float newY) {
        return (float) Math.sqrt(Math.pow(newX - getX(), 2) + Math.pow(newY - getY(), 2));
    }

    @Override
    public void collidedWith(SpriteObject so) {
        if (!dead && !chargeBar.isDodging() && getBoundingCircle().overlaps(so.getBoundingCircle())) {
            dead = true;
            stopMovement();
            deathTimer = System.currentTimeMillis() + 1000;
            AssetLoader.explosion().play();
            AssetLoader.explosion.play(AssetLoader.volume);
        }
    }

    public boolean isAlive() {
        return !dead;
    }

    public void drawChargeBar(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        chargeBar.draw(batcher, shapeRenderer, font, outline, runTime);
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        if (!dead) {
            batcher.begin();
            batcher.setColor(Color.WHITE);
            batcher.draw(AssetLoader.ship(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            batcher.draw(AssetLoader.thrusters().getKeyFrame(runTime), getX(), getY() + (21.0f * getScaleY()), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            batcher.end();
        } else {
            batcher.begin();
            batcher.setColor(Color.WHITE);
            batcher.draw(AssetLoader.explosion().getKeyFrame(runTime), getX(), getY());
            batcher.end();
        }
    }
}
