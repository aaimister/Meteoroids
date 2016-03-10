package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

public class Score extends SpriteObject {

    private boolean newBest;
    private boolean playSound;

    private static int bestScore;
    private int score;

    public Score(GameWorld world, float x, float y, int width, int height) {
        super(world, x, y, width, height);
        playSound = true;
        bestScore = AssetLoader.prefs.getInteger("bestScore");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {
        score = 0;
        newBest = false;
        playSound = true;
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

    public void addScore() {
        score++;
        if ((newBest = score > bestScore)) {
            bestScore = score;
            if (playSound) {
                playSound = false;
                AssetLoader.highScore.play(AssetLoader.volume);
            }
        }
    }

    public boolean newBest() {
        return newBest;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public static void save() {
        AssetLoader.prefs.putInteger("bestScore", bestScore);
    }

    @Override
    public void drawBatcher(SpriteBatch batcher, float runTime) {
        if (newBest) {
            batcher.setColor(Color.WHITE);
            batcher.draw(AssetLoader.crown, getX() - 1.5f, getY() - 6.0f);
        }
        AssetLoader.setFontScale(0.65f);
        AssetLoader.outline.setColor(AssetLoader.BLACK);
        AssetLoader.outline.draw(batcher, "" + score, getX(), getY());
        AssetLoader.font.setColor(AssetLoader.WHITE);
        AssetLoader.font.draw(batcher, "" + score, getX(), getY());
    }

    @Override
    public void drawShapeRenderer(ShapeRenderer shapeRenderer, float runTime) {
        // Do nothing.
    }
}
