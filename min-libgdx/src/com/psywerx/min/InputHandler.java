package com.psywerx.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

public class InputHandler {
  
  
  public static void update(){
    if(Gdx.input.isTouched()){
      int x = Gdx.input.getDeltaX();
      int y = Gdx.input.getDeltaY();
      
      
      Vector3 position = MinGame.camera.getPosition();
      
      position.add(x, y, (float)Math.atan2(y, x));
      
      System.out.println(String.format("%s %s %s", position.x, position.y, position.z));
      
    }
    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
      
    }
  }
}
