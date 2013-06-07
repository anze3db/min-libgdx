package com.psywerx.min;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;


public class MinGame implements ApplicationListener {
	private OrthographicCamera camera;
	private PerspectiveCamera perCamera;
  private MeshHelper meshHelper;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		perCamera = new PerspectiveCamera();
		
		
		meshHelper = new MeshHelper();
    meshHelper.createMesh(new float[] { 
         0, -0.5f, -4,    1f, 0, 0, 1.0f,  0f, 0, 1,
         1, -0.5f, -4,  1f, 1f, 0, 1.0f,  0f, 0, 1,
         1, 0.5f, -4,   1f, 0, 0, 1.0f,  0f, 0 , 1,
         0, 0.5f, -4,    1f, 0, 0, 1.0f,  0f, 1 , 1,
                        
         1, 0.5f, -3,     0f, 0, 1, 1.0f,  0f, 1, 0,
         1, -0.5f, -3,    0f, 0f, 1, 1.0f,  0f, 1, 0,
         0, -0.5f, -3,  0f, 0, 1, 1.0f,  0f, 1 , 0,
         0, 0.5f,-3,     0f, 0, 1, 1.0f,  0f, 1, 0,
          
    
      }, new short[]{
        0, 1, 2, 3, 4, 5, 6, 5, 6, 7,
        
        
    });
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
		
		
//		camera.rotate(-0.1f);
		camera.position.x = 10;
		camera.position.y = 10;
		camera.lookAt(0.0f, 0.0f, 0f);
		camera.position.z = -50f;
		camera.update();
		meshHelper.drawMesh(perCamera);
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
