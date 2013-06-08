package com.psywerx.min;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MinGame implements ApplicationListener {
	private OrthographicCamera camera;
  private MeshHelper meshHelper;
	
	@Override
	public void create() {		
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		meshHelper = new MeshHelper();
    meshHelper.createMesh();
	}
	
	@Override
	public void dispose() {
		meshHelper.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT
            | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		camera.position.x = 10;
		camera.position.y = 10;
		camera.lookAt(0.0f, 0.0f, 0f);
		camera.position.z = -50f;
		camera.update();
		meshHelper.drawMesh(camera);
	}

	@Override
	public void resize(int width, int height) {
	  camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
