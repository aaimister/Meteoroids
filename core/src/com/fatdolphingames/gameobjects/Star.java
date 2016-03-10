package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

import java.util.Random;

public class Star extends SpriteObject {

    public static int[] sizes = { 5, 3, 1 };

    private Random rand;

    private int size;
    private float gameWidth;
    private float gameHeight;

    public Star(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        rand = new Random();
        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();
        randomize();
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
       tweenManager.killTarget(this);
        randomize();
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

    private void randomize() {
        setAlpha(0.0f);
        setPosition(rand.nextInt((int) (gameWidth - getWidth() + 1)), rand.nextInt((int) (gameHeight - getHeight() - 25 + 1)));
        float life = rand.nextInt(11) + 4.0f;
        float duration = (gameHeight - getY()) / 7.0f + rand.nextFloat();
        duration = duration - life < 0 ? 4.0f : duration - life;
        size = sizes[rand.nextInt(sizes.length)];
        Timeline.createParallel()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 3.0f).target(rand.nextFloat()).ease(TweenEquations.easeInOutQuad))
                .push(Tween.to(this, SpriteAccessor.POSITION, duration).target(getX(), gameHeight).ease(TweenEquations.easeNone))
                .push(Tween.to(this, SpriteAccessor.ALPHA, 1.0f).target(0.0f).ease(TweenEquations.easeInOutQuad).delay(duration - 1.0f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        randomize();
                    }
                }).start(tweenManager);
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        batcher.setColor(1.0f, 1.0f, 1.0f, getColor().a);
        batcher.draw(AssetLoader.stars[size == 5 ? 0 : size == 3 ? 1 : 2], getX(), getY());
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
