package com.fatdolphingames.gameworld;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        menu = new Menu(this, -113.0f, 27.0f, 112, 180);
        warning = new Warning(this, gameWidth / 2.0f - 16, midPointY - 16, 32, 32);
    }

    public void update(float delta) {
    //    System.out.println(tweenManager.getRunningTweensCount());
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

    public void touchUp(float screenX, float screenY, int pointer, int dragCount) {
        padManager.touchUp(screenX, screenY, pointer);
        if (ship.isAlive()) {
            ship.touchUp(screenX, screenY, pointer);
        } else if (dragCount > 3 || menu.isOpen()) {
            menu.touchUp(screenX, screenY, pointer);
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

    public void checkShipCollisions(Meteor[] meteors) {
        for (Meteor m : meteors) {
            ship.collidedWith(m);
        }
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        starManager.draw(batcher, shapeRenderer, font, outline, runTime);
        padManager.draw(batcher, shapeRenderer, font, outline, runTime);
        ship.draw(batcher, shapeRenderer, font, outline, runTime);
        meteorManager.draw(batcher, shapeRenderer, font, outline, runTime);
        ship.drawChargeBar(batcher, shapeRenderer, font, outline, runTime);
        retry.draw(batcher, shapeRenderer, font, outline, runTime);
        score.draw(batcher, shapeRenderer, font, outline, runTime);
        warning.draw(batcher, shapeRenderer, font, outline, runTime);
        menu.draw(batcher, shapeRenderer, font, outline, runTime);
    }

    public void addScore() {
        score.addScore();
    }

    public void hideRetryText() {
        retry.hide();
    }

    public void toggleRetryText() {
        retry.toggle();
    }

    public int getScore() {
        return score.getScore();
    }

    public int getBestScore() {
        return score.getBestScore();
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
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
