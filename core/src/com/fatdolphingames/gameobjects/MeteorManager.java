package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.ScreenBox;

public class MeteorManager {

    private GameWorld world;
    private ScreenBox screenBox;
    private Meteor[] meteors;

    private boolean pause;

    private long resetTimer;
    private long resetTime;

    private float gameWidth;

    public MeteorManager(GameWorld world, int meteorCount) {
        this.world = world;
        meteors = new Meteor[meteorCount];
        gameWidth = world.getGameWidth();
        screenBox = new ScreenBox(gameWidth, world.getGameHeight(), 28);

        for (int i = 0; i < meteorCount; i++) {
            int size = i % 3 == 0 ? 28 : i % 3 == 1 ? 20 : 8;
            meteors[i] = new Meteor(world, 0, -28, size, size);
        }

        pause = true;

        resetTime = 3000;
        resetTimer = System.currentTimeMillis() + resetTime;
    }

    public void update(float delta) {
        if (!pause && System.currentTimeMillis() >= resetTimer) {
            world.checkShipCollisions(meteors);
            for (Meteor m : meteors) {
                if (m.isOffScreen()) {
                    fireMeteor(m);
                }
            }
        }
    }

    public void reset() {
        pause = false;
        for (Meteor m : meteors) {
            m.reset();
        }
        resetTimer = System.currentTimeMillis() + resetTime;
    }

    public void fireMeteor(Meteor m) {
        float x = screenBox.fireMeteor(m);
        if (x != -1) {
            m.fire(x);
        }
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        screenBox.draw(batcher, shapeRenderer, font, outline, runTime);
        for (Meteor m : meteors) {
            m.draw(batcher, shapeRenderer, font, outline, runTime);
        }
    }

}
