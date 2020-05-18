package com.mygames.flappybird;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.Gdx;

public class GameScreen extends ScreenAdapter {
    static final int GAME_READY     = 0;
    static final int GAME_RUNNING   = 1;
    static final int GAME_PAUSED    = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER      = 4;

    private int state;

    private FlappyBird game;

    private Viewport viewport;
    private OrthographicCamera camera;
    //private Vector3 touchPoint;
    private World world;
    private float jump_bird;

    public GameScreen(FlappyBird game){
        this.game = game;
        this.state = GAME_READY;

        world = new World();
        jump_bird = 0;

        camera = new OrthographicCamera();
        camera.position.set(Settings.VIRTUAL_WIDTH/2, Settings.VIRTUAL_HEIGHT/2, 0);
        viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
    }

    public void update (float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                //updatePaused();
                break;
            case GAME_LEVEL_END:
                //updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateReady () {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime){
        world.update(deltaTime, jump_bird);
        jump_bird++;

        if(Gdx.input.justTouched())
            jump_bird = -Bird.JUMP_BIRD;

        if(world.state == World.WORLD_STATE_GAME_OVER)
            state = GAME_OVER;
    }

    public void render(){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        renderBackground();
        renderObjects();
    }

    public void renderBackground(){
        this.game.batch.disableBlending();
        this.game.batch.begin();
        this.game.batch.draw(Assets.backgroundDayRegion, 0, 0, Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT);
        this.game.batch.end();
    }

    public void renderObjects(){
        this.game.batch.enableBlending();
        this.game.batch.begin();
        renderPipes();
        renderGround();
        renderBird();
        renderGameReady();
        this.game.batch.end();
    }

    public void renderPipes(){
        this.game.batch.draw(Assets.pipeNorthRegion, world.pipeNorth.bounds.x,
                world.pipeNorth.bounds.y,
                world.pipeNorth.bounds.getWidth(),
                world.pipeNorth.bounds.getHeight());

        this.game.batch.draw(Assets.pipeSouthRegion, world.pipeSouth.bounds.x,
                world.pipeSouth.bounds.y,
                world.pipeSouth.bounds.getWidth(),
                world.pipeSouth.bounds.getHeight());
    }

    public void renderBird(){
        this.game.batch.draw(Assets.bird[(int) world.bird.indexTexture], world.bird.bounds.x,
                world.bird.bounds.y,
                world.bird.bounds.getWidth(),
                world.bird.bounds.getHeight());
    }

    public void renderGround(){
        this.game.batch.draw(Assets.foregroundRegion, world.ground.bounds.x,
                world.ground.bounds.y,
                world.ground.GROUND_WIDTH,
                world.ground.GROUND_HEIGHT);
    }

    public void renderGameReady(){
        if(this.state == GAME_READY){
            this.game.batch.draw(Assets.tapToInitRegion,
                    Settings.VIRTUAL_WIDTH /2 - 125,
                    Settings.VIRTUAL_HEIGHT/2 - 50, 250, 200);
            this.game.batch.draw(Assets.getReadyRegion,
                    Settings.VIRTUAL_WIDTH /2 - 200,
                    Settings.VIRTUAL_HEIGHT/2 + 200, 400, 200);
        }
    }

    private void updateGameOver(){
        game.setScreen(new GameOverScreen(game, world.score));
    }

    @Override
    public void render(float delta)
    {
        update(delta);
        render();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

}