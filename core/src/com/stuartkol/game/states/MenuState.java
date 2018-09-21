package com.stuartkol.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.stuartkol.game.FlappyDemo;

/**
 * This class represent the menu state of the game. It should allow
 * the user to start a game.
 */
public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    private Texture title;

    private float playBtnX;
    private float playBtnY;

    Music music;

    private boolean created;

    /**
     * Constructor for the MenuState
     * @param gsm The Game State Manager
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        created = false;
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        title = new Texture("title.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("menumusic.mp3"));
        music.setLooping(true);
        music.play();
        created = true;
    }

    /**
     * When the screen is touched in the right area (over the play button sprite),
     * this state will hand control over to the PlayState
     */
    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(tmp);
            Rectangle textureBounds = new Rectangle(playBtnX, playBtnY, playBtn.getWidth(), playBtn.getHeight());
            if (textureBounds.contains(tmp.x, tmp.y) && created){
                music.stop();
                gsm.set(new PlayState(gsm));
            }
        }
    }

    /**
     * Checks for any input
     * @param dt Amount of time passed
     */
    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * Draws a background, a play button, and a title
     * @param sb The game's spritebatch
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        playBtnX = cam.position.x - playBtn.getWidth() / 2;
        playBtnY = cam.position.y - playBtn.getHeight();
        sb.draw(playBtn, playBtnX, playBtnY);
        sb.draw(title, cam.position.x - title.getWidth() / 2, cam.position.y + title.getHeight());
        sb.end();
    }

    /**
     * Disposes of textures and sounds
     */
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        title.dispose();
        music.dispose();
        System.out.println("MenuState Disposed");
    }
}
