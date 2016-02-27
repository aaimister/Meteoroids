package com.fatdolphingames.gameobjects;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class ScreenText extends SpriteObject {

    private String text;

    private boolean show;

    public ScreenText(GameWorld world, float x, float y, int width, int height, String text, float scale) {
        super(world, x, y, width, height);
        this.text = text;
        setScale(scale);
        setAlpha(0.0f);
    }


    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        tweenManager.killTarget(this);
        setAlpha(0.0f);
        show = false;
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

    public void show() {
        if (!show) {
            show = true;
            Tween.to(this, SpriteAccessor.ALPHA, 0.5f).target(1.0f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
        }
    }

    public void hide() {
        Tween.to(this, SpriteAccessor.ALPHA, 0.5f).target(0.0f).ease(TweenEquations.easeInOutQuad).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                show = false;
            }
        }).start(tweenManager);
    }

    public boolean showing() {
        return show;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        if (show) {
            AssetLoader.setFontScale(getScaleX());
            Color c = AssetLoader.BLACK;
            batcher.begin();
            outline.setColor(c.r, c.g, c.b, getColor().a);
            outline.draw(batcher, text, getX(), getY());
            c = AssetLoader.WHITE;
            font.setColor(c.r, c.g, c.b, getColor().a);
            font.draw(batcher, text, getX(), getY());
            batcher.end();
        }
    }
}
