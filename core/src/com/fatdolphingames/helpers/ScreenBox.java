package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType ;
import com.badlogic.gdx.math.Rectangle;
import com.fatdolphingames.gameworld.GameWorld;

import java.util.Random;

public class ScreenBox {

    private Random rand;
    private Box[][] boxes;

    private float gameWidth;
    private float gameHeight;
    private float widthSpaceOffset;
    private float heightSpaceOffset;

    private int widthBoxCount;
    private int widthBoxSize;
    private int heightBoxCount;
    private int heightBoxSize;

    public ScreenBox(float gameWidth, float gameHeight, int boxSize) {
        rand = new Random();
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        widthBoxCount = (int) Math.floor(gameWidth / boxSize);
        widthBoxSize = (int) Math.floor(gameWidth / widthBoxCount);
        widthSpaceOffset = (gameWidth - (widthBoxSize * widthBoxCount)) / (widthBoxCount - 1);

        heightBoxCount = (int) Math.floor(gameHeight / boxSize);
        heightBoxSize = (int) Math.floor(gameHeight / heightBoxCount);
        heightSpaceOffset = (gameHeight - (heightBoxSize * heightBoxCount)) / (heightBoxCount - 1);

        boxes = new Box[widthBoxCount][heightBoxCount];
        for (int w = 0; w < widthBoxCount; w++) {
            for (int h = 0; h < heightBoxCount; h++) {
                boxes[w][h] = new Box(0 + ((widthBoxSize + widthSpaceOffset) * w), 0 + ((heightBoxSize + heightSpaceOffset) * h), widthBoxSize, heightBoxSize);
            }
        }
    }

    public float getRandomX(float width) {
        return boxes[rand.nextInt(widthBoxCount)][0].x + ((widthBoxSize - width) / 2.0f);
    }

    public void checkBoxes(Rectangle rec) {
        for (Box w[] : boxes) {
            for (Box h : w) {
                h.contains(rec);
            }
        }
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        for (Box w[] : boxes) {
            for (Box h : w) {
                h.draw(batcher, shapeRenderer, font, outline, runTime);
            }
        }
    }

    private class Box {

        private Rectangle bounds;
        private Color green;
        private Color red;

        private boolean contains;

        private float x;
        private float y;

        private int width;
        private int height;

        public Box(float x, float y, int width, int height) {
            bounds = new Rectangle(x, y, width, height);
            green = new Color(0.0f, 1.0f, 0.0f, 0.2f);
            red = new Color(1.0f, 0.0f, 0.0f, 0.2f);
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean contains(Rectangle rec) {
            return (contains = bounds.overlaps(rec));
        }

        public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(contains ? red : green);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 0.2f);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

    }

}
