package com.psywerx.min;

import com.badlogic.gdx.graphics.Camera;
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
      vertices[i++] = 1;
      vertices[i++] = 0.0f;
      vertices[i++] = 0.0f;
      vertices[i++] = 1.0f;
    }

    short[] indices = {0, 2, 1, 0, 3, 2, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 15, 14, 12, 14, 13, 16, 17, 18, 16, 18, 19,
      20, 23, 22, 20, 22, 21};

    mesh.setVertices(vertices);
    mesh.setIndices(indices);

    return mesh;
  }
  
  
  
  public void createMesh(float[] vertices, short[] indices){
    
    mesh = genCube();
    mesh.scale(0.2f, 0.2f, 0.2f);
    modelView = new Matrix4().idt();
  }
  
  
  public void drawMesh(Camera camera){
    if(mesh == null){
      throw new IllegalStateException("Draw mesh called before mash was initialized");
    }
    
//    modelView.setToScaling(0.3f, 0.3f, 0.3f);
    modelView.rotate(1, 1, 0, 0.5f);
    meshShader.begin();
    meshShader.setUniformMatrix("u_worldView", camera.combined);
    meshShader.setUniformMatrix("u_modelView", modelView);
    meshShader.setUniform3fv("u_lightPos", new float[]{0,0,-2f}, 0, 3);
    mesh.render(meshShader, GL20.GL_TRIANGLES);
    meshShader.end();
  }
  
  private void createShader(){
    // this shader tells opengl where to put things
    String vertexShader = "uniform mat4 u_worldView;     \n"
                        + "uniform mat4 u_modelView;     \n"
                        + "uniform vec3 u_lightPos;      \n"
        
                        + "attribute vec4 a_position;    \n"
                        + "attribute vec4 a_color;       \n"
                        + "attribute vec3 a_normal;      \n"
        
                        + "varying vec4 v_color;         \n"
                        
                        + "void main()                   \n"
                        + "{                             \n"
                        + "   vec3 modelViewVertex = vec3(u_modelView * a_position); \n"
                        + "   vec3 modelViewNormal = vec3(u_modelView * vec4(a_normal, 0.0));"
                        + "   vec3 lightVector = normalize(u_lightPos - modelViewVertex);"
                        + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.5);"
                        + "   v_color = a_color * diffuse;         \n"
                        + "   gl_Position = u_worldView * u_modelView * a_position;  \n"
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
