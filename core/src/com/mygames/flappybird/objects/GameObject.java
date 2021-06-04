package com.mygames.flappybird.objects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {
    private final Vector2 position;
    private final Rectangle bounds;

    public GameObject(float x, float y, float width, float height){
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void setPosition(float x, float y){
        position.set(x, y);
        bounds.setPosition(position);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }
}