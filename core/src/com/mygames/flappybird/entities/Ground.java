package com.mygames.flappybird.entities;

import com.mygames.flappybird.config.Settings;
import com.mygames.flappybird.objects.GameObject;

public class Ground extends GameObject {
    public static final float GROUND_WIDTH = 2 * Settings.VIRTUAL_WIDTH;
    public static final float GROUND_HEIGHT = 100;
    private static final float POSITION_Y = -10;
    private float positionX;

    public Ground() {
        super(0, POSITION_Y, GROUND_WIDTH, GROUND_HEIGHT);
        this.positionX = 0;
    }

    public void update(float speed) {
        positionX -= speed;
        if (positionX < -Settings.VIRTUAL_WIDTH) {
            positionX = 0;
        }
        setPosition(positionX, POSITION_Y);
    }

}