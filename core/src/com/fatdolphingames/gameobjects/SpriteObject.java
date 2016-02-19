package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;

public abstract class SpriteObject extends Sprite {

    protected GameWorld world;

    public SpriteObject(GameWorld world, float x, float y, int width, int height) {
        super();
        setBounds(x, y, width, height);
        this.world = world;
    };

    public abstract void update(float delta);

    public abstract void reset();

    public abstract void onTouch(float touchX, float touchY);

    public abstract void collidedWith(SpriteObject so);

    public abstract void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime);

    public TweenManager getTweenManager() {
        return world.getTweenManager();
    }

}
