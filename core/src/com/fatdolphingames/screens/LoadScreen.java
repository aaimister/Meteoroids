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

public class LoadScreen implements Screen {

    private MGame game;
    private SpriteBatch batcher;
    private TweenManager tweenManager;
    private Sprite meteoroids;
    private Sprite meteor01;
    private Sprite o01;
    private Sprite meteor02;
    private Sprite o02;

    private float meteorY;
    private float oY;

    public LoadScreen(MGame game, TweenManager tweenManager, SpriteBatch batcher, float width, float height, float scale) {
        this.game = game;
        this.tweenManager = tweenManager;
        this.batcher = batcher;

        meteoroids = new Sprite(AssetLoader.loadingScreen[0]);
        meteoroids.setAlpha(0.0f);
        meteoroids.setScale(scale, scale);
        meteoroids.setPosition((width / 2.0f) - (meteoroids.getWidth() / 2.0f), (height / 2.0f) - (meteoroids.getHeight() / 2.0f));

        meteor01 = new Sprite(AssetLoader.loadingScreen[1]);
        meteor01.setColor(AssetLoader.BLACK);
        meteor01.setScale(scale, scale);
        meteorY = (height / 2.0f) - (meteor01.getHeight() / 2.0f) - (9 * scale);
        meteor01.setPosition((width / 2.0f) - (meteor01.getWidth() / 2.0f) - (14 * scale), height + meteor01.getHeight());

        o01 = new Sprite(AssetLoader.loadingScreen[2]);
        o01.setColor(AssetLoader.BLACK);
        o01.setAlpha(0.0f);
        o01.setScale(scale, scale);
        oY = (height / 2.0f) - (o01.getHeight() / 2.0f) - (9 * scale);
        o01.setPosition((width / 2.0f) - (o01.getWidth() / 2.0f) - (13 * scale), oY);

        meteor02 = new Sprite(AssetLoader.loadingScreen[1]);
        meteor02.setColor(AssetLoader.BLACK);
        meteor02.setScale(scale, scale);
        meteor02.setPosition((width / 2.0f) - (meteor02.getWidth() / 2.0f) + (88 * scale), height + meteor02.getHeight());

        o02 = new Sprite(AssetLoader.loadingScreen[2]);
        o02.setColor(AssetLoader.BLACK);
        o02.setAlpha(0.0f);
        o02.setScale(scale, scale);
        o02.setPosition((width / 2.0f) - (o01.getWidth() / 2.0f) + (87 * scale), oY);

        setupTween();
    }

    private void setupTween() {
        Timeline.createSequence()
                .push(Timeline.createParallel()
                    .push(Tween.to(meteor01, SpriteAccessor.POSITION, 1.2f).target(meteor01.getX(), meteorY).ease(TweenEquations.easeNone))
                    .push(Tween.to(meteor01, SpriteAccessor.ROTATION, 1.2f).target(360).ease(TweenEquations.easeNone))
                    .push(Tween.to(meteor02, SpriteAccessor.POSITION, 0.8f).target(meteor02.getX(), meteorY).ease(TweenEquations.easeNone).delay(0.4f))
                    .push(Tween.to(meteor02, SpriteAccessor.ROTATION, 0.8f).target(-360).ease(TweenEquations.easeNone).delay(0.4f)))
                .push(Timeline.createParallel()
                    .push(Tween.to(meteoroids, SpriteAccessor.ALPHA, 0.8f).target(1.0f).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.6f))
                    .push(Tween.to(meteor01, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(o01, SpriteAccessor.COLOR, 0.8f).target(1.0f, 1.0f, 1.0f, 1.0f).ease(TweenEquations.easeInExpo))
                    .push(Tween.to(meteor02, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad))
                    .push(Tween.to(o02, SpriteAccessor.COLOR, 0.8f).target(1.0f, 1.0f, 1.0f, 1.0f).ease(TweenEquations.easeInExpo))
                    .push(Tween.to(o01, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad).delay(1.4f))
                    .push(Tween.to(o02, SpriteAccessor.ALPHA, 0.8f).target(0.0f).ease(TweenEquations.easeInOutQuad).delay(1.4f)))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        endScreen();
                    }
                })
                .start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batcher.begin();
        meteoroids.draw(batcher);
        meteor01.draw(batcher);
        o01.draw(batcher);
        meteor02.draw(batcher);
        o02.draw(batcher);
        batcher.end();

        if (Gdx.input.justTouched()) {
            endScreen();
        }
    }

    private void endScreen() {
        tweenManager.killAll();
        game.setScreen(new TutorialScreen(game, tweenManager));
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
