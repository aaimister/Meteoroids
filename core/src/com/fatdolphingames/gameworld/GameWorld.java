package com.fatdolphingames.gameworld;

import aurelienribon.tweenengine.TweenManager;

public class GameWorld {

    private TweenManager tweenManager;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;

    public GameWorld(TweenManager tweenManager, float gameWidth, float gameHeight, float midPointY) {
        this.tweenManager = tweenManager;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;


    }

    public void update(float delta) {

    }

    public void onTouch(float touchX, float touchY) {

    }

    public void reset() {

    }



}
