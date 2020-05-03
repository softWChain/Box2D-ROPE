package com.veys.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veys.game.Game;
import com.veys.game.managers.GSM;

public abstract class GameState {
	
	protected OrthographicCamera cam;
	protected SpriteBatch sb;
	
	private Game game;
	protected GSM gsm;
	
	
	
	public GameState(GSM gsm) {
		this.gsm = gsm;
		cam = gsm.game().getCamera();
		sb = gsm.game().getSpriteBatch();
		
	}
	
	public abstract void HandleInput(float dt);
	public abstract void tick(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();


}
