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
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		
		meshHelper = new MeshHelper();
    meshHelper.createMesh(new float[] { 
        -0.5f, -0.5f, 0,  1f, 0, 0, 1.0f,  1f, 0, 0,
         0.5f, -0.5f, 0,  1f, 1f, 0, 1.0f,  1f, 0, 0,
           0f,  0.5f, 0,  1f, 0, 0, 1.0f,  1f, 0 ,0,});
	}
	
	@Override
	public void dispose() {
		meshHelper.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.rotate(-0.1f);
		camera.update();
		meshHelper.drawMesh(camera);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
