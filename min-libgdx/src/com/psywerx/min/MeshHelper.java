package com.psywerx.min;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class MeshHelper {
  private Mesh mesh;
  private ShaderProgram meshShader;
  private Matrix4 modelView;
  private float[] lightPos = new float[3];
  private float totalTime = 1;
  
  public MeshHelper(){
    createShader();
  }
  
  public static Mesh genCube () {
    Mesh mesh = new Mesh(true, 24, 36, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.Normal,
      3, "a_normal"), new VertexAttribute(Usage.TextureCoordinates, 4, "a_color"));

    float[] cubeVerts = {-0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
      -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
      0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f,
      -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
      0.5f, 0.5f, -0.5f,};

    float[] cubeNormals = {0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,};

    float[] vertices = new float[24 * 10];
    int pIdx = 0;
    int nIdx = 0;
    for (int i = 0; i < vertices.length;) {
      vertices[i++] = cubeVerts[pIdx++];
      vertices[i++] = cubeVerts[pIdx++];
      vertices[i++] = cubeVerts[pIdx++];
      vertices[i++] = cubeNormals[nIdx++];
      vertices[i++] = cubeNormals[nIdx++];
      vertices[i++] = cubeNormals[nIdx++];
      vertices[i++] = 0.0f;
      vertices[i++] = 0.0f;
      vertices[i++] = 0.5f;
      vertices[i++] = 0.0f;
    }

    short[] indices = {0, 2, 1, 0, 3, 2, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 15, 14, 12, 14, 13, 16, 17, 18, 16, 18, 19,
      20, 23, 22, 20, 22, 21};

    mesh.setVertices(vertices);
    mesh.setIndices(indices);

    return mesh;
  }

  public static MeshHelper createCubeMesh() {
    MeshHelper m = new MeshHelper();
    m.mesh = genCube();
    m.mesh.scale(40f, 40f, 40f);
    m.modelView = new Matrix4().idt();
    return m;
  }
  
  public void draw(){
    if(mesh == null){
      throw new IllegalStateException("Draw mesh called before mash was initialized");
    }
    
//    modelView.setToScaling(0.3f, 0.3f, 0.3f);
//    modelView.rotate(1, 1, 0, 0.5f);
    
    lightPos[0] = MinGame.camera.getPosition().x;
    lightPos[1] = MinGame.camera.getPosition().y;
    lightPos[2] = MinGame.camera.getPosition().z;
    
    meshShader.begin();
    meshShader.setUniformMatrix("u_worldView", MinGame.camera.getCombined());
    meshShader.setUniformMatrix("u_modelView", modelView);
    meshShader.setUniform3fv("u_lightPos", lightPos, 0, 3);
    meshShader.setUniformf("u_time", totalTime);
    
    mesh.render(meshShader, GL20.GL_TRIANGLES);
    meshShader.end();
  }
  
  private void createShader(){
    // this shader tells opengl where to put things
    String vertexShader = "uniform mat4 u_worldView;     \n"
                        + "uniform mat4 u_modelView;     \n"
                        + "uniform vec3 u_lightPos;      \n"
                        + "uniform float u_time;         \n"
        
                        + "attribute vec4 a_position;    \n"
                        + "attribute vec4 a_color;       \n"
                        + "attribute vec3 a_normal;      \n"
        
                        + "varying vec4 v_color;         \n"
                        
                        + "void main()                   \n"
                        + "{                             \n"
                        + "   float ndotl = max(0.0, dot(a_normal, u_lightPos));"
                        + "   v_color = vec4(0.0, 0.0, 0.0, 0.0);         \n"
                        + "   v_color += a_color;"
                        + "   v_color += ndotl * (0.008, 0.008, 0.008); "
                        + "   gl_Position = u_worldView * u_modelView * (a_position + vec4(a_normal * (sin(u_time)+1)*10, 0.0));  \n"
                        + "}                             \n";
    // this one tells it what goes in between the points (i.e
    // colour/texture)
    String fragmentShader = "#ifdef GL_ES                \n"
                          + "precision mediump float;    \n"
                          + "#endif                      \n"
                          + "uniform float u_time;       \n"
                          + "varying vec4 v_color;       \n"
                          + "void main()                 \n"
                          + "{                           \n"
                          + "  v_color.r = sin(u_time)+1; \n"
                          + "  v_color.a = 1 - (sin(u_time)+1); \n"
                          + "  gl_FragColor = v_color + (sin(u_time/10))/10;    \n"
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

  public void update(float delta) {
    totalTime  += delta/500;
  }
}
