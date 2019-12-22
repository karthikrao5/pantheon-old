package com.pantheon.kernel;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Window {

    private static Window _instance = null;

    long window;
    int width;
    int height;

    public static Window getInstance() {
        if (_instance == null) {
            _instance = new Window();
        }
        return _instance;
    }

    public void init() {}

    void create(int width, int height) {
        this.width = width;
        this.height = height;

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, "Pantheon Game Engine", 0,0);

        if (window == 0) {
            throw new RuntimeException("Failed to create window");
        }
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }


    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    public void dispose() {
        glfwDestroyWindow(window);
    }

    public void render() {
        glfwSwapBuffers(window);
    }

    public long getWindow() {
        return window;
    }
}
