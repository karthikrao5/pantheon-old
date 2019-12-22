package com.pantheon.buffers;

public interface VBO {
    void allocate(float[] vertices);
    void draw();
    void delete();
}
