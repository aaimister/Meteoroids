package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.fatdolphingames.gameobjects.Meteor;

import java.util.ArrayList;
import java.util.Random;

public class ScreenBox {

    private Random rand;
    private Box[] boxes;

    private float gameWidth;
    private float gameHeight;
    private float widthSpaceOffset;
    private float heightSpaceOffset;

    private int widthBoxCount;
    private int widthBoxSize;
    private int midPointY;
    private int heightBoxCount;
    private int heightBoxSize;

    public ScreenBox(float gameWidth, float gameHeight, int boxSize) {
        rand = new Random();
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        midPointY = Math.round(gameHeight / 2);
        widthBoxCount = (int) Math.floor(gameWidth / boxSize);
        widthBoxSize = (int) Math.floor(gameWidth / widthBoxCount);
        widthSpaceOffset = (gameWidth - (widthBoxSize * widthBoxCount)) / (widthBoxCount - 1);

//        heightBoxCount = (int) Math.floor(midPointY / boxSize);
//        heightBoxSize = (int) Math.floor(gameHeight / (heightBoxCount * 2));
//        heightSpaceOffset = (gameHeight - (heightBoxSize * heightBoxCount * 2)) / (heightBoxCount * 2 - 1);

        boxes = new Box[widthBoxCount];
        for (int i = 0; i < widthBoxCount; i++) {
            boxes[i] = new Box(0 + ((widthBoxSize + widthSpaceOffset) * i), 0, widthBoxSize, midPointY);
        }
    }

    public void updateBoxes(Meteor[] meteors) {
        ArrayList<Box> clear = new ArrayList<Box>(widthBoxCount);
        for (Box b : boxes) {
            b.clear();
            clear.add(b);
        }
        for (int i = 0; i < meteors.length && clear.size() > 0; i++) {
            if (!meteors[i].isOffScreen() && meteors[i].getY() <= midPointY) {
                Box remove = null;
                for (Box b : clear) {
                    if (b.contains(meteors[i].getX())) {
                        remove = b;
                        break;
                    }
                }
                if (remove != null) {
                    clear.remove(remove);
                }
            }
        }
    }

    public float getRandomX(float width) {
        ArrayList<Integer> open = getOpenColumns();
        if (open.size() <= 1) {
            return -1;
        }
        return boxes[open.get(rand.nextInt(open.size()))].x + ((widthBoxSize - width) / 2.0f);
    }

    private ArrayList<Integer> getOpenColumns() {
        ArrayList<Integer> open = new ArrayList<Integer>();
        for (int i = 0; i < widthBoxCount; i++) {
            if (boxes[i].isClear()) {
                open.add(i);
            }
        }
        return open;
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        for (Box b : boxes) {
            b.draw(batcher, shapeRenderer, font, outline, runTime);
        }
    }

    private class Box {

        private Rectangle bounds;
        private Color green;
        private Color red;

        private boolean clear;

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

        public boolean contains(float x) {
            clear = !(x >= this.x && x <= this.x + width);
            return !clear;
        }

        public void clear() {
            clear = true;
        }

        public boolean isClear() {
            return clear;
        }

        public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(clear ? green : red);
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
