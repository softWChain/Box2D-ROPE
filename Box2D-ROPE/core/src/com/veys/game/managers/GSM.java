package com.veys.game.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veys.game.Game;
import com.veys.game.states.GameState;
import com.veys.game.states.PlayState;

public class GSM {
	
	private Game game;
	
	public Stack<GameState> gameState = new Stack<GameState>();
	public static final int PLAY = 848484;
	
	
	public GSM(Game game) {
		this.game = game;
		pushState(PLAY);
	}
	
	
	public void tick(float dt) {
		gameState.peek().tick(dt);
		
	}
	public void render(SpriteBatch sb) {
		gameState.peek().render(sb);
	}
	
	public GameState getState(int state) {
		if(state == PLAY) {
			
			return new PlayState(this);
		}
		
		return null;
	}

	public void pushState(int state) {
		gameState.push(getState(state));
	}
	
	public void pop() {
		gameState.pop();
	}
	
	
	public Game game() { return game;}

}
