package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.accessors.SpriteObjectAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

import java.util.Random;

public class Meteor extends SpriteObject {

    private Random rand;
    private Circle hitBox;

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
        setY(-getHeight());
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
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        batcher.begin();
        batcher.setColor(Color.WHITE);
        batcher.draw(AssetLoader.meteoroids[size][textureType], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
       // batcher.draw(AssetLoader.meteoroids[0][0], getX(), getY());
        batcher.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.0f, 0.0f, 1.0f, 0.2f);
        Circle cbounds = getBoundingCircle();
    //    shapeRenderer.circle(cbounds.x, cbounds.y, cbounds.radius);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
