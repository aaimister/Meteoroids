package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatdolphingames.gameworld.GameWorld;

public class StarManager {

    private GameWorld world;
    private Star[] stars;

    public StarManager(GameWorld world) {
        this.world = world;
        stars = generateStars(50);
    }

    public void update(float delta) {
        for (Star s : stars) {
            s.update(delta);
        }
    }

    public void reset() {
        for (Star s : stars) {
            s.reset();
        }
    }

    public void touchDown(float screenX, float screenY, int pointer) {
        for (Star s : stars) {
            s.touchDown(screenX, screenY, pointer);
        }
    }

    public void touchUp(float screenX, float screenY, int pointer) {
        for (Star s : stars) {
            s.touchUp(screenX, screenY, pointer);
        }
    }

    public void drawBatcher(SpriteBatch batcher, float runTime) {
        for (Star s : stars) {
            s.drawBatcher(batcher, runTime);
        }
    }

    public Star[] getStars() {
        return stars;
    }

    private Star[] generateStars(int count) {
        Star[] stars = new Star[count];

        for (int i = 0; i < count; i++) {
            stars[i] = new Star(world, 0, 0, 0, 0);
        }

        return stars;
    }

}
