package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ship extends Sprite implements SpriteObject {

    public Ship(TextureRegion texture, int x, int y, int width, int height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void onTouch(float touchX, float touchY) {

    }

    @Override
    public void collidedWith(SpriteObject so) {

    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTIme) {

    }
}
