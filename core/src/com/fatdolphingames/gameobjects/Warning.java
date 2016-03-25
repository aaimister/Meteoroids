package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Warning extends SpriteObject {

    public Warning(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        setAlpha(0.0f);
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        setAlpha(0.0f);
        Timeline.createSequence()
                .push(Tween.to(this, SpriteAccessor.ALPHA, 0.5f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.0f).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        AssetLoader.warningVoice.play(AssetLoader.volume);
                    }
                }).setCallbackTriggers(TweenCallback.BEGIN))
                .push(Tween.to(this, SpriteAccessor.ALPHA, 0.5f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.0f).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        AssetLoader.warningVoice.play(AssetLoader.volume);
                    }
                }).setCallbackTriggers(TweenCallback.BEGIN))
                .push(Tween.to(this, SpriteAccessor.ALPHA, 0.5f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.0f).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        AssetLoader.warningVoice.play(AssetLoader.volume);
                    }
                }).setCallbackTriggers(TweenCallback.BEGIN))
                .start(tweenManager);
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

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        batcher.setColor(1.0f, 1.0f, 1.0f, getColor().a);
        batcher.draw(AssetLoader.warning, getX(), getY());
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
