package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    private static ShipPool shipPool;
    private static GlyphLayout layout;

    public static Texture dolphin;
    public static Texture[] loadingScreen;
    public static Texture texture;

    public static TextureRegion[] stars;
    public static TextureRegion[][] meteoroids;
    public static TextureRegion chargeBar;
    public static TextureRegion chargeBarSquare[];
    public static TextureRegion crown;
    public static TextureRegion tapRex;
    public static TextureRegion libGDX;
    public static TextureRegion java;
    public static TextureRegion ads[];
    public static TextureRegion volume[];

    public static BitmapFont font;
    public static BitmapFont outline;

    public static final Color BLACK = getColor(46.0f, 46.0f, 46.0f, 1.0f);
    public static final Color WHITE = getColor(251.0f, 244.0f, 238.0f, 1.0f);

    public static void start() {
        shipPool = new ShipPool();
        layout = new GlyphLayout();

        dolphin = new Texture(Gdx.files.internal("data/FatDolphinGames.png"));
        dolphin.setFilter(TextureFilter.Linear, Texture.TextureFilter.Linear);

        loadingScreen = new Texture[] { new Texture(Gdx.files.internal("data/Meteoroids.png")), new Texture(Gdx.files.internal("data/Meteor.png")),
            new Texture(Gdx.files.internal("data/o.png")) };

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        outline = new BitmapFont(Gdx.files.internal("data/outline.fnt"));

        stars = new TextureRegion[] { new TextureRegion(texture, 138, 46, 5, 5), new TextureRegion(texture, 144, 46, 3, 3), new TextureRegion(texture, 148, 46, 1, 1) };

        meteoroids = new TextureRegion[][] { { new TextureRegion(texture, 0, 0, 28, 28), new TextureRegion(texture, 29, 0, 28, 28), new TextureRegion(texture, 58, 0, 28, 28), new TextureRegion(texture, 87, 0, 28, 28) },
                { new TextureRegion(texture, 0, 58, 20, 20), new TextureRegion(texture, 21, 58, 20, 20), new TextureRegion(texture, 42, 58, 20, 20), new TextureRegion(texture, 63, 58, 20, 20) },
                { new TextureRegion(texture, 0, 79, 8, 8), new TextureRegion(texture, 9, 79, 8, 8), new TextureRegion(texture, 18, 79, 8, 8), new TextureRegion(texture, 27, 79, 8, 8) } };

        chargeBar = new TextureRegion(texture, 84, 63, 24, 4);
        chargeBarSquare = new TextureRegion[] { new TextureRegion(texture, 84, 58, 4, 4), new TextureRegion(texture, 89, 58, 4, 4) };

        crown = new TextureRegion(texture, 162, 29, 13, 13);
        crown.flip(false, true);

        tapRex = new TextureRegion(texture, 110, 208, 48, 48);
        tapRex.flip(false, true);

        libGDX = new TextureRegion(texture, 159, 208, 48, 48);
        libGDX.flip(false, true);

        java = new TextureRegion(texture, 208, 208, 48, 48);
        java.flip(false, true);

        ads = new TextureRegion[] { new TextureRegion(texture, 116, 0, 22, 22), new TextureRegion(texture, 139, 0, 22, 22) };
        flip(ads, false, true);

        volume = new TextureRegion[] { new TextureRegion(texture, 116, 23, 22, 22), new TextureRegion(texture, 139, 23, 22, 22) };
        flip(volume, false, true);
    }

    public static TextureRegion ship() {
        return shipPool.ship;
    }

    public static Animation thrusters() {
        return shipPool.thrusterAnimation;
    }

    public static Animation explosion() {
        return shipPool.explosionAnimation;
    }

    public static float calculateFontWidth(String text, float scaleX) {
        if (font.getScaleX() != scaleX) {
            setFontScale(scaleX);
        }
        layout.setText(font, text);
        return layout.width;
    }

    public static float calculateFontHeight(String text, float scaleY) {
        if (font.getScaleY() != scaleY) {
            setFontScale(scaleY);
        }
        layout.setText(font, text);
        return layout.height;
    }

    public static void setFontScale(float scale) {
        font.getData().setScale(scale, -scale);
        outline.getData().setScale(scale, -scale);
    }

    public static Color getColor(float r, float g, float b, float a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

    public static void flip(TextureRegion[] texture, boolean x, boolean y) {
        for (TextureRegion tr : texture) {
            tr.flip(x, y);
        }
    }

    public static void dispose() {
        shipPool.dispose();
        dolphin.dispose();
        for (Texture t : loadingScreen)
            t.dispose();
        texture.dispose();
        font.dispose();
        outline.dispose();
    }

}
