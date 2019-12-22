package com.pantheon.kernel;

import com.pantheon.kernel.configs.Config;
import com.pantheon.kernel.configs.Constants;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import static org.lwjgl.glfw.GLFW.*;

public class CoreEngine {

    int fps;
    float framerate = 200;
    float frameTime = 1.0f / framerate;
    boolean isRunning;

    GLFWErrorCallback errorCallback;

    RenderingEngine renderingEngine;

    public void createWindow(int width, int height) {
        glfwInit();

        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        Window.getInstance().create(width, height);

        renderingEngine = new RenderingEngine();

        getDeviceProperties();
    }

    private void getDeviceProperties(){
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + " bytes");
        System.out.println("Max Geometry Uniform Blocks: " + GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS+ " bytes");
        System.out.println("Max Geometry Shader Invocations: " + GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS + " bytes");
        System.out.println("Max Uniform Buffer Bindings: " + GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS + " bytes");
        System.out.println("Max Uniform Block Size: " + GL31.GL_MAX_UNIFORM_BLOCK_SIZE + " bytes");
        System.out.println("Max SSBO Block Size: " + GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE + " bytes");
    }

    public void init() {
        Config.init();
        renderingEngine.init();
    }

    void start() {
        if (isRunning) {
            return;
        }
        run();
    }

    /**
     * main game loop
     */
    void run() {
        this.isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        // Rendering Loop
        while(isRunning)
        {
            boolean render = false;

            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Constants.NANOSECOND;
            frameCounter += passedTime;


            while(unprocessedTime > frameTime)
            {

                render = true;
                unprocessedTime -= frameTime;

                if(Window.getInstance().isCloseRequested())
                    stop();

                update();

                if(frameCounter >= Constants.NANOSECOND)
                {
                    setFps(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if(render)
            {
                render();
                frames++;
            }
            else
            {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    private void cleanUp() {
        Window.getInstance().dispose();
        glfwTerminate();
    }

    private void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
    }

    private void update() {
        Input.getInstance().update();
        renderingEngine.update();
    }

    private void render() {
        renderingEngine.render();
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
