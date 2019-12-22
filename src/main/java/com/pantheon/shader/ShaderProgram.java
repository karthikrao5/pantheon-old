package com.pantheon.shader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int program;

    public ShaderProgram() {
        program = glCreateProgram();
        if (program == 0) {
            System.err.println("Unable to create shader program");
            System.exit(1);
        }
    }

    public void addVertexShader(String shaderCode) {
        addShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void addFragShader(String shaderCode) {
        addShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private void addShader(String code, int type) {
        int shaderId = glCreateShader(type);

        if (shaderId == 0) {
            System.err.println(this.getClass().getName() + " Shader creation failed");
            System.exit(1);
        }

        glShaderSource(shaderId, code);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(shaderId, 1024));
            System.exit(1);
        }

        glAttachShader(program, shaderId);
    }

    public void link() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.out.println(this.getClass().getName() + " " + glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public int getProgram() {
        return this.program;
    }
}
