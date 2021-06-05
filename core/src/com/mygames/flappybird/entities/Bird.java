package com.mygames.flappybird.entities;

import com.mygames.flappybird.objects.DynamicGameObject;

public class Bird extends DynamicGameObject {
    private static final int BIRD_FLYING = 1;
    private static final int BIRD_FALL = 2;
    private static final float BIRD_WIDTH = 70;
    private static final float BIRD_HEIGHT = 65;
    private static final float BIRD_POS_X = 60;
    private static final float BIRD_JUMP = 9f;
    private float indexTexture;
    private int birdState;

    public Bird(float y) {
        super(BIRD_POS_X, y, BIRD_WIDTH, BIRD_HEIGHT);
        setVelocity(0, y);
        birdState = BIRD_FLYING;
        indexTexture = 0;
    }

    public void fly(float deltaTime, float mY) {
        if (isFlying()) {
            float velocity = this.getVelocity().y - mY;
            setVelocity(0, velocity);
            setPosition(BIRD_POS_X, velocity);
            indexTexture += deltaTime * 8;

            if (indexTexture > 2)
                indexTexture = 0;
        }
    }

    public void checkColisionGround(Ground ground) {
        if (getBounds().overlaps(ground.getBounds())) {
            birdState = BIRD_FALL;
        }
    }

    public void checkColisionPipe(Pipe pipe) {
        if (getBounds().overlaps(pipe.getBounds()))
            birdState = BIRD_FALL;
    }

    public boolean isFall() {
        return birdState == BIRD_FALL;
    }

    public boolean isFlying() {
        return birdState == BIRD_FLYING;
    }

    public float getBirdJump() {
        return BIRD_JUMP;
    }

    public float getIndexTexture() {
        return indexTexture;
    }
}