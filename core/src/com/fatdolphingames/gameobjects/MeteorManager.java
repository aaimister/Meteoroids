package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.ScreenBox;

public class MeteorManager {

    private GameWorld world;
    private ScreenBox screenBox;
    private Meteor[] meteors;

    private boolean pause;

    private long resetTimer;
    private long resetTime;

    public MeteorManager(GameWorld world, int meteorCount) {
        this.world = world;
        meteors = new Meteor[meteorCount];
        screenBox = new ScreenBox(world.getGameWidth(), world.getGameHeight(), 28);

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
            for (Meteor m : meteors) {
                if (m.isOffScreen()) {
                    fireMeteor(m);
                } else {
                    world.checkShipCollisions(m);
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

    public void drawBatcher(SpriteBatch batcher, float runTime) {
        for (Meteor m : meteors) {
            m.drawBatcher(batcher, runTime);
        }
    }

}
