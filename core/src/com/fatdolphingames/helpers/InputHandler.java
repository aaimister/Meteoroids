package com.fatdolphingames.helpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.fatdolphingames.gameworld.GameWorld;

public class InputHandler implements InputProcessor {

    private GameWorld world;

    private float scaleX;
    private float scaleY;

    private int fingers;

    public InputHandler(GameWorld world, float scaleX, float scaleY) {
        this.world = world;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean keyDown(int keycode) {
        fingers++;
        if (keycode == Keys.LEFT) {
            world.touchDown(0, 0, fingers);
        } else if (keycode == Keys.RIGHT) {
            world.touchDown(136, 0, fingers);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        fingers--;
        if (keycode == Keys.LEFT) {
            world.touchUp(0, 0, fingers);
        } else if (keycode == Keys.RIGHT) {
            world.touchUp(136, 0, fingers);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Do nothing.
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = (int) (((float) screenX) / scaleX);
        screenY = (int) (((float) screenY) / scaleY);
        fingers++;
        world.touchDown(screenX, screenY, fingers);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = (int) (((float) screenX) / scaleX);
        screenY = (int) (((float) screenY) / scaleY);
        fingers = fingers - 1 >= 0 ? fingers - 1 : 0;
        world.touchUp(screenX, screenY, fingers);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Do nothing.
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Do nothing.
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // Do nothing.
        return false;
    }
}
