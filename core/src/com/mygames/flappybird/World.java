package com.mygames.flappybird;

import java.util.List;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import java.util.Random;

public class World
{
    public static final int WORLD_STATE_RUNNING    = 1;
    public static final int WORLD_STATE_NEXT_LEVEL = 2;
    public static final int WORLD_STATE_GAME_OVER  = 3;
    public int state;

    public final Bird bird;

    public final Ground ground;

    public Pipe pipeNorth;
    public Pipe pipeSouth;

    private float pipePosInitX;
    private float pipePosX;

    private float pipeSouthPosY;
    private float pipeNorthPosY;

    private float speedLevel;
    public static int spaceBetweenPipes = 200;
    public int randomSpaceBetweenPipes;
    private Random rand;

    public int score;

    public World(){

        bird  = new Bird(Settings.VIRTUAL_HEIGHT / 2);

        ground = new Ground();

        speedLevel = 4;
        rand = new Random();
        randomSpaceBetweenPipes = rand.nextInt(400) - 200;

        pipePosInitX = Settings.VIRTUAL_WIDTH;
        pipePosX = pipePosInitX;

        pipeNorthPosY = Settings.VIRTUAL_HEIGHT/2 + spaceBetweenPipes/2 + randomSpaceBetweenPipes;
        pipeSouthPosY = Settings.VIRTUAL_HEIGHT/2 - Pipe.PIPE_HEIGHT - spaceBetweenPipes/2 + randomSpaceBetweenPipes;

        pipeSouth = new Pipe(pipePosInitX, pipeSouthPosY);
        pipeNorth = new Pipe(pipePosInitX, pipeNorthPosY);

        this.state = WORLD_STATE_RUNNING;

        this.score = 0;
    }

    private void generateLevel(float deltaTime){

        if(pipePosX < -Pipe.PIPE_WIDTH){
            pipePosX = pipePosInitX;
            randomSpaceBetweenPipes = rand.nextInt(400) - 200;

            pipeNorthPosY = Settings.VIRTUAL_HEIGHT/2 + spaceBetweenPipes/2 + randomSpaceBetweenPipes;
            pipeSouthPosY = Settings.VIRTUAL_HEIGHT/2 - Pipe.PIPE_HEIGHT - spaceBetweenPipes/2 + randomSpaceBetweenPipes;

            speedLevel += speedLevel * deltaTime * 5;
            if(speedLevel > 10) speedLevel = 10;
            score++;
        }

        pipeNorth.setPosition(pipePosX, pipeNorthPosY);

        pipeSouth.setPosition(pipePosX, pipeSouthPosY);

        ground.update(speedLevel);

        pipePosX = pipePosX - speedLevel;

    }

    public void update(float deltaTime, float mY){
        generateLevel(deltaTime);

        // checkCollisions
        bird.checkColisionGround(ground);
        bird.checkColisionPipe(pipeSouth);
        bird.checkColisionPipe(pipeNorth);

        //checkGameOver
        gameOver();

        bird.fly(deltaTime, mY);
    }

    private void gameOver(){
        if(bird.getState() == Bird.BIRD_FALL)
            state = WORLD_STATE_GAME_OVER;
    }
}