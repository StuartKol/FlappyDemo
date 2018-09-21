package com.stuartkol.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * This class represents a pair of tubes that act as obstacles in this game, Flappy Bird.
 * A player can collide with these tubes, but the result of those collisions are NOT
 * determined in this class.
 *
 * This class also contains a scoring mechanism. Much like the tubes, the player can collide
 * with the scoring hit-box, but it does not have a texture.
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;
    // Fluctuation of tube y placement
    private static final int FLUCTUATION =  130;
    private static final int TUBE_GAP = 80;
    // Lower bound for tube y placement
    private static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube, posScore;
    private Rectangle boundsTop, boundsBot, boundsScore;
    private Random rand;
    private Sound point;

    /**
     * Constructor for one set of tubes, a top tube and a bottom tube that function as a pair
     * @param x The initial starting x position of the pair
     */
    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        posScore = new Vector2(x + (bottomTube.getWidth() / 2), posBotTube.y + bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

        // Score hit-box
        boundsScore = new Rectangle(posScore.x, posScore.y, bottomTube.getWidth(), TUBE_GAP);

        point = Gdx.audio.newSound(Gdx.files.internal("point.mp3"));
    }

    /**
     * Returns the texture of the top tube
     * @return topTube The texture of the top tube
     */
    public Texture getTopTube() {
        return topTube;
    }

    /**
     * Returns the texture of the bottom tube
     * @return bottomTube The texture of the bottom tube
     */
    public Texture getBottomTube() {
        return bottomTube;
    }

    /**
     * Returns the position of the top tube as an (x, y) vector
     * @return posTopTube An (x, y) vector representing the position
     *      of the top tube
     */
    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    /**
     * Returns the position of the bottom tube as an (x, y) vector
     * @return posBotTube An (x, y) vector representing the position
     *      of the bottom tube
     */
    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    /**
     * Repositions a pair of tubes to a given position and randomizes the
     * pair's y coordinate along with the pair's hit-box. The score hit-box
     * is moved to the same new position as well.
     * @param x The new x coordinate the tube pair should take
     */
    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        posScore.set(x + (bottomTube.getWidth() / 2), posBotTube.y + bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);

        boundsScore.setPosition(posScore.x, posScore.y);
    }

    /**
     * Returns a boolean representing whether or not the hit-box of either the
     * top tube or bottom tube are currently overlapping with the player's hit-box
     * @param player The hit-box of the player
     * @return True if the player overlaps with either tube and false if the player
     * isn't overlapping either tube at all
     */
    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    /**
     * Returns a boolean dependent on whether or not the player's hit-box is currently
     * overlapping a score hit-box.
     * @param player The hit-box of the player
     * @return True if the player's hit-box is overlapping with the score hit-box and
     * false if the player's hit-box is not overlapping with the score hit-box
     */
    public boolean scores(Rectangle player){
        boolean scored = player.overlaps(boundsScore);

        if (scored) {
            // move off screen to prevent unintentional additional scoring
            boundsScore.setPosition(posScore.x + 1000, posScore.y + 1000);
            point.play(1.0f);
        }
        return scored;
    }

    /**
     * Disposes of textures
     */
    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
