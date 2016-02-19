package com.fatdolphingames.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;

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

    public void onTouch(float touchX, float touchY) {
        for (Star s : stars) {
            s.onTouch(touchX, touchY);
        }
    }

    public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, BitmapFont outline, float runTime) {
        for (Star s : stars) {
            s.draw(batcher, shapeRenderer, font, outline, runTime);
        }
    }

    public Star[] getStars() {
        return stars;
    }

    private Star[] generateStars(int count) {
        Star[] stars = new Star[count];

        for (int i = 0; i < count; i++) {
            stars[i] = new Star(world, AssetLoader.dolphin, 0, 0, 5, 5);
        }

        return stars;
    }

}
