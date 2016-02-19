package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.Texture;
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

    private long life;
    private int size;
    private float gameWidth;
    private float gameHeight;
    private float duration;

    public Star(GameWorld world, Texture texture, float x, float y, int width, int height) {
        super(world, texture, x, y, width, height);
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
        setPosition(rand.nextInt((int) (gameWidth - getWidth() + 1)), rand.nextInt((int) (gameHeight - getWidth() + 1)));
        life = System.currentTimeMillis() + rand.nextInt(10001) + 2500;
        duration = (gameHeight - getY()) / 7.0f + rand.nextFloat();
        duration = life < duration ? life : duration - 3.0f < 0 ? 0 : duration;
        size = sizes[rand.nextInt(sizes.length)];
        Timeline.createParallel().beginParallel()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 3.0f).target(rand.nextFloat()).ease(TweenEquations.easeOutQuad))
                .push(Timeline.createSequence().beginSequence()
                    .push(Tween.to(this, SpriteAccessor.POSITION, duration).target(getX(), duration > 0 ? gameHeight + 1 : getY()).ease(TweenEquations.easeNone))
                    .push(Tween.to(this, SpriteAccessor.ALPHA, 3.0f).target(0.0f).ease(TweenEquations.easeOutQuad))
                    .end())
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
