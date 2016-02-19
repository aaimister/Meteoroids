package com.fatdolphingames.helpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fatdolphingames.gameobjects.GamePad;
import com.fatdolphingames.gameobjects.Ship;
import com.fatdolphingames.gameworld.GameWorld;

public class InputHandler implements InputProcessor {

    private GameWorld world;
    private Ship ship;
    private GamePad gamePad;

    private int dragCount;
    private int fingers;

    private long doublePressTime;
    private long doublePressTimer;

    private float scaleX;
    private float scaleY;
    private float startX;
    private float startY;

    public InputHandler(GameWorld world, OrthographicCamera cam) {
        this.world = world;

        scaleX = ((float) Gdx.graphics.getWidth()) / cam.viewportWidth;
        scaleY = ((float) Gdx.graphics.getHeight()) / cam.viewportHeight;
        ship = world.getShip();
        gamePad = world.getGamePad();
        doublePressTime = 10;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT) {
            ship.onTouch(0, 0);
            gamePad.onTouch(0, 0);
        } else if (keycode == Keys.RIGHT) {
            ship.onTouch(136, 0);
            gamePad.onTouch(136, 0);
        }

     /*   if (keycode == Keys.UP)
            ship.dodge();

        if (ship.isDead() && world.isMenuClosed() && keycode == Keys.SPACE)
            world.reset();
    */
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
       // ship.stopMovement();
        gamePad.reset();

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Do nothing.
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
