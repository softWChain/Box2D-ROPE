package com.veys.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.veys.game.managers.GSM;
import com.veys.game.states.PlayState;

public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 780;
	public static final int HEIGHT = 650;
	
	private OrthographicCamera cam;
	private SpriteBatch sb;
	private GSM gsm;
	

	
	@Override
	public void create () {
		
		gsm = new GSM(this);
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,Game.WIDTH,Game.HEIGHT);
		
		
	}

	@Override
	public void render () {
		sb.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.tick(Gdx.graphics.getDeltaTime());
		gsm.render(sb);

	}
	
	@Override
	public void dispose () {

	}
	
	
	
	@Override
	public void resize(int width, int height) {
		
		super.resize(width, height);
		PlayState.view.update(width, height);
	}

	public SpriteBatch getSpriteBatch() { return sb;}
	public OrthographicCamera getCamera() { return cam;}
}
