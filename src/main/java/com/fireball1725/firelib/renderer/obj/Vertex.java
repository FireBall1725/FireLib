package com.fireball1725.firelib.renderer.obj;

public class Vertex {
    public float x, y, z;

    public Vertex(float x, float y) {
        this(x, y, 0f);
    }

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
