package com.fatdolphingames.gameobjects.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class VolumeButton extends MenuButton {

    private boolean on;

    public VolumeButton(GameWorld world, float x, float y, int width, int height, float offsetX, float offsetY) {
        super(world, x, y, width, height, offsetX, offsetY);
        on = AssetLoader.prefs.getBoolean("volumeOnOff");
        if (!on)
            AssetLoader.toggleVolume();
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        // Do nothing.
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        if (getBoundingRectangle().contains(screenX, screenY)) {
            on = !on;
            AssetLoader.toggleVolume();
        }
    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
        // Do nothing.
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    @Override
    public void save() {
        AssetLoader.prefs.putBoolean("volumeOnOff", on);
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        batcher.setColor(Color.WHITE);
        batcher.draw(AssetLoader.sound[on ? 0 : 1], getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
