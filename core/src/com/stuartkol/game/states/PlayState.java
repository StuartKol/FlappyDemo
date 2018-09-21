package com.stuartkol.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.stuartkol.game.FlappyDemo;
import com.stuartkol.game.sprites.Bird;
import com.stuartkol.game.sprites.Tube;

/**
 * This class represents the game-play. The player should be able
 * to tap the screen to cause their bird character to jump, navigating
 * themselves through obstacles.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int score = 0;
    private BitmapFont scoreBoard;

    Music music;

    private boolean created;

    private Array<Tube> tubes;

    /**
     * Constructor for the PlayState
     * @param gsm The Game State Manager
     */
    protected PlayState(GameStateManager gsm) {
        super(gsm);
        created = false;
        bird = new Bird(50, 225);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();

        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube((i + 2)*(TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        scoreBoard = new BitmapFont();

        music = Gdx.audio.newMusic(Gdx.files.internal("gameplaymusic.mp3"));
        music.setLooping(true);
        music.play();
        created = true;
    }

    /**
     * When the screen is touched, the bird jumps
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    /**
     * Checks for any user input and reacts accordingly. Also checks for collisions
     * between the bird and tubes/scoring.
     * @param dt Amount of time passed
     */
    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPos().x + 80;

        for (Tube tube: tubes){
            if (cam.position.x - (cam.viewportWidth / 2) >
                    tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x +
                        ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())){
                music.stop();
                gsm.set(new GameOverState(gsm));
                break;
            }

            if (tube.scores(bird.getBounds())){
                score++;
                System.out.println("Score: " + score);
            }
        }

        if (bird.getPos().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            music.stop();
            gsm.set(new GameOverState(gsm));
        }
        cam.update();
    }

    /**
     * Draws the bird, tubes, ground, background, and score
     * @param sb The game's spritebatch
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPos().x, bird.getPos().y);
        for (Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        scoreBoard.setColor(Color.GOLD);
        scoreBoard.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreBoard.getData().setScale(2.0f);
        GlyphLayout layout = new GlyphLayout(scoreBoard, "" + score);
        scoreBoard.draw(sb, "" + score, cam.position.x - (layout.width / 2), (cam.position.y * 7) / 4);
        sb.end();
    }

    /**
     * Updates the ground to give it the illusion of movement
     */
    private void updateGround(){
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    /**
     * Disposes of textures and sounds
     */
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        ground.dispose();
        music.dispose();
        System.out.println("PlayState Disposed");
    }
}
