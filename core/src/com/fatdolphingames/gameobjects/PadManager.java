package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;

public class PadManager {

    private Pad leftPad;
    private Pad rightPad;

    public PadManager(GameWorld world, float gameWidth, float gameHeight, int width, int height) {
        leftPad = new Pad(world, 0.0f, gameHeight - 140.0f, width, height, true);
        rightPad = new Pad(world, gameWidth - 40.0f, gameHeight - 140.0f, width, height, false);
    }

    public void update(float delta) {
        leftPad.update(delta);
        rightPad.update(delta);
    }

    public void reset() {
        leftPad.reset();
        rightPad.reset();
    }

    public void touchDown(float screenX, float screenY, int pointer) {
        leftPad.touchDown(screenX, screenY, pointer);
        rightPad.touchDown(screenX, screenY, pointer);
    }

    public void touchUp(float screenX, float screenY, int pointer) {
        leftPad.touchUp(screenX, screenY, pointer);
        rightPad.touchUp(screenX, screenY, pointer);
    }

    public void collidedWith(SpriteObject so) {
        leftPad.collidedWith(so);
        rightPad.collidedWith(so);
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        leftPad.draw(batcher, shapeRenderer, font, outline, runTime);
        rightPad.draw(batcher, shapeRenderer, font, outline, runTime);
    }

}
