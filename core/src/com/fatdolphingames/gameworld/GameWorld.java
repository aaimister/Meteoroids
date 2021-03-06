package com.fatdolphingames.gameworld;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameobjects.*;
import com.fatdolphingames.gameobjects.menu.Menu;
import com.fatdolphingames.helpers.AssetLoader;

public class GameWorld {

    private TweenManager tweenManager;
    private Ship ship;
    private PadManager padManager;
    private StarManager starManager;
    private MeteorManager meteorManager;
    private ScreenText retry;
    private Score score;
    private Menu menu;
    private Warning warning;

    private boolean tutorial;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;
    private float startX;
    private float startY;

    public GameWorld(TweenManager tweenManager, float gameWidth, float gameHeight, float midPointY) {
        this.tweenManager = tweenManager;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        tutorial = true;

        ship = new Ship(this, gameWidth / 2.0f - 12, gameHeight - 50, 24, 23);
        padManager = new PadManager(this, gameWidth, gameHeight, 50, 100);
        starManager = new StarManager(this);
        meteorManager = new MeteorManager(this, 9);
        float stringHeight = AssetLoader.calculateFontHeight("Tap To Retry", 0.3f);
        retry = new ScreenText(this, 35, midPointY - (stringHeight / 2.0f), (int) AssetLoader.calculateFontWidth("Tap To Retry", 0.3f), (int) stringHeight, "Tap To Retry", 0.3f);
        score = new Score(this, 3.0f, gameHeight - 22.0f, 0, 0);
        menu = new Menu(this, -113.0f, 27.0f, 112, (int) (gameHeight - 54.0f));
        warning = new Warning(this, gameWidth / 2.0f - 16, midPointY - 16, 32, 32);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    public void update(float delta) {
        if (!ship.isAlive() && ship.respawn() && !menu.isOpen() && !tutorial) {
            retry.show();
        }
        tweenManager.update(delta);
        ship.update(delta);
        starManager.update(delta);
        meteorManager.update(delta);
        score.update(delta);
        menu.update(delta);

    }

    public void touchDown(float screenX, float screenY, int pointer) {
        startX = screenX;
        startY = screenY;
        if (ship.isAlive()) {
            ship.touchDown(screenX, screenY, pointer);
            padManager.touchDown(screenX, screenY, pointer);
        } else if (menu.isOpen()) {
            menu.touchDown(screenX, screenY, pointer);
        }
    }

    public void touchUp(float screenX, float screenY, int pointer) {
        float leftRight = Math.abs(screenX - startX);
        float upDown = Math.abs(screenY - startY);
        padManager.touchUp(screenX, screenY, pointer);
        if (ship.isAlive()) {
            ship.touchUp(screenX, screenY, pointer);
        } else if (leftRight >= 25.0f || upDown >= 25.0f || menu.isOpen()) {
            menu.openMenu(screenX, screenY, startX, startY, leftRight, upDown);
        } else if (ship.respawn() && !tutorial) {
            retry.hide();
            reset();
        }
    }

    public void reset() {
        tutorial = false;
        ship.reset();
        padManager.reset();
        meteorManager.reset();
        score.reset();
        menu.reset();
        warning.reset();
    }

    public void checkShipCollisions(Meteor m) {
        ship.collidedWith(m);
    }

    public void drawBatcher(SpriteBatch batcher, float runTime) {
        starManager.drawBatcher(batcher, runTime);
        ship.drawBatcher(batcher, runTime);
        meteorManager.drawBatcher(batcher, runTime);
        ship.drawChargeBar(batcher, runTime);
        retry.drawBatcher(batcher, runTime);
        score.drawBatcher(batcher, runTime);
        warning.drawBatcher(batcher, runTime);
    }

    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        padManager.drawShapeRenderer(shapeRenderer, runTime);
    }

    public void drawMenu(SpriteBatch batcher, ShapeRenderer shapeRenderer, float runTime) {
        menu.draw(batcher, shapeRenderer, runTime);
    }

    public void addScore() {
        score.addScore();
    }

    public void hideRetryText() {
        retry.hide();
    }

    public int getScore() {
        return score.getScore();
    }

    public int getBestScore() {
        return score.getBestScore();
    }

    public boolean newBestScore() {
        return score.newBest();
    }

    public boolean isMenuOpen() {
        return menu.isOpen();
    }

    public boolean isShipAlive() {
        return ship.isAlive();
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

}
