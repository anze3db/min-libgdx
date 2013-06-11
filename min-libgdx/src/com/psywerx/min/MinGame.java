package com.psywerx.min;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class MinGame implements ApplicationListener {
	public static Camera camera;
  private MeshHelper cube;
	
	@Override
	public void create() {		
		
	  camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cube = MeshHelper.createCubeMesh();
	}
	
	@Override
	public void dispose() {
		cube.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT
            | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		InputHandler.update();
		camera.update(0);
		cube.draw();
	}

	@Override
	public void resize(int width, int height) {
	  camera.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
