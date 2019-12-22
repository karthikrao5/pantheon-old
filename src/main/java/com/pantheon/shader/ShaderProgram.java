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

    void addVertexShader(String shaderCode) {
        addShader(shaderCode, GL_VERTEX_SHADER);
    }

    void addFragShader(String shaderCode) {
        addShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private void addShader(String code, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println(this.getClass().getName() + " Shader creation failed");
            System.exit(1);
        }

        glShaderSource(shader, code);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    public void compileShader() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.out.println(this.getClass().getName() + " " + glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(this.getClass().getName() + " " + glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }
}
