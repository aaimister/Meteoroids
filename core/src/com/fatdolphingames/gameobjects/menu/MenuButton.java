package com.fatdolphingames.gameobjects.menu;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.math.Vector2;
import com.fatdolphingames.accessors.SpriteAccessor;
import com.fatdolphingames.gameobjects.SpriteObject;
import com.fatdolphingames.gameworld.GameWorld;

public abstract class MenuButton extends SpriteObject {

    private float offsetX;
    private float offsetY;

    public MenuButton(GameWorld world, float x, float y, int width, int height, float offsetX, float offsetY) {
        super(world, x + offsetX, y + offsetY, width, height);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void moveButton(Vector2 start, Vector2 end) {
        if (start != null) {
            setPosition(start.x + offsetX, start.y + offsetY);
        }
        tweenManager.killTarget(this);
        Tween.to(this, SpriteAccessor.POSITION, 0.5f).target(end.x + offsetX, end.y + offsetY).ease(TweenEquations.easeInOutQuad).start(tweenManager);
    }

//    @Override
//    public float getX() {
//        return super.getX() + offsetY;
//    }
//
//    @Override
//    public float getY() {
//        return super.getY() + offsetY;
//    }

}
