package com.pantheon.buffers;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SimpleVBO implements VBO {
    private int vbo;
    private int vaoId;

    public SimpleVBO() {
        vbo = glGenBuffers();
        vaoId = glGenVertexArrays();
        System.out.println(String.format("vboId: %d and vaoId: %d", vbo, vaoId));
    }

    @Override
    public void allocate(float[] vertices) {
        glBindVertexArray(vaoId);

        FloatBuffer verticesBuff = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuff.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuff, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);
    }

    @Override
    public void draw() {
        glBindVertexArray(vaoId);

        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public void delete() {

    }
}
