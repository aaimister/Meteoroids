package com.fatdolphingames.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation extends com.badlogic.gdx.graphics.g2d.Animation {

    private boolean play;
    private float startTime;

    public Animation(float frameDuration, TextureRegion ...keyFrames) {
        super(frameDuration, keyFrames);
    }

    public void play() {
        play = true;
    }

    @Override
    public TextureRegion getKeyFrame(float stateTime) {
        if (play) {
            startTime = stateTime;
            play = false;
        }

        return super.getKeyFrame(Math.abs(stateTime - startTime));
    }

}
