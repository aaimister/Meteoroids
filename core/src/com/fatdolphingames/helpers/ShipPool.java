package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipPool {

    public static Texture shipPool;

    public static TextureRegion ship;
    public static TextureRegion[] thrusters;
    public static TextureRegion[] explosion;

    public static Animation thrusterAnimation;
    public static Animation explosionAnimation;

    public static void start() {
        shipPool = new Texture(Gdx.files.internal("data/shippool.png"));
        shipPool.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        ship = new TextureRegion(shipPool, 0, 0, 24, 23);
        ship.flip(false, true);

        thrusters = new TextureRegion[] { new TextureRegion(shipPool, 50, 0, 24, 23), new TextureRegion(shipPool, 100, 0, 24, 23), new TextureRegion(shipPool, 150, 0, 24, 23), new TextureRegion(shipPool, 200, 0, 24, 23) };
        AssetLoader.flip(thrusters, false, true);

        thrusterAnimation = new Animation(0.1f, thrusters);
        thrusterAnimation.setPlayMode(Animation.PlayMode.LOOP);

        explosion = new TextureRegion[] { new TextureRegion(shipPool, 250, 0, 24, 23), new TextureRegion(shipPool, 300, 0, 24, 23), new TextureRegion(shipPool, 350, 0, 24, 23), new TextureRegion(shipPool, 350, 0, 24, 23),
                new TextureRegion(shipPool, 400, 0, 24, 23), new TextureRegion(shipPool, 450, 0, 24, 23), new TextureRegion(shipPool, 500, 0, 24, 23), new TextureRegion(shipPool, 550, 0, 24, 23) };
        AssetLoader.flip(explosion, false, true);

        explosionAnimation = new Animation(0.1f, explosion);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public static void dispose() {
        shipPool.dispose();
    }

}
