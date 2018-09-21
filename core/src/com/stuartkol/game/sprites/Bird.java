package com.stuartkol.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Our player is a bird and this class provides the necessary controls
 * and functions of the bird.
 */
public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 pos;
    private Vector3 vel;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;

    /**
     * Constructor for the bird
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     */
    public Bird(int x, int y) {
        pos = new Vector3(x, y, 0);
        vel = new Vector3(0, 0, 0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    /**
     * Updates the current position of the bird dependent on the
     * amount of time that has passed
     * @param dt Amount of time passed
     */
    public void update(float dt){
        birdAnimation.update(dt);
        if (pos.y > 0){
            vel.add(0, GRAVITY, 0);
        }

        // Scale velocity to match time passed
        vel.scl(dt);
        pos.add(MOVEMENT * dt, vel.y, 0);
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y > 375) {
            pos.y = 375;
        }

        // Reset scale to 1
        vel.scl(1/dt);
        bounds.setPosition(pos.x, pos.y);
    }

    /**
     * Gets the current position of the bird as an (x, y, z) vector
     * @return pos An (x, y, z) vector representing the position
     *      of the bird
     */
    public Vector3 getPos() {
        return pos;
    }

    /**
     * Gets the current frame of the animation the bird is currently in
     * @return The current animation frame
     */
    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    /**
     * Causes the bird to "jump" by increasing its' upwards velocity
     */
    public void jump(){
        if (pos.y < 375) {
            vel.y = 250;
            flap.play(0.5f);
        }
    }

    /**
     * Gets the hit-box of the player
     * @return bounds The hit-box of the player
     */
    public Rectangle getBounds(){
        return bounds;
    }

    /**
     * Disposes of textures and sounds
     */
    public void dispose(){
        texture.dispose();
        flap.dispose();
    }
}
