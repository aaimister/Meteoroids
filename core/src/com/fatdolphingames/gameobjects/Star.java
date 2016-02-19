package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private float duration;
    private float life;

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
        randomize();
    }

    @Override
    public void onTouch(float touchX, float touchY) {
        // Do nothing.
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    private void randomize() {
        setAlpha(0.0f);
        setPosition(rand.nextInt((int) (gameWidth - getWidth() + 1)), rand.nextInt((int) (gameHeight - getHeight() - 25 + 1)));
        life = rand.nextInt(11) + 4.0f;
        duration = (gameHeight - getY()) / 7.0f + rand.nextFloat();
        duration = duration - life < 0 ? 4.0f : duration - life;
        size = sizes[rand.nextInt(sizes.length)];
        Timeline.createParallel()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 3.0f).target(rand.nextFloat()).ease(TweenEquations.easeOutQuad))
                .push(Tween.to(this, SpriteAccessor.POSITION, duration).target(getX(), gameHeight).ease(TweenEquations.easeNone))
                .push(Tween.to(this, SpriteAccessor.ALPHA, 1.0f).target(0.0f).ease(TweenEquations.easeOutQuad).delay(duration - 1.0f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        randomize();
                    }
                }).start(getTweenManager());
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        batcher.begin();

        batcher.setColor(1.0f, 1.0f, 1.0f, getColor().a);
        batcher.draw(AssetLoader.stars[size == 5 ? 0 : size == 3 ? 1 : 2], getX(), getY());

        batcher.end();
    }
}
