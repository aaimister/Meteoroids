package com.fatdolphingames.gameobjects.menu;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameobjects.ScreenText;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Menu extends SpriteObject {

    private Vector2[] startPosition;
    private Vector2 endPosition;
    private Color red;

    private ScreenText swipeText;

    private boolean open;

    private float gameWidth;
    private float gameHeight;

    public Menu(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();
        red = AssetLoader.getColor(166.0f, 45.0f, 48.0f, 1.0f);
        endPosition = new Vector2(12.0f, 27.0f);
        startPosition = new Vector2[] { new Vector2(-width - 1, endPosition.y), new Vector2(endPosition.x, -height - 1), new Vector2(gameWidth + 1, endPosition.y), new Vector2(endPosition.x, gameHeight + 1) };
        swipeText = new ScreenText(world, 44.0f, 10.0f, 0, 0, "Swipe To Close", 0.20f);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {

    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
//        float leftRight = Math.abs(screenX - startX);
//        float upDown = Math.abs(screenY - startY);
//
//        if (leftRight >= 50.0f || upDown >= 50.0f) {
//            if (leftRight > upDown) {
//                world.moveMenu(screenX > startX ? direction.LEFT : direction.RIGHT);
//            } else {
//                world.moveMenu(screenY < startY ? direction.UP : direction.DOWN);
//            }
//        }
        float leftRight = Math.abs(screenX - world.getStartX());
        float upDown = Math.abs(screenY - world.getStartY());
        if (leftRight >= 25.0f || upDown >= 25.f) {
            int direction;
            if (leftRight > upDown) {
                direction = screenX > world.getStartX() ? 0 : 2;
            } else {
                direction = screenY > world.getStartY() ? 1 : 3;
            }
            world.toggleRetryText();
            swipeText.toggle();
            Vector2 start = open ? startPosition[direction < 2 ? direction + 2 : direction - 2] : startPosition[direction];
            tweenManager.killTarget(this);
            if (open) {
                open = false;
                Tween.to(this, SpriteAccessor.POSITION, 0.5f).target(start.x, start.y).ease(TweenEquations.easeInOutQuad).start(tweenManager);
            } else {
                setPosition(start.x, start.y);
                open = true;
                Tween.to(this, SpriteAccessor.POSITION, 0.5f).target(endPosition.x, endPosition.y).ease(TweenEquations.easeInOutQuad).start(tweenManager);
            }
        }
    }

    @Override
    public void collidedWith(SpriteObject so) {
        // Do nothing.
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        swipeText.draw(batcher, shapeRenderer, font, outline, runTime);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeType.Filled);
        Color c = AssetLoader.BLACK;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.8f);
        shapeRenderer.rect(getX() + 5.0f, getY() + 5.0f, getWidth() - 10.0f, getHeight() - 10.0f);
        shapeRenderer.rect(getX() + 5.0f, getY(), getWidth() - 10.0f, 5.0f);
        shapeRenderer.rect(getX() + getWidth() - 5.0f, getY() + 5.0f, 5.0f, getHeight() - 10.0f);
        shapeRenderer.rect(getX() + 5.0f, getY() + getHeight() - 5.0f, getWidth() - 10.0f, 5.0f);
        shapeRenderer.rect(getX(), getY() + 5.0f, 5.0f, getHeight() - 10.0f);
        shapeRenderer.arc(getX() + 5.0f, getY() + 5.0f, 5.0f, 180.0f, 90.0f, 10);
        shapeRenderer.arc(getX() + 5.0f, getY() + getHeight() - 5.0f, 5.0f, 90.0f, 90.0f, 10);
        shapeRenderer.arc(getX() + getWidth() - 5.0f, getY() + getHeight() - 5.0f, 5.0f, 0.0f, 90.0f, 10);
        shapeRenderer.arc(getX() + getWidth() - 5.0f, getY() + 5.0f, 5.0f, 270.0f, 90.0f, 10);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batcher.begin();
        AssetLoader.setFontScale(0.2f);
     //   outline.draw(batcher, medalButton.getText(), getX() + ((getWidth() - (medalButton.getText().length() * font.getSpaceWidth())) / 2.0f), getY() + 7.0f);
        font.setColor(AssetLoader.WHITE);
        font.draw(batcher, "Medal", getX() + (getWidth() - AssetLoader.calculateFontWidth("Medal", 0.2f)) / 2.0f, getY() + 7.0f);

        AssetLoader.setFontScale(0.5f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        outline.draw(batcher, "Score", getX() + 5.0f, getY() + 65.0f);
        font.draw(batcher, "Score", getX() + 5.0f, getY() + 65.0f);
        outline.draw(batcher, "Best", getX() + 70.0f, getY() + 65.0f);
        font.draw(batcher, "Best", getX() + 70.0f, getY() + 65.0f);
        outline.draw(batcher, "" + world.getScore(), getX() + 5.0f, getY() + 82.0f);
        font.draw(batcher, "" + world.getScore(), getX() + 5.0f, getY() + 82.0f);

        if (world.newBestScore()) {
            AssetLoader.setFontScale(0.18f);
            font.setColor(red);
            font.draw(batcher, "new!", getX() + 54.0f, getY() + 87.0f);
        }

        AssetLoader.setFontScale(0.5f);
        outline.draw(batcher, "" + world.getBestScore(), getX() + 70.0f, getY() + 82.0f);
        font.setColor(AssetLoader.WHITE);
        font.draw(batcher, "" + world.getBestScore(), getX() + 70.0f, getY() + 82.0f);

//        if (characterButton.isEnabled()) {
//            AssetLoader.setFontScale(0.12f);
//            outline.draw(batcher, characterButton.getText(), getX() + getWidth() - 24.0f - (characterButton.getText().length() / 2 * font.getSpaceWidth()), getY() + 105.0f - Math.abs(font.getCapHeight()));
//            font.draw(batcher, characterButton.getText(), getX() + getWidth() - 24.0f - (characterButton.getText().length() / 2 * font.getSpaceWidth()), getY() + 105.0f - Math.abs(font.getCapHeight()));
//        }

        batcher.setColor(Color.WHITE);

        AssetLoader.setFontScale(0.18f);
        font.draw(batcher, "Also Try", getX() + 3.0f, getY() + getHeight() - 23.0f);
        font.draw(batcher, "Powered By", getX() + getWidth() - 38.0f, getY() + getHeight() - 23.0f);
        batcher.draw(AssetLoader.libGDX, getX() + getWidth() - 32.0f, getY() + getHeight() - 15.0f, 0.0f, 0.0f, AssetLoader.libGDX.getRegionWidth(), AssetLoader.libGDX.getRegionHeight(), -0.2f, -0.2f, 180.0f);
        batcher.draw(AssetLoader.java, getX() + getWidth() - 20.0f, getY() + getHeight() - 15.0f, 0.0f, 0.0f, AssetLoader.java.getRegionWidth(), AssetLoader.java.getRegionHeight(), -0.2f, -0.2f, 180.0f);

        batcher.end();
    }
}
