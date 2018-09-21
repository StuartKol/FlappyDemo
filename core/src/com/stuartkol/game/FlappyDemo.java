package com.stuartkol.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuartkol.game.states.GameStateManager;
import com.stuartkol.game.states.MenuState;

/**
 * The application! This class starts our game!
 */
public class FlappyDemo extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Flappy Bird";
	private GameStateManager gsm;
	// Want only one sb per game
	private SpriteBatch batch;


	/**
	 * Initializes essentials and instantiates a MenuState
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	/**
	 * Passes the render call to the Game State Manager
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	/**
	 * Disposes of the ApplicationAdapter
	 */
	@Override
	public void dispose () {
		super.dispose();
	}
}
