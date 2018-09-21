package com.stuartkol.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * A manager of all states used to make this game. Only one state
 * is active at a time, so the manager is organized as a stack where
 * the top state is the only active state.
 */
public class GameStateManager {

    private Stack<State> states;

    /**
     * Constructor for the GameStateManager
     */
    public GameStateManager(){
        states = new Stack<State>();
    }

    /**
     * Puts a new state into focus
     * @param state The state being put into focus
     */
    public void push(State state){
        states.push(state);
    }

    /**
     * Taking the current state out of focus and replacing it
     * with the previous state if it exists
     */
    public void pop(){
        states.pop().dispose();
    }

    /**
     * Sets the current state
     * @param state The state to be set
     */
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Calls update method of the current state
     * @param dt Amount of time passed
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * Calls render method of the current state
     * @param sb The game's spritebatch
     */
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
