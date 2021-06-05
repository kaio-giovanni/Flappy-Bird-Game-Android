package com.mygames.flappybird.entities;

import com.mygames.flappybird.config.Settings;
import com.mygames.flappybird.utils.Utils;

public class World {
    private static final int WORLD_STATE_RUNNING = 1;
    private static final int WORLD_STATE_NEXT_LEVEL = 2;
    private static final int WORLD_STATE_GAME_OVER = 3;
    private static final int SPACE_BETWEEN_PIPES = 200;

    private final Bird bird;
    private final Ground ground;
    private final Pipe pipeNorth;
    private final Pipe pipeSouth;

    private float pipePosInitX;
    private float pipePosX;
    private float pipeSouthPosY;
    private float pipeNorthPosY;
    private float speedLevel;
    private int worldState;
    private long score;
    private int randomSpace;

    public World() {
        bird = new Bird(Settings.VIRTUAL_HEIGHT / 2);
        ground = new Ground();
        randomSpace = Utils.getRandomNumberByInterval(400) - 200;
        pipeNorthPosY = getPipeNorthPosY(randomSpace);
        pipeSouthPosY = getPipeSouthPosY(randomSpace);
        pipePosInitX = Settings.VIRTUAL_WIDTH;
        pipePosX = pipePosInitX;

        pipeSouth = new Pipe(pipePosInitX, pipeSouthPosY);
        pipeNorth = new Pipe(pipePosInitX, pipeNorthPosY);
        speedLevel = 4;
        worldState = WORLD_STATE_RUNNING;
        score = 0;
    }

    public void update(float deltaTime, float mY) {
        generateLevel(deltaTime);
        bird.fly(deltaTime, mY);
        bird.checkColisionGround(ground);
        bird.checkColisionPipe(pipeSouth);
        bird.checkColisionPipe(pipeNorth);

        gameOver();
    }

    public long getScore() {
        return score;
    }

    public boolean isGameOver() {
        return worldState == WORLD_STATE_GAME_OVER;
    }

    public float getBirdJump() {
        return bird.getBirdJump();
    }

    public Bird getBird() {
        return bird;
    }

    public Pipe getPipeNorth() {
        return pipeNorth;
    }

    public Pipe getPipeSouth() {
        return pipeSouth;
    }

    public Ground getGround() {
        return ground;
    }

    private void generateLevel(float deltaTime) {
        if (pipePosX < -Pipe.PIPE_WIDTH) {
            pipePosX = pipePosInitX;

            randomSpace = Utils.getRandomNumberByInterval(400) - 200;

            pipeNorthPosY = getPipeNorthPosY(randomSpace);
            pipeSouthPosY = getPipeSouthPosY(randomSpace);

            speedLevel += speedLevel * deltaTime * 5;
            if (speedLevel > 10) {
                speedLevel = 10;
            }
            score++;
        }

        pipeNorth.setPosition(pipePosX, pipeNorthPosY);
        pipeSouth.setPosition(pipePosX, pipeSouthPosY);
        ground.update(speedLevel);
        pipePosX = pipePosX - speedLevel;
    }


    private float getPipeNorthPosY(int randomSpace) {
        return Settings.VIRTUAL_HEIGHT / 2 + SPACE_BETWEEN_PIPES / 2 + randomSpace;
    }

    private float getPipeSouthPosY(int randomSpace) {
        return Settings.VIRTUAL_HEIGHT / 2 - Pipe.PIPE_HEIGHT - SPACE_BETWEEN_PIPES / 2 + randomSpace;
    }

    private void gameOver() {
        if (bird.isFall()) {
            worldState = WORLD_STATE_GAME_OVER;
        }
    }
}