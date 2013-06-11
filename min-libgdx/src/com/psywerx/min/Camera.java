package com.psywerx.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Camera {
  private OrthographicCamera camera;
  private float theta = 0;
  private float phi   = 0;
  private Vector3 target = new Vector3(0,0,-50);
  private final float EPS = 0.01f;
  
  private boolean moving = false;

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
  
  public void move(int dx, int dy){
    moving = true;
    theta += dx / 100f;
    phi += dy / 100f;
  }

  public void update(float delta) {

    Vector3 pos = camera.position;

    if(moving){
      
      float t = MathUtils.atan2(pos.x, pos.z);
      float p = MathUtils.atan2((float) Math.sqrt(pos.x * pos.x + pos.z * pos.z), pos.y);
      t += theta;
      p += phi;
      
      System.out.println(p);
      p = MathUtils.clamp(p, EPS, MathUtils.PI - EPS);
      if(Math.abs(t) < MathUtils.PI/2){
        t = Math.abs(t)/t * MathUtils.PI/2;
      }
      //t = MathUtils.clamp(t, -1.55f, 1.56f);
      pos.x = 50 * MathUtils.sin(p) * MathUtils.sin(t);
      pos.y = 50 * MathUtils.cos(p);
      pos.z = 50 * MathUtils.sin(p) * MathUtils.cos(t);
      camera.lookAt(0, 0, 0);
      camera.update();
      theta = 0;
      phi = 0;
      return;
    }
    // Reset to position
    pos.mul(0.9f).add(target.cpy().mul(0.1f));
    camera.lookAt(0, 0, 0);
    camera.update();
  }

  public Matrix4 getCombined() {
    return camera.combined;
  }

  public Vector3 getPosition() {
    return camera.position;
  }

  public void stopMove() {
    moving = false;
  }
}
