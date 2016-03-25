package com.fatdolphingames.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Random;

public class ColorPool {

    private static Random rand;

    public static TextureRegion[] colorButton;
    private static Color current;
    private static Color[] characterColors;
    private static ArrayList<Color> locked;
    private static ArrayList<Color> unlocked;

    private static int selected;
    private static int nextUnlock;
    private static int maxUnlock;

    private static boolean show;

    public static void start() {
        rand = new Random();

        // Star Gazer
        characterColors = new Color[] { getColor(206, 40, 45) };

        // FireBrick, Coral, Orange Red, Dark Orange,
        // Gold, Golden Rod, Dark Khaki, Olive, Yellow
        // Green, Light Green, Sea Green, Light Sea Green, Dark Slate Gray
        // Dark Cyan, Aqua, Powder Blue, Deep Sky Blue, Light Sky Blue
        // Midnight Blue, Royal Blue, Blue Violet, Indigo, Medium Purple
        // Medium Orchid, Plum, Magenta/Fuchsia, Medium Violet Red, Hot Pink
        // Lawn Green, Octopus, Saddle Brown, Chocolate, Sandy Brown
        // Tan, Rosy Brown, Misty Rose, Mint Cream, Slate Gray
        // Medium Purple, Deep Sky Blue 4, Indian Red, SGI Slateblue, Dim Gray
        // Black, Pink Something, Blueish
        locked = new ArrayList<Color>();
        locked.add(getColor(178, 34, 34));   locked.add(getColor(255, 127, 80));  locked.add(getColor(255, 69, 0));    locked.add(getColor(255, 140, 0));
        locked.add(getColor(255, 215, 0));   locked.add(getColor(218, 165, 32));  locked.add(getColor(189, 183, 107)); locked.add(getColor(128, 128, 0));   locked.add(getColor(255, 255, 0));
        locked.add(getColor(0, 128, 0));     locked.add(getColor(144, 238, 144)); locked.add(getColor(32, 178, 170));  locked.add(getColor(47, 79, 79));
        locked.add(getColor(0, 139, 139));   locked.add(getColor(0, 255, 255));   locked.add(getColor(176, 224, 230)); locked.add(getColor(0, 191, 255));   locked.add(getColor(135, 206, 250));
        locked.add(getColor(25, 25, 112));   locked.add(getColor(65, 105, 225));  locked.add(getColor(138, 43, 226));  locked.add(getColor(75, 0, 130));    locked.add(getColor(147, 112, 219));
        locked.add(getColor(186, 85, 211));  locked.add(getColor(221, 160, 221)); locked.add(getColor(255, 0, 255));   locked.add(getColor(199, 21, 133));  locked.add(getColor(255, 105, 180));
        locked.add(getColor(124, 252, 0));   locked.add(getColor(139, 6, 56));    locked.add(getColor(139, 69, 19));   locked.add(getColor(210, 105, 30));  locked.add(getColor(244, 164, 96));
        locked.add(getColor(210, 180, 140)); locked.add(getColor(188, 143, 143)); locked.add(getColor(255, 228, 225)); locked.add(getColor(245, 255, 250)); locked.add(getColor(112, 128, 144));
        locked.add(getColor(0, 104, 139));   locked.add(getColor(113, 113, 198)); locked.add(getColor(105, 105, 105));
        locked.add(getColor(50, 50, 50));    locked.add(getColor(255, 153, 204)); locked.add(getColor(51, 51, 102));

        colorButton = new TextureRegion[] { new TextureRegion(AssetLoader.texture, 116, 46, 10, 10), new TextureRegion(AssetLoader.texture, 127, 46, 10, 10) };

        load();
    }

    public static void save() {
        AssetLoader.prefs.putString("currentColor", saveColor(current));
        AssetLoader.prefs.putInteger("unlockedSizeColor", unlocked.size());
        for (int i = 0; i < unlocked.size(); i++) {
            AssetLoader.prefs.putString("Color " + i, saveColor(unlocked.get(i)));
        }
        AssetLoader.prefs.putInteger("nextUnlockColor", nextUnlock);
        AssetLoader.prefs.putInteger("maxUnlockColor", maxUnlock);
    }

    private static void load() {
        current = AssetLoader.prefs.contains("currentColor") ? loadColor(AssetLoader.prefs.getString("currentColor")) : characterColors[0];
        selected = AssetLoader.prefs.getInteger("selectedColor");
        nextUnlock = AssetLoader.prefs.contains("nextUnlockColor") ? AssetLoader.prefs.getInteger("nextUnlockColor") : 15;
        maxUnlock = AssetLoader.prefs.contains("maxUnlockColor") ? AssetLoader.prefs.getInteger("maxUnlockColor") : 30;
        unlocked = new ArrayList<Color>();
        int size = AssetLoader.prefs.getInteger("unlockedSizeColor");
        show = size > 1;
        for (int i = 0; i < size; i++) {
            Color c = loadColor(AssetLoader.prefs.getString("Color " + i));
            unlocked.add(c);
            locked.remove(c);
        }
        if (size == 0) {
            unlocked.add(characterColors[0]);
        }
    }

    private static String saveColor(Color color) {
        return "" + color.r + "," + color.g + "," + color.b;
    }

    private static Color loadColor(String color) {
        String[] parse = color.split(",");
        return new Color(Float.parseFloat(parse[0]), Float.parseFloat(parse[1]), Float.parseFloat(parse[2]), 1.0f);
    }

    public static void checkUnlock(int score) {
        if (score == nextUnlock) {
            maxUnlock += 30;
            nextUnlock = maxUnlock + rand.nextInt(7) - 3;
            show = true;
            unlockRandom(true);
        }
    }

    public static void unlockAll() {
        for (Color c : locked) {
            unlocked.add(c);
            locked.remove(c);
        }
    }

    public static void unlock(Color color, boolean showNote) {
        if (!unlocked.contains(color)) {
            unlocked.add(color);
            locked.remove(color);
            if (showNote) {
            //    NotificationManager.addNotification("Unlock:  New Color", NotificationManager.marktype.COLOR, (TextureRegion[]) null);
            }
        }
    }

    public static void unlockRandom(boolean showNote) {
        if (locked.size() > 0) {
            int color = rand.nextInt(locked.size());
            unlock(locked.get(color), showNote);
        }
    }

    public static void next() {
        selected = selected + 1 < unlocked.size() ? selected + 1 : 0;
        current = unlocked.get(selected);
    }

    public static void prev() {
        selected = selected - 1 > -1 ? selected - 1 : unlocked.size() - 1;
        current = unlocked.get(selected);
    }

    public static Color getColor() {
        return current;
    }

    public static boolean showColorButton() {
        return show;
    }

    public static Color getColor(float r, float g, float b) {
        return getColor(r, g, b, 1);
    }

    public static Color getColor(float r, float g, float b, float a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

}
