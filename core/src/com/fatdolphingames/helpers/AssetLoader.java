package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture dolphin;

    public static TextureRegion blank;

    public static BitmapFont font;
    public static BitmapFont outline;

    public static void start() {
        dolphin = new Texture(Gdx.files.internal("data/FatDolphinGames.png"));
        dolphin.setFilter(TextureFilter.Linear, Texture.TextureFilter.Linear);

        blank = new TextureRegion(dolphin, 0, 0, 0, 0);

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        outline = new BitmapFont(Gdx.files.internal("data/outline.fnt"));

    }

    public static Color getColor(float r, float g, float b, float a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

    public static void dispose() {
        dolphin.dispose();
    }

}
