package com.fireball1725.firelib.renderer.obj;

public class TextureCoordinate {
    public float u, v, w;

    public TextureCoordinate(float u, float v) {
        this(u, v, 0f);
    }

    public TextureCoordinate(float u, float v, float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

