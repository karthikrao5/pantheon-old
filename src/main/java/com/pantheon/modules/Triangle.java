package com.pantheon.modules;

import com.pantheon.buffers.SimpleVBO;
import com.pantheon.shader.ShaderProgram;
import com.pantheon.utils.ResourceLoader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Triangle {

    //    private SimpleVBO vbo;
    ShaderProgram program;
    private int vboId;
    private int vaoId;

    public Triangle() {
        program = new ShaderProgram();
        program.addVertexShader(ResourceLoader.loadShader("shaders/vertex.glsl"));
        program.addFragShader(ResourceLoader.loadShader("shaders/frag.glsl"));
        program.link();
        // The vertices of our Triangle
        final float[] vertices = new float[]{0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f};

//        vbo = new SimpleVBO();
//        vbo.allocate(vertices);

        vboId = glGenBuffers();
        vaoId = glGenVertexArrays();

        glBindVertexArray(vaoId);
        FloatBuffer verticesBuff = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuff.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuff, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }


    public void render() {
        program.bind();

        glBindVertexArray(vaoId);

        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, 3);

//        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        program.unbind();
    }
}
