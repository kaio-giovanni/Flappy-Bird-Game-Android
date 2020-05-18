package com.mygames.flappybird;

public class Pipe extends GameObject
{
    public static final float PIPE_WIDTH = 110;
    public static final float PIPE_HEIGHT = 768;

    public Pipe(float x, float y){
        super(x, y, PIPE_WIDTH, PIPE_HEIGHT);
    }
}