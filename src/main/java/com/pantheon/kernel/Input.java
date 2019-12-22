package com.pantheon.kernel;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static Input _input;

    private ArrayList<Integer> pushedKeys = new ArrayList<Integer>();
    private ArrayList<Integer> keysHolding = new ArrayList<Integer>();
    private ArrayList<Integer> releasedKeys = new ArrayList<Integer>();

    private ArrayList<Integer> pushedButtons = new ArrayList<Integer>();
    private ArrayList<Integer> buttonsHolding = new ArrayList<Integer>();
    private ArrayList<Integer> releasedButtons = new ArrayList<Integer>();

    @SuppressWarnings("unused")
    private GLFWKeyCallback keyCallback;


    public static Input getInstance() {
        if (_input == null) {
            _input = new Input();
        }
        return _input;
    }

    public Input() {
        glfwSetKeyCallback(Window.getInstance().getWindow(), (keyCallback) = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS){
                    if (!pushedKeys.contains(key)){
                        pushedKeys.add(key);
                        keysHolding.add(key);
                    }
                }

                if (action == GLFW_RELEASE){
                    keysHolding.remove(Integer.valueOf(key));
                    releasedKeys.add(key);
                }
            }
        });
    }

    public boolean isKeyPushed(int key)
    {
        return pushedKeys.contains(key);
    }

    public void update() {
        pushedKeys.clear();
        releasedKeys.clear();
        pushedButtons.clear();
        releasedButtons.clear();

        glfwPollEvents();
    }
}
