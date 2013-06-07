package com.psywerx.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MeshHelper {
  private Mesh mesh;
  private ShaderProgram meshShader;
  
  public MeshHelper(){
    createShader();
  }
  
  public void createMesh(float[] vertices){
    mesh = new Mesh(true, vertices.length, 0, 
        new VertexAttribute(Usage.Position, 3, "a_position"),
        new VertexAttribute(Usage.Color, 4, "a_color"),
        new VertexAttribute(Usage.Normal, 3, "a_normal"));
    
    mesh.setVertices(vertices);

  }
  
  public void drawMesh(OrthographicCamera camera){
    if(mesh == null){
      throw new IllegalStateException("Draw mesh called before mash was initialized");
    }
    meshShader.begin();
    meshShader.setUniformMatrix("u_worldView", camera.combined);
    meshShader.setUniform3fv("u_lightPos", new float[]{0.2f, 0.2f, 1.2f}, 0, 3);
    mesh.render(meshShader, GL20.GL_TRIANGLES);
    meshShader.end();
  }
  
  private void createShader(){
    // this shader tells opengl where to put things
    String vertexShader = "uniform mat4 u_worldView;     \n"
                        + "uniform vec3 u_lightPos;      \n"
        
                        + "attribute vec4 a_position;    \n"
                        + "attribute vec4 a_color;       \n"
                        + "attribute vec3 a_normal;      \n"
        
                        + "varying vec4 v_color;         \n"
                        
                        + "void main()                   \n"
                        + "{                             \n"
                        + "   vec3 modelViewVertex = vec3(u_worldView * a_position); \n"
                        + "   vec3 modelViewNormal = vec3(u_worldView * vec4(a_normal, 0.0));"
                        + "   vec3 lightVector = normalize(u_lightPos - modelViewVertex);"
                        + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);"
                        + "   v_color = a_color * diffuse;         \n"
                        + "   gl_Position = u_worldView * a_position;  \n"
                        + "}                             \n";
    // this one tells it what goes in between the points (i.e
    // colour/texture)
    String fragmentShader = "#ifdef GL_ES                \n"
                          + "precision mediump float;    \n"
                          + "#endif                      \n"
                          + "varying vec4 v_color;       \n"
                          + "void main()                 \n"
                          + "{                           \n"
                          + "  gl_FragColor = v_color;    \n"
                          + "}";

    // make an actual shader from our strings
    meshShader = new ShaderProgram(vertexShader, fragmentShader);

    // check there's no shader compile errors
    if (!meshShader.isCompiled())
      throw new IllegalStateException(meshShader.getLog());
  }
  
  public void dispose(){
    mesh.dispose();
    meshShader.dispose();
  }
}
