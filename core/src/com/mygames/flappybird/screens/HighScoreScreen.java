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

public class HighScoreScreen extends ScreenAdapter {
    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Vector3 touchPoint;
    private final GameObject buttonMenu;
    private final long[] records;

    public HighScoreScreen(FlappyBird game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.position.set(Settings.VIRTUAL_WIDTH / 2, Settings.VIRTUAL_HEIGHT / 2, 0);
        this.viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
        this.buttonMenu = new GameObject(Settings.BUTTON_MENU_POS_X, Settings.BUTTON_MENU_POS_Y,
                Settings.BUTTON_MENU_WIDTH, Settings.BUTTON_MENU_HEIGHT);
        this.touchPoint = new Vector3();
        this.records = new Settings().load();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonMenu.getBounds().contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    private String getRecords() {
        return "1.    " + records[0] + "Pts\n" + "2.    " + records[1] + "Pts\n" + "3.    " + records[2] + "Pts\n";
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

        // draw highScores

        game.getGameBatch().enableBlending();
        game.getGameBatch().begin();

        Assets.font.draw(game.getGameBatch(), getRecords(), Settings.VIRTUAL_WIDTH/2 - 100, 800);

        game.getGameBatch().draw(Assets.menuMainRegion, buttonMenu.getPosition().x,
                buttonMenu.getPosition().y,
                buttonMenu.getBounds().getWidth(),
                buttonMenu.getBounds().getHeight());

        game.getGameBatch().end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
