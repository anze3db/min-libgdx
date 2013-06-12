package com.psywerx.min;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;


public class MinGame implements ApplicationListener {
	public static Camera camera;
  private MeshHelper cube;
  private long previous = System.currentTimeMillis();
  private boolean m_fboEnabled = true;
  private FrameBuffer m_fbo;
  private float m_fboScaler = 1f;
  private TextureRegion m_fboRegion;
  private SpriteBatch spriteBatch;
	
	@Override
	public void create() {		
		
	  camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cube = MeshHelper.createCubeMesh();
		spriteBatch = new SpriteBatch();
	}
	
	@Override
	public void dispose() {
		cube.dispose();
	}

	@Override
	public void render() {		
	  
	  int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    if(m_fboEnabled )      // enable or disable the supersampling
    {                  
        if(m_fbo == null)
        {
            // m_fboScaler increase or decrease the antialiasing quality

            m_fbo = new FrameBuffer(Format.RGB565, (int)(width * m_fboScaler ), (int)(height * m_fboScaler), true);
            m_fboRegion = new TextureRegion(m_fbo.getColorBufferTexture());
            m_fboRegion.flip(false, true);
        }

        m_fbo.begin();
    }

    // this is the main render function
    normal_render();
    

    if(m_fbo != null)
    {
        m_fbo.end();

        spriteBatch.begin();         
        spriteBatch.draw(m_fboRegion, 0, 0, width, height);               
        spriteBatch.end();
    }
	}

	private void normal_render() {
	  
	  long current = System.currentTimeMillis();
    float deltaTime = current - previous;
    previous = current;
	  
	  Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    
    
//    Gdx.gl.glEnable(GL20.GL_BLEND);
//    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    Gdx.gl.glDepthMask(true);
    InputHandler.update();
    camera.update(deltaTime);
    cube.update(deltaTime);
    cube.draw();
    Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
    
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
	}

}
