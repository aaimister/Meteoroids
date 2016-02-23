package com.fatdolphingames.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.accessors.SpriteObjectAccessor;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.helpers.AssetLoader;
import com.fatdolphingames.meteoroids.MGame;

public class SplashScreen implements Screen {

    private MGame game;
    private SpriteBatch batcher;
    private Sprite dolphin;
    private TweenManager tweenManager;

    private float width;
    private float height;
    private float scale;

    public SplashScreen(MGame game) {
        this.game = game;
        batcher = new SpriteBatch();
        dolphin = new Sprite(AssetLoader.dolphin);
        dolphin.setColor(1.0f, 1.0f, 1.0f, 0.0f);

        width = (float) Gdx.graphics.getWidth();
        height = (float) Gdx.graphics.getHeight();
        scale = width / dolphin.getWidth();

        dolphin.setScale(scale, scale);
        dolphin.setPosition((width / 2.0f) - (dolphin.getWidth() / 2.0f), (height / 2.0f) - (dolphin.getHeight() / 2.0f));

        setupTween();
    }

    private void setupTween() {
        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(4);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(SpriteObject.class, new SpriteObjectAccessor());
        Timeline.createParallel()
                .push(Tween.to(dolphin, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f))
                .push(Tween.to(dolphin, SpriteAccessor.COLOR, 0.8f).target(0.0f, 0.0f, 0.0f, 0.0f).ease(TweenEquations.easeInOutQuad).delay(1.2f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        endScreen();
                    }
                }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Color c = dolphin.getColor();
        Gdx.gl.glClearColor(c.r, c.g, c.b, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batcher.begin();
        dolphin.draw(batcher);
        batcher.end();

        if (Gdx.input.justTouched()) {
            endScreen();
        }
    }

    private void endScreen() {
        tweenManager.killAll();
        game.setScreen(new LoadScreen(game, tweenManager, batcher, width, height, scale));
    }

    @Override
    public void show() {
        // Do nothing.
    }

    @Override
    public void resize(int width, int height) {
        // Do nothing.
    }

    @Override
    public void pause() {
        // Do nothing.
    }

    @Override
    public void resume() {
        // Do nothing.
    }

    @Override
    public void hide() {
        // Do nothing.
    }

    @Override
    public void dispose() {
        // Do nothing.
    }
}
