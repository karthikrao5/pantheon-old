package com.pantheon.kernel;

import com.pantheon.configs.Config;
import com.pantheon.shader.ShaderProgram;
import com.pantheon.utils.ResourceLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderingEngine {

    private Window window;
    private int program;
    private int vboId;
    private int vaoId;

    public RenderingEngine() {
        window = Window.getInstance();
        program = glCreateProgram();
        if (program == 0) {
            System.err.println("Unable to create shader program");
            System.exit(1);
        }

        int vertexShaderId = addShader(ResourceLoader.loadShader("shaders/vertex.glsl"), GL_VERTEX_SHADER);
        int fragShaderId = addShader(ResourceLoader.loadShader("shaders/frag.glsl"), GL_FRAGMENT_SHADER);

        System.out.println("linking program");
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0)
        {

            System.err.println(glGetShaderInfoLog(program, 1024));
            System.exit(1);
        }

        System.out.println("validating program");
        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.out.println(glGetShaderInfoLog(program, 1024));
        }

        System.out.println("deleting shaders");
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragShaderId);

        final float[] vertices = new float[]{
                -0.5f, 0.0f, 0.0f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        FloatBuffer verticesBuff = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuff.put(vertices).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBufferData(GL_ARRAY_BUFFER, verticesBuff, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false,  0, 0);
        glEnableVertexAttribArray(0);
    }

    void init() {
    }

    void render() {
        glClearColor(0.0f,0.0f,0.0f,1.0f);
        glClearDepth(1.0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram(program);

        glBindVertexArray(vaoId);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glUseProgram(0);

        glfwSwapBuffers(window.getWindow());
    }

    public void update() {
        if (Input.getInstance().isKeyPushed(GLFW.GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindow(), true);
        }
    }

    private int addShader(String code, int type) {
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

        System.out.println("attaching shaderid: " + shaderId + "to progrma: " + program);
        glAttachShader(program, shaderId);
        return shaderId;
    }
}
