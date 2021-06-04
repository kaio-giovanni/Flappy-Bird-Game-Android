package com.mygames.flappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygames.flappybird.FlappyBird;
import com.mygames.flappybird.config.Assets;
import com.mygames.flappybird.config.Settings;
import com.mygames.flappybird.entities.World;

public class GameScreen extends ScreenAdapter {
    private static final int GAME_READY = 0;
    private static final int GAME_RUNNING = 1;
    private static final int GAME_PAUSED = 2;
    private static final int GAME_LEVEL_END = 3;
    private static final int GAME_OVER = 4;
    private final FlappyBird game;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final World world;
    private float jump_bird;
    private int gameState;

    public GameScreen(FlappyBird game) {
        this.game = game;
        gameState = GAME_READY;
        world = new World();
        jump_bird = 0;
        camera = new OrthographicCamera();
        camera.position.set(Settings.VIRTUAL_WIDTH / 2, Settings.VIRTUAL_HEIGHT / 2, 0);
        viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
    }

    private void update(float deltaTime) {
        if (deltaTime > 0.1f) {
            deltaTime = 0.1f;
        }

        switch (gameState) {
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

    private void updateReady() {
        if (Gdx.input.justTouched()) {
            gameState = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime) {
        world.update(deltaTime, jump_bird);
        jump_bird++;

        if (Gdx.input.justTouched()) {
            jump_bird = -1 * world.getBirdJump();
        }

        if (world.isGameOver()) {
            gameState = GAME_OVER;
        }

    }

    private void updateGameOver() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new GameOverScreen(game, world.getScore()));
            }
        }, 1.5f);
    }

    private void render() {
        camera.update();
        game.getGameBatch().setProjectionMatrix(camera.combined);
        renderBackground();
        renderObjects();
    }

    private void renderBackground() {
        game.getGameBatch().disableBlending();
        game.getGameBatch().begin();
        game.getGameBatch().draw(Assets.backgroundDayRegion, 0, 0, Settings.VIRTUAL_WIDTH,
                Settings.VIRTUAL_HEIGHT);
        game.getGameBatch().end();
    }

    private void renderObjects() {
        game.getGameBatch().enableBlending();
        game.getGameBatch().begin();
        renderPipes();
        renderGround();
        renderBird();
        renderGameReady();
        game.getGameBatch().end();
    }

    private void renderPipes() {
        game.getGameBatch().draw(Assets.pipeNorthRegion, world.getPipeNorth().getBounds().x,
                world.getPipeNorth().getBounds().y,
                world.getPipeNorth().getBounds().getWidth(),
                world.getPipeNorth().getBounds().getHeight());

        game.getGameBatch().draw(Assets.pipeSouthRegion, world.getPipeSouth().getBounds().x,
                world.getPipeSouth().getBounds().y,
                world.getPipeSouth().getBounds().getWidth(),
                world.getPipeSouth().getBounds().getHeight());
    }

    private void renderBird() {
        game.getGameBatch().draw(Assets.bird[(int) world.getBird().getIndexTexture()], world.getBird().getBounds().x,
                world.getBird().getBounds().y,
                world.getBird().getBounds().getWidth(),
                world.getBird().getBounds().getHeight());
    }

    private void renderGround() {
        game.getGameBatch().draw(Assets.foregroundRegion, world.getGround().getBounds().x,
                world.getGround().getBounds().y,
                world.getGround().getBounds().getWidth(),
                world.getGround().getBounds().getHeight());
    }

    private void renderGameReady() {
        if (gameState == GAME_READY) {
            game.getGameBatch().draw(Assets.tapToInitRegion,
                    Settings.VIRTUAL_WIDTH / 2 - 125,
                    Settings.VIRTUAL_HEIGHT / 2 - 50, 250, 200);
            game.getGameBatch().draw(Assets.getReadyRegion,
                    Settings.VIRTUAL_WIDTH / 2 - 200,
                    Settings.VIRTUAL_HEIGHT / 2 + 200, 400, 200);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}