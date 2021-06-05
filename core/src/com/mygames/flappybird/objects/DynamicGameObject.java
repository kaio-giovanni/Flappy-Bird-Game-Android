package com.mygames.flappybird.objects;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {
    private final Vector2 velocity;

    public DynamicGameObject(float x, float y, float width, float height){
        super(x, y, width, height);
        this.velocity = new Vector2();
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }
}