package com.mygames.flappybird;
import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject
{
    Vector2 velocity;

    public DynamicGameObject(float x, float y, float width, float height){
        super(x, y, width, height);
        this.velocity = new Vector2();
    }
}