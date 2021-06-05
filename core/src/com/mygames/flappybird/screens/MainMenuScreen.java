package com.mygames.flappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygames.flappybird.FlappyBird;
import com.mygames.flappybird.config.Assets;
import com.mygames.flappybird.config.Settings;
import com.mygames.flappybird.objects.GameObject;

public class MainMenuScreen extends ScreenAdapter {
    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final GameObject playBounds;
    private final GameObject highScoresBounds;
    private final Vector3 touchPoint;

    public MainMenuScreen(FlappyBird game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.position.set(Settings.VIRTUAL_WIDTH / 2, Settings.VIRTUAL_HEIGHT / 2, 0);
        this.viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
        this.playBounds = new GameObject(Settings.VIRTUAL_WIDTH / 2 - 100, Settings.VIRTUAL_HEIGHT / 2 - 50,
                200, 100);
        this.highScoresBounds = new GameObject(Settings.VIRTUAL_WIDTH / 2 - 100, Settings.VIRTUAL_HEIGHT / 2 - 180,
                200, 100);
        this.touchPoint = new Vector3();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.getBounds().contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
            }
            if (highScoresBounds.getBounds().contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new HighScoreScreen(game));
            }

        }

    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getGameBatch().setProjectionMatrix(camera.combined);

        game.getGameBatch().disableBlending();
        game.getGameBatch().begin();
        game.getGameBatch().draw(Assets.backgroundDayRegion, 0, 0, Settings.VIRTUAL_WIDTH,
                Settings.VIRTUAL_HEIGHT);
        game.getGameBatch().end();

        game.getGameBatch().enableBlending();
        game.getGameBatch().begin();
        game.getGameBatch().draw(Assets.logo, Settings.VIRTUAL_WIDTH / 2 - 200,
                Settings.VIRTUAL_HEIGHT / 2 + 200, 400, 250);

        game.getGameBatch().draw(Assets.playRegion, playBounds.getPosition().x,
                playBounds.getPosition().y,
                playBounds.getBounds().getWidth(),
                playBounds.getBounds().getHeight());

        game.getGameBatch().draw(Assets.highScoreRegion,
                highScoresBounds.getPosition().x,
                highScoresBounds.getPosition().y,
                highScoresBounds.getBounds().getWidth(),
                highScoresBounds.getBounds().getHeight());

        game.getGameBatch().end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void pause() {
        //Settings.save();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


}