package com.fatdolphingames.gameworld;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.MeteorManager;
import com.fatdolphingames.gameobjects.PadManager;
import com.fatdolphingames.gameobjects.Ship;
import com.fatdolphingames.gameobjects.StarManager;

public class GameWorld {

    private TweenManager tweenManager;
    private Ship ship;
    private PadManager padManager;
    private StarManager starManager;
    private MeteorManager meteorManager;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;

    public GameWorld(TweenManager tweenManager, float gameWidth, float gameHeight, float midPointY) {
        this.tweenManager = tweenManager;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        ship = new Ship(this, gameWidth / 2.0f - 12, gameHeight - 50, 24, 23);
        padManager = new PadManager(this, gameWidth, gameHeight, 50, 100);
        starManager = new StarManager(this);
        meteorManager = new MeteorManager(this, 1);
    }

    public void update(float delta) {
    //    System.out.println(tweenManager.getRunningTweensCount());
        tweenManager.update(delta);
        ship.update(delta);
        starManager.update(delta);
        meteorManager.update(delta);
    }

    public void touchDown(float screenX, float screenY, int pointer) {
        ship.touchDown(screenX, screenY, pointer);
        padManager.touchDown(screenX, screenY, pointer);
        starManager.touchDown(screenX, screenY, pointer);
    }

    public void touchUp(float screenX, float screenY, int pointer) {
        ship.touchUp(screenX, screenY, pointer);
        padManager.touchUp(screenX, screenY, pointer);
        starManager.touchUp(screenX, screenY, pointer);
    }

    public void reset() {
        ship.reset();
        padManager.reset();
        starManager.reset();
        meteorManager.reset();
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        starManager.draw(batcher, shapeRenderer, font, outline, runTime);
        padManager.draw(batcher, shapeRenderer, font, outline, runTime);
        meteorManager.draw(batcher, shapeRenderer, font, outline, runTime);
        ship.draw(batcher, shapeRenderer, font, outline, runTime);
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

    public PadManager getPadManager() {
        return padManager;
    }

    public StarManager getStarManager() {
        return starManager;
    }

}
