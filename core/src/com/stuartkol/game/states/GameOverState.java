package com.stuartkol.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuartkol.game.FlappyDemo;

/**
 * This class represents a state of the game where the player made a mistake
 * and reaches this state as a result.
 */
public class GameOverState extends State{
    private Texture background;
    private Texture gameover;

    Music music;

    private boolean created;

    /**
     * Constructor of the GameOverState
     * @param gsm The Game State Manager
     */
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        created = false;
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("gameovermusic.mp3"));
        music.setLooping(true);
        music.play();
        created = true;
    }

    /**
     * When screen is touched, gives control to the MenuState
     */
    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && created){
            music.stop();
            gsm.set(new MenuState(gsm));
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
     * Draws background and gameover sprite
     * @param sb The game's spritebatch
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameover, cam.position.x - gameover.getWidth() / 2, cam.position.y);
        sb.end();
    }

    /**
     * Disposes of textures of sounds
     */
    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
        music.dispose();
        System.out.println("GameOverState Disposed");
    }
}
