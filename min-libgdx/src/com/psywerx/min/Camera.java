package com.psywerx.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Camera {
  private OrthographicCamera camera;

  public Camera(float width, float height){
    camera = new OrthographicCamera(width, height);
    camera.setToOrtho(true, width, height);
    camera.position.set(0,0,-50);
    camera.far = 1000;
  }

  public void resize(int width, int height) {
    
    camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(0, 0, -50);
  }

  public void update(float delta) {
    camera.lookAt(0.0f, 0.0f, 0f);
    camera.update();
  }

  public Matrix4 getCombined() {
    return camera.combined;
  }

  public Vector3 getPosition() {
    return camera.position;
  }
}
