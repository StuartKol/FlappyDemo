package com.stuartkol.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * This class creates an animation for the player.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    /**
     * Constructor for the animation of the player
     * @param region The texture representing the animation
     * @param frameCount The number of frames
     * @param cycleTime The amount of time it takes to go through on cycle of frames
     */
    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    /**
     * Updates the current frame dependent on the amount of time that
     * has passed.
     * @param dt Amount of time passed
     */
    public void update(float dt){
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount) {
            frame = 0;
        }
    }

    /**
     * Gets the current frame of the animation
     * @return The current frame of the animation
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
