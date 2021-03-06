package com.fatdolphingames.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.fatdolphingames.gameobjects.Meteor;

import java.util.ArrayList;
import java.util.Random;

public class ScreenBox {

    private Random rand;
    private Box[] boxes;
    private Meteor[] inColumn;

    private int widthBoxCount;
    private int widthBoxSize;
    private int midPointY;
    private int nextColumn;
    private int previousColumn;

    public ScreenBox(float gameWidth, float gameHeight, int boxSize) {
        rand = new Random();
        midPointY = Math.round(gameHeight / 2);
        widthBoxCount = (int) Math.floor(gameWidth / boxSize);
        widthBoxSize = (int) Math.floor(gameWidth / widthBoxCount);
        float widthSpaceOffset = (gameWidth - (widthBoxSize * widthBoxCount)) / (widthBoxCount - 1);

        previousColumn = nextColumn = (widthBoxCount - 1) / 2;
        inColumn = new Meteor[widthBoxCount];
        boxes = new Box[widthBoxCount];
        for (int i = 0; i < widthBoxCount; i++) {
            boxes[i] = new Box(0 + ((widthBoxSize + widthSpaceOffset) * i), 0, widthBoxSize, midPointY);
        }
    }

    public float fireMeteor(Meteor m) {
        ArrayList<Integer> open = getOpenColumns();
        if (open.size() == 0) {
            return -1;
        }
        if (boxes[nextColumn].isClear()) {
            previousColumn = nextColumn;
            nextColumn = getNextColumn();
        }
        int fireColumn = open.get(rand.nextInt(open.size()));
        inColumn[fireColumn] = m;
        boxes[fireColumn].setClear(false);
        return boxes[fireColumn].x + (rand.nextInt((int) (widthBoxSize - m.getWidth())));
    }

    private int getNextColumn() {
        int next = rand.nextInt(2);
        if (next == 0) {
            return nextColumn;
        }
        return (nextColumn += rand.nextBoolean() ? (nextColumn - 1 >= 0 ? -1 : 1) : (nextColumn + 1 < widthBoxCount ? 1 : -1));
    }

    private ArrayList<Integer> getOpenColumns() {
        ArrayList<Integer> open = new ArrayList<Integer>();
        for (int i = 0; i < widthBoxCount; i++) {
            Meteor m = inColumn[i];
            if (m == null || m.isOffScreen() || m.getY() > midPointY) {
                inColumn[i] = null;
                boxes[i].setClear(true);
                if (i != nextColumn && i != previousColumn) {
                    open.add(i);
                }
            }
        }
        return open;
    }

    private class Box {

        private Rectangle bounds;
        private Color green;
        private Color red;
        private Color orange;

        private boolean clear;

        private float x;
        private float y;

        private int width;
        private int height;

        public Box(float x, float y, int width, int height) {
            bounds = new Rectangle(x, y, width, height);
            green = new Color(0.0f, 1.0f, 0.0f, 0.2f);
            red = new Color(1.0f, 0.0f, 0.0f, 0.2f);
            orange = new Color(1.0f, 0.5f, 0.0f, 0.2f);
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void setClear(boolean clear) {
            this.clear = clear;
        }

        public boolean isClear() {
            return clear;
        }

    }

}
