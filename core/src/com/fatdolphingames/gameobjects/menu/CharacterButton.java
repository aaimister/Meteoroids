package com.fatdolphingames.gameobjects.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;

public class CharacterButton extends MenuButton {

    public CharacterButton(GameWorld world, float x, float y, int width, int height, float offsetX, float offsetY) {
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
            // TODO Move to next character.
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
        // TODO Save current character.
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        // Do nothing.
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
