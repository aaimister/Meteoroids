package com.fatdolphingames.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.helpers.AssetLoader;
import com.fatdolphingames.meteoroids.MGame;

public class SplashScreen implements Screen {

    private MGame game;
    private SpriteBatch batcher;
    private Sprite sprite;
    private TweenManager tweenManager;

    private float width;
    private float height;
    private float scale;

    public SplashScreen(MGame game) {
        this.game = game;
        batcher = new SpriteBatch();
        sprite = new Sprite(AssetLoader.dolphin);
        sprite.setColor(1.0f, 1.0f, 1.0f, 0.0f);

        width = (float) Gdx.graphics.getWidth();
        height = (float) Gdx.graphics.getHeight();
        scale = width / sprite.getWidth();

        sprite.setScale(scale, scale);
        sprite.setPosition((width / 2.0f) - (sprite.getWidth() / 2.0f), (height / 2.0f) - (sprite.getHeight() / 2.0f));

        setupTween();
    }

    private void setupTween() {
        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(4);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.to(sprite, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                endScreen();
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batcher.begin();
        sprite.draw(batcher);
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
