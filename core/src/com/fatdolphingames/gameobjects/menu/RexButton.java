package com.fatdolphingames.gameobjects.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class RexButton extends MenuButton {

    public RexButton(GameWorld world, float x, float y, int width, int height, float offsetX, float offsetY) {
        super(world, x, y, width, height, offsetX, offsetY);
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
            Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.fatdolphongames.jumper.android");
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
        // Do nothing.
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        batcher.draw(AssetLoader.tapRex, getX(), getY(), 0.0f, 0.0f, AssetLoader.tapRex.getRegionWidth(), AssetLoader.tapRex.getRegionHeight(), -0.2f, -0.2f, 180.0f);
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
