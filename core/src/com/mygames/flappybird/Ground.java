package com.mygames.flappybird;

public class Ground extends GameObject
{
    public static final float GROUND_WIDTH = 2 * Settings.VIRTUAL_WIDTH;
    public static final float GROUND_HEIGHT = 100;
    private static final float POS_Y = -10;
    private float posX;

    public Ground(){
        super(0, POS_Y, GROUND_WIDTH, GROUND_HEIGHT);
        posX = 0;
    }

    public void update(float speed){
        posX -= speed;
        if(posX < -Settings.VIRTUAL_WIDTH){
            posX = 0;
        }
        setPosition(posX, POS_Y);
    }

}