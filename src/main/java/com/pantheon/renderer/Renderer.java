package com.pantheon.renderer;

import com.pantheon.buffers.VBO;
import com.pantheon.shader.ShaderProgram;

public class Renderer {

    private VBO vbo;
    private ShaderProgram shader;

    public Renderer() {
    }

    void render() {
        shader.bind();
        vbo.draw();
    }

    public void setVbo(VBO vbo) {
        this.vbo = vbo;
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }
}
