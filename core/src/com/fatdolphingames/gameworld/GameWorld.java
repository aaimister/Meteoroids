package com.fatdolphingames.gameworld;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.GamePad;
import com.fatdolphingames.gameobjects.Ship;
import com.fatdolphingames.gameobjects.StarManager;
import com.fatdolphingames.helpers.AssetLoader;

public class GameWorld {

    private TweenManager tweenManager;
    private Ship ship;
    private GamePad gamePad;
    private StarManager starManager;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;

    public GameWorld(TweenManager tweenManager, float gameWidth, float gameHeight, float midPointY) {
        this.tweenManager = tweenManager;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        ship = new Ship(this, AssetLoader.blank, (int) (gameWidth / 2.0f - 12), (int) (gameHeight - 50), 24, 23);
        gamePad = new GamePad(this, AssetLoader.blank, (int) gameWidth, 0, 40, 100);
        starManager = new StarManager(this);

    }

    public void update(float delta) {
        tweenManager.update(delta);
        ship.update(delta);
        starManager.update(delta);
    }

    public void onTouch(float touchX, float touchY) {
        ship.onTouch(touchX, touchY);
        gamePad.onTouch(touchX, touchY);
        starManager.onTouch(touchX, touchY);
    }

    public void reset() {
        ship.reset();
        gamePad.reset();
        starManager.reset();
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        ship.draw(batcher, shapeRenderer, font, outline, runTime);
        gamePad.draw(batcher, shapeRenderer, font, outline, runTime);
        starManager.draw(batcher, shapeRenderer, font, outline, runTime);
    }

    public float getGameWidth() {
        return gameWidth;
    }

    public float getGameHeight() {
        return gameHeight;
    }

    public float getMidPointY() {
        return midPointY;
    }

    public TweenManager getTweenManager() {
         return tweenManager;
    }

    public Ship getShip() {
        return ship;
    }

    public GamePad getGamePad() {
        return gamePad;
    }

    public StarManager getStarManager() {
        return starManager;
    }

}
