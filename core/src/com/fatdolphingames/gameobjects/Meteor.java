package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.accessors.SpriteObjectAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

import java.util.Random;

public class Meteor extends SpriteObject {

    private Random rand;

    private boolean offScreen;

    // 0 = small, 1 = medium, 2 = big
    private int size;
    // 0 - 3
    private int textureType;

    private float gameHeight;
    private float duration;
    private float rotation;

    public Meteor(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        rand = new Random();
        size = width == 28 ? 0 : width == 20 ? 1 : 2;
        gameHeight = world.getGameHeight();
        offScreen = true;
        setOriginCenter();
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        tweenManager.killTarget(this);
        setY(-getHeight() - 1);
        offScreen = true;
        duration = rand.nextInt(2) + 1 + rand.nextFloat();
        rotation = rand.nextInt(361);
        textureType = rand.nextInt(4);
    }

    public void fire(float x) {
        if (offScreen) {
            reset();
            setX(x);
            offScreen = false;
            Timeline.createParallel()
                    .push(Tween.to(this, SpriteAccessor.POSITION, duration).target(getX(), gameHeight + getHeight()).ease(TweenEquations.easeNone))
                    .push(Tween.to(this, SpriteObjectAccessor.ROTATION, duration).target(rotation).ease(TweenEquations.easeNone))
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            offScreen = true;
                            if (world.isShipAlive()) {
                                world.addScore();
                                AssetLoader.scoreCount.play(AssetLoader.volume);
                            }
                        }
                    })
                    .start(tweenManager);
        }
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        // Do nothing.
    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
        // Do nothing.
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    public boolean isOffScreen() {
        return offScreen;
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        batcher.setColor(Color.WHITE);
        batcher.draw(AssetLoader.meteoroids[size][textureType], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
