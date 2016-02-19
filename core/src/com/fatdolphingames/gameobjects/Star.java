package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;

import java.util.Random;

public class Star extends SpriteObject {

    public static int[] sizes = { 5, 3, 1 };

    private Random rand;

    private boolean tweenAlpha;

    private long life;
    private int size;
    private float gameWidth;
    private float gameHeight;
    private float duration;

    public Star(GameWorld world, TextureRegion texture, int x, int y, int width, int height) {
        super(world, texture, x, y, width, height);
        rand = new Random();
        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();
        randomize();
    }

    @Override
    public void update(float delta) {
        if (getY() > gameHeight) {
            reset();
        } else if (System.currentTimeMillis() >= life) {
            if (getColor().a > 0.0f) {
                tweenAlpha = true;
            } else {
                reset();
            }
        }
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
        tweenAlpha = true;
        setX(rand.nextInt((int) (gameWidth - getWidth() + 1)));

    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {

    }
}
