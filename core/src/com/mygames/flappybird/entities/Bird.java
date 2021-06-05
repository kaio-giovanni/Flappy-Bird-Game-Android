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

    public Bird(float posY) {
        super(BIRD_POS_X, posY, BIRD_WIDTH, BIRD_HEIGHT);
        setVelocity(0, posY);
        this.birdState = BIRD_FLYING;
        this.indexTexture = 0;
    }

    public void fly(float deltaTime, float deltaY) {
        if (isFlying()) {
            float jump = getVelocity().y - deltaY;
            setVelocity(0, jump);
            setPosition(BIRD_POS_X, jump);
            updateIndexTexture(deltaTime);
        }
    }

    private void updateIndexTexture(float deltaTime) {
        indexTexture += deltaTime * 8;

        if (indexTexture > 2) {
            indexTexture = 0;
        }
    }

    public void checkCollisionGround(Ground ground) {
        if (getBounds().overlaps(ground.getBounds())) {
            birdState = BIRD_FALL;
        }
    }

    public void checkCollisionPipe(Pipe pipe) {
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