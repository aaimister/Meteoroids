package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class ChargeBar {

    private ChargeSquare[] squares;

    private boolean dodging;

    private float x;
    private float y;

    private long dodgeTime;
    private long dodgeTimer;
    private long rechargeTime;
    private long rechargeTimer;

    private int width;
    private int height;

    public ChargeBar(GameWorld world, float x, float y, int width, int height, long dodgeTime, long rechargeTime) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dodgeTime = dodgeTime;
        this.rechargeTime = rechargeTime;
        float squareChargeTime = rechargeTime / 6000.0f;
        width = width / 6;
        squares = new ChargeSquare[] { new ChargeSquare(world, x, y, width, height, squareChargeTime),
            new ChargeSquare(world, x + width, y, width, height, squareChargeTime),
            new ChargeSquare(world, x + width * 2, y, width, height, squareChargeTime),
            new ChargeSquare(world, x + width * 3, y, width, height, squareChargeTime),
            new ChargeSquare(world, x + width * 4, y, width, height, squareChargeTime),
            new ChargeSquare(world, x + width * 5, y, width, height, squareChargeTime) };
    }

    public void reset() {
        for (ChargeSquare cs : squares) {
            cs.reset();
        }
    }

    public void updateTimer() {
        dodgeTimer = System.currentTimeMillis() + dodgeTime;
    }

    public boolean dodge() {
        if (System.currentTimeMillis() >= rechargeTimer && System.currentTimeMillis() <= dodgeTimer) {
            dodging = true;
            rechargeTimer = System.currentTimeMillis() + rechargeTime;
            for (int i = 0; i < squares.length; i++) {
                squares[i].charge(i);
            }
            return true;
        }
        return false;
    }

    public boolean getDodging() {
        return dodging;
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        batcher.begin();

        batcher.setColor(Color.WHITE);
        batcher.draw(AssetLoader.chargeBar, x, y, width, height);
        for (ChargeSquare cs : squares) {
            cs.draw(batcher, shapeRenderer, font, outline, runTime);
        }

        batcher.end();

    }

    private class ChargeSquare extends SpriteObject {

        private float rechargeTime;

        public ChargeSquare(GameWorld world, float x, float y, int width, int height, float rechargeTime) {
            super(world, x, y, width, height);
            this.rechargeTime = rechargeTime;
            setColor(AssetLoader.getColor(166.0f, 45.0f, 48.0f, 0.0f));
        }

        @Override
        public void update(float delta) {
            // Do nothing.
        }

        @Override
        public void reset() {
            tweenManager.killTarget(this);
            setAlpha(0.0f);
        }

        @Override
        public void touchDown(float screenX, float screenY, int pointer) {
            // Do nothing.
        }

        @Override
        public void touchUp(float screenX, float screenY, int pointer) {
            // Do nothing.
        }

        @Override
        public void collidedWith(SpriteObject so) {
            // Do nothing.
        }

        public void charge(int chargeNum) {
            Timeline.createSequence()
                    .pushPause(rechargeTime / 6.0f * (squares.length - chargeNum))
                    .push(Tween.to(this, SpriteAccessor.ALPHA, rechargeTime / 6.0f).target(1.0f).ease(TweenEquations.easeInOutQuad))
                    .pushPause(rechargeTime * chargeNum)
                    .push(Tween.to(this, SpriteAccessor.ALPHA, rechargeTime).target(0.0f).ease(TweenEquations.easeInOutQuad))
                    .start(tweenManager);
        }

        @Override
        public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
            batcher.setColor(getColor());
            batcher.draw(AssetLoader.chargeBarSquare[0], getX(), getY(), getWidth(), getHeight());
            batcher.setColor(1.0f, 1.0f, 1.0f, getColor().a);
            batcher.draw(AssetLoader.chargeBarSquare[1], getX(), getY(), getWidth(), getHeight());
        }
    }
}
