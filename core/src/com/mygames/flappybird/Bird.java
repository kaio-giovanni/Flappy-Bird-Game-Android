package com.mygames.flappybird;
import com.badlogic.gdx.math.Vector2;

public class Bird extends DynamicGameObject
{
    public static final int BIRD_FLYING = 1;
    public static final int BIRD_FALL   = 2;
    private int state;

    public static final float BIRD_WIDTH  = 70;
    public static final float BIRD_HEIGHT = 65;
    public static final float BIRD_POSX   = 60;

    public static final float JUMP_BIRD = 12f;

    public float indexTexture;

    public Bird(float y){
        super(BIRD_POSX, y, BIRD_WIDTH, BIRD_HEIGHT);

        this.state = BIRD_FLYING;

        this.indexTexture = 0;

        velocity.set(0, y);
    }

    public void fly(float deltaTime, float mY){
        if(state == BIRD_FLYING){
            velocity.y = velocity.y - mY;

            setPosition(BIRD_POSX, velocity.y);

            indexTexture += deltaTime * 8;

            if(indexTexture > 2)
                indexTexture = 0;
        }
    }

    /* crashed to the ground */
    public void checkColisionGround(Ground ground){
        if(bounds.overlaps(ground.bounds)){
            state = BIRD_FALL;
        }
    }

    /* collision check with the pipe */
    public void checkColisionPipe(Pipe pipe){
        if(bounds.overlaps(pipe.bounds))
            state = BIRD_FALL;
    }

    /* get state of bird */
    public int getState(){
        return this.state;
    }
}