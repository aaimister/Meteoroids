package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.fatdolphingames.gameworld.GameWorld;

public abstract class SpriteObject extends Sprite {

    protected TweenManager tweenManager;
    protected GameWorld world;
    protected Circle bounds;

    protected float rotation;

    public SpriteObject(GameWorld world, float x, float y, int width, int height) {
        super();
        setBounds(x, y, width, height);
        this.tweenManager = world.getTweenManager();
        this.world = world;
    };

    public abstract void update(float delta);

    public abstract void reset();

    public abstract void touchDown(float screenX, float screenY, int pointer);

    public abstract void touchUp(float screenX, float screenY, int pointer);

    public abstract void collidedWith(SpriteObject so);

    public abstract void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime);

    public Circle getBoundingCircle() {
        if (bounds == null) {
            bounds = new Circle();
        }
        Rectangle rec = getBoundingRectangle();
        bounds.x = rec.x + rec.width / 2.0f;
        bounds.y = rec.y + rec.height / 2.0f;
        bounds.radius = rec.width / 2.0f;
        return bounds;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
