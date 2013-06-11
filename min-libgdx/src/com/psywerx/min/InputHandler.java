package com.psywerx.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
  
  
  public static void update(){
    if(Gdx.input.isTouched()){
      int x = Gdx.input.getDeltaX();
      int y = Gdx.input.getDeltaY();
      
      MinGame.camera.move(x, y);
      
    }
    else {
      MinGame.camera.stopMove();
    }
    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
      
    }
  }
}
