package com.psywerx.min;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.utils.ShaderLoader;


public class MinGame implements ApplicationListener {
	public static Camera camera;
  private MeshHelper cube;
  private long previous = System.currentTimeMillis();
  private PostProcessor postProcessor;
	
	@Override
	public void create() {		
		
	  camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cube = MeshHelper.createCubeMesh();
		
		ShaderLoader.BasePath = "data/shaders/";
    postProcessor = new PostProcessor( false, false, false); //TODO: isDesktop
    Bloom bloom = new Bloom( (int)(Gdx.graphics.getWidth() * 0.25f), (int)(Gdx.graphics.getHeight() * 0.25f) );
    postProcessor.addEffect( bloom );
		
	}
	
	@Override
	public void dispose() {
		cube.dispose();
		postProcessor.dispose();
	}

	@Override
	public void render() {		
	  
	  long current = System.currentTimeMillis();
	  float deltaTime = current - previous;
	  previous = current;
	  
	  postProcessor.capture();
	  
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT
            | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		InputHandler.update();
		camera.update(deltaTime);
		cube.update(deltaTime);
		cube.draw();
		
		postProcessor.render();
		
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
	  // TODO: android rebind code here
	  postProcessor.rebind();
	}

}
