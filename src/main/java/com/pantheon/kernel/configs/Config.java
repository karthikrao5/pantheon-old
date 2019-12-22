package com.pantheon.kernel.configs;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

public class Config {

    public static void init() {
        //counterclockwise triangles
        glFrontFace(GL_CW);

        // enable face culling (don't render sides not visible)
        glEnable(GL_CULL_FACE);

        // remove sides that dont face viewer
        glCullFace(GL_BACK);


        glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public static void clearScreen() {
        glClearColor(0.0f,0.0f,0.0f,1.0f);
        glClearDepth(1.0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
