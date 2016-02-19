package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture dolphin;
    public static Texture texture;

    public static TextureRegion blank;
    public static TextureRegion ship;
    public static TextureRegion[] stars;
    public static TextureRegion[][] meteoroids;

    public static BitmapFont font;
    public static BitmapFont outline;

    public static void start() {
        dolphin = new Texture(Gdx.files.internal("data/FatDolphinGames.png"));
        dolphin.setFilter(TextureFilter.Linear, Texture.TextureFilter.Linear);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        blank = new TextureRegion(dolphin, 0, 0, 0, 0);

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        outline = new BitmapFont(Gdx.files.internal("data/outline.fnt"));

        stars = new TextureRegion[] { new TextureRegion(texture, 138, 46, 5, 5), new TextureRegion(texture, 144, 46, 3, 3), new TextureRegion(texture, 148, 46, 1, 1) };

        meteoroids = new TextureRegion[][] { { new TextureRegion(texture, 0, 0, 28, 28), new TextureRegion(texture, 29, 0, 28, 28), new TextureRegion(texture, 58, 0, 28, 28), new TextureRegion(texture, 87, 0, 28, 28) },
                { new TextureRegion(texture, 0, 58, 20, 20), new TextureRegion(texture, 21, 58, 20, 20), new TextureRegion(texture, 42, 58, 20, 20), new TextureRegion(texture, 63, 58, 20, 20) },
                { new TextureRegion(texture, 0, 79, 8, 8), new TextureRegion(texture, 9, 79, 8, 8), new TextureRegion(texture, 18, 79, 8, 8), new TextureRegion(texture, 27, 79, 8, 8) } };

    }

    public static Color getColor(float r, float g, float b, float a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

    public static void dispose() {
        dolphin.dispose();
    }

}
