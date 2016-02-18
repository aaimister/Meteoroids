package com.fatdolphingames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetLoader {

    public static Texture dolphin;

    public static void start() {
        dolphin = new Texture(Gdx.files.internal("android/assets/data/FatDolphinGames.png"));
        dolphin.setFilter(TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void dispose() {
        dolphin.dispose();
    }

}
