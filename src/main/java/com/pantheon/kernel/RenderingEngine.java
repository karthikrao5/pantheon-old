package com.pantheon.kernel;

import com.pantheon.configs.Config;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine {

    private Window window;

    public RenderingEngine() {
        window = Window.getInstance();
    }

    void init() {
        window.init();
    }

    void render() {
        Config.clearScreen();

        glBegin(GL_QUADS);

        glVertex2f(-0.5f, 0.5f);
        glVertex2f(0.5f, 0.5f);
        glVertex2f(0.5f, -0.5f);
        glVertex2f(-0.5f, -0.5f);

        glEnd();

        // draw into openGL window
        window.render();
    }

    public void update(){
        if (Input.getInstance().isKeyPushed(GLFW.GLFW_KEY_ESCAPE)){
            glfwSetWindowShouldClose(window.getWindow(), true); // We will detect this in the rendering loop
        }
    }
}
