package com.fatdolphingames.gameobjects.menu;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameobjects.Score;
import com.fatdolphingames.gameobjects.ScreenText;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Menu extends SpriteObject {

    private Vector2[] startPosition;
    private Vector2 endPosition;

    private ScreenText swipeText;
    private static MenuButton[] buttons;

    private boolean open;

    private float gameWidth;
    private float gameHeight;
    private float scaleY;

    public Menu(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        scaleY = 180.0f / height;
        gameWidth = world.getGameWidth();
        gameHeight = world.getGameHeight();
        endPosition = new Vector2(12.0f, 27.0f);
        startPosition = new Vector2[] { new Vector2(-width - 1, endPosition.y), new Vector2(endPosition.x, -height - 1), new Vector2(gameWidth + 1, endPosition.y), new Vector2(endPosition.x, gameHeight + 1) };
        swipeText = new ScreenText(world, 44.0f, 10.0f, 0, 0, "Swipe To Close", 0.20f);
        buttons = new MenuButton[] { new AdButton(world, x, y, 12, 12, 20.0f, 127.0f / scaleY), new VolumeButton(world, x, y, 12, 12, 20.0f, 110.0f / scaleY),
            new RexButton(world, x, y, 10, 10, 10.0f, 165.0f / scaleY), new MedalButton(world, x, y, 30, 30, 42.0f, 23.2f / scaleY),
            new CharacterButton(world, x, y, 22, 22, 77.0f, 108.3f / scaleY), new ColorButton(world, x, y, 10, 10, 83.0f, 137.3f / scaleY)};
    }

    @Override
    public void update(float delta) {
        // Do nothing.
    }

    @Override
    public void reset() {
        // Do nothing.
    }

    @Override
    public void touchDown(float screenX, float screenY, int pointer) {
        for (MenuButton mb : buttons) {
            mb.touchDown(screenX, screenY, pointer);
        }
    }

    @Override
    public void touchUp(float screenX, float screenY, int pointer) {
        // Do nothing.
    }

    public void openMenu(float screenX, float screenY, float startX, float startY, float leftRight, float upDown) {
        if (leftRight >= 25.0f || upDown >= 25.0f) {
            int direction;
            if (leftRight > upDown) {
                direction = screenX > startX ? 0 : 2;
            } else {
                direction = screenY > startY ? 1 : 3;
            }
            swipeText.toggle();
            Vector2 start = open ? startPosition[direction < 2 ? direction + 2 : direction - 2] : startPosition[direction];
            tweenManager.killTarget(this);
            if (open) {
                open = false;
                for (MenuButton mb : buttons) {
                    mb.moveButton(null, start);
                }
                Tween.to(this, SpriteAccessor.POSITION, 0.5f).target(start.x, start.y).ease(TweenEquations.easeInOutQuad).start(tweenManager);
            } else {
                setPosition(start.x, start.y);
                world.hideRetryText();
                open = true;
                for (MenuButton mb : buttons) {
                    mb.moveButton(start, endPosition);
                }
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

    public static void save() {
        for (MenuButton mb : buttons) {
            mb.save();
        }
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
        outline.draw(batcher, "Medal", getX(), getY() + 7.0f / scaleY, getWidth(), Align.center, false);
        font.setColor(AssetLoader.WHITE);
        font.draw(batcher, "Medal", getX(), getY() + 7.0f / scaleY, getWidth(), Align.center, false);

        AssetLoader.setFontScale(0.5f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        outline.draw(batcher, "Score", getX() + 5.0f, getY() + 65.0f / scaleY);
        font.draw(batcher, "Score", getX() + 5.0f, getY() + 65.0f / scaleY);
        outline.draw(batcher, "Best", getX() + 70.0f, getY() + 65.0f / scaleY);
        font.draw(batcher, "Best", getX() + 70.0f, getY() + 65.0f / scaleY);
        outline.draw(batcher, "" + world.getScore(), getX() + 5.0f, getY() + 82.0f / scaleY);
        font.draw(batcher, "" + world.getScore(), getX() + 5.0f, getY() + 82.0f / scaleY);

        if (world.newBestScore()) {
            AssetLoader.setFontScale(0.18f);
            font.setColor(AssetLoader.RED);
            font.draw(batcher, "new!", getX() + 54.0f, getY() + 87.0f / scaleY);
        }

        AssetLoader.setFontScale(0.5f);
        outline.draw(batcher, "" + world.getBestScore(), getX() + 70.0f, getY() + 82.0f / scaleY);
        font.setColor(AssetLoader.WHITE);
        font.draw(batcher, "" + world.getBestScore(), getX() + 70.0f, getY() + 82.0f / scaleY);

//        if (characterButton.isEnabled()) {
            AssetLoader.setFontScale(0.12f);
            outline.draw(batcher, "Character Name Here", getX() + 75.0f, getY() + 101.7f / scaleY, 26.0f, Align.center, false);
            font.draw(batcher, "Character Name Here", getX() + 75.0f, getY() + 101.7f / scaleY, 26.0f, Align.center, false);
//        }

        batcher.setColor(Color.WHITE);

        AssetLoader.setFontScale(0.18f);
        font.draw(batcher, "Also Try", getX() + 3.0f, getY() + 157.0f / scaleY);
        font.draw(batcher, "Powered By", getX() + getWidth() - 38.0f, getY() + 157.0f / scaleY);
        batcher.draw(AssetLoader.libGDX, getX() + getWidth() - 32.0f, getY() + 165.0f / scaleY, 0.0f, 0.0f, AssetLoader.libGDX.getRegionWidth(), AssetLoader.libGDX.getRegionHeight(), -0.2f, -0.2f, 180.0f);
        batcher.draw(AssetLoader.java, getX() + getWidth() - 20.0f, getY() + 165.0f / scaleY, 0.0f, 0.0f, AssetLoader.java.getRegionWidth(), AssetLoader.java.getRegionHeight(), -0.2f, -0.2f, 180.0f);
        batcher.end();

        for (MenuButton mb : buttons) {
            mb.draw(batcher, shapeRenderer, font, outline, runTime);
        }
    }
}
