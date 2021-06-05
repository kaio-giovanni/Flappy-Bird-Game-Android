package com.mygames.flappybird.entities;

import com.mygames.flappybird.config.Settings;
import com.mygames.flappybird.utils.Utils;

public class World {
    private static final int WORLD_STATE_RUNNING = 1;
    private static final int WORLD_STATE_NEXT_LEVEL = 2;
    private static final int WORLD_STATE_GAME_OVER = 3;
    private static final int SPACE_BETWEEN_PIPES = 200;

    private final Bird player;
    private final Ground ground;
    private final Pipe pipeTop;
    private final Pipe pipeBottom;

    private float pipeInitialPositionX;
    private float pipePositionX;
    private float pipeBottomPositionY;
    private float pipeTopPositionY;
    private float speedLevel;
    private int worldState;
    private long score;
    private int randomSpace;

    public World() {
        player = new Bird(Settings.VIRTUAL_HEIGHT / 2);
        ground = new Ground();
        randomSpace = Utils.getRandomNumberByInterval(400) - 200;
        pipeTopPositionY = getPipeBottomPositionY(randomSpace);
        pipeBottomPositionY = getPipeTopPositionY(randomSpace);
        pipeInitialPositionX = Settings.VIRTUAL_WIDTH;
        pipePositionX = Settings.VIRTUAL_WIDTH;

        pipeBottom = new Pipe(pipeInitialPositionX, pipeBottomPositionY);
        pipeTop = new Pipe(pipeInitialPositionX, pipeTopPositionY);
        speedLevel = 4;
        worldState = WORLD_STATE_RUNNING;
        score = 0;
    }

    public void update(float deltaTime, float mY) {
        generateLevel(deltaTime);
        player.fly(deltaTime, mY);
        player.checkCollisionGround(ground);
        player.checkCollisionPipe(pipeBottom);
        player.checkCollisionPipe(pipeTop);

        gameOver();
    }

    public long getScore() {
        return score;
    }

    public boolean isGameOver() {
        return worldState == WORLD_STATE_GAME_OVER;
    }

    public float getBirdJump() {
        return player.getBirdJump();
    }

    public Bird getPlayer() {
        return player;
    }

    public Pipe getPipeTop() {
        return pipeTop;
    }

    public Pipe getPipeBottom() {
        return pipeBottom;
    }

    public Ground getGround() {
        return ground;
    }

    private void generateLevel(float deltaTime) {
        if (pipePositionX < -Pipe.PIPE_WIDTH) {
            pipePositionX = pipeInitialPositionX;

            randomSpace = Utils.getRandomNumberByInterval(400) - 200;

            pipeTopPositionY = getPipeBottomPositionY(randomSpace);
            pipeBottomPositionY = getPipeTopPositionY(randomSpace);

            speedLevel += speedLevel * deltaTime * 5;
            if (speedLevel > 10) {
                speedLevel = 10;
            }
            score++;
        }

        pipeTop.setPosition(pipePositionX, pipeTopPositionY);
        pipeBottom.setPosition(pipePositionX, pipeBottomPositionY);
        ground.update(speedLevel);
        pipePositionX = pipePositionX - speedLevel;
    }


    private float getPipeBottomPositionY(int randomSpace) {
        return Settings.VIRTUAL_HEIGHT / 2 + SPACE_BETWEEN_PIPES / 2 + randomSpace;
    }

    private float getPipeTopPositionY(int randomSpace) {
        return Settings.VIRTUAL_HEIGHT / 2 - Pipe.PIPE_HEIGHT - SPACE_BETWEEN_PIPES / 2 + randomSpace;
    }

    private void gameOver() {
        if (player.isFall()) {
            worldState = WORLD_STATE_GAME_OVER;
        }
    }
}