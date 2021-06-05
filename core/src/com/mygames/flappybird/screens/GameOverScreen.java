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

public class GameOverScreen extends ScreenAdapter {
    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final GameObject buttonMenu;
    private final GameObject scoreRect;
    private final Vector3 touchPoint;
    private final Long score;
    private final Settings settings;

    public GameOverScreen(FlappyBird game, Long score) {
        this.game = game;
        this.score = score;
        this.camera = new OrthographicCamera();
        this.camera.position.set(Settings.VIRTUAL_WIDTH / 2, Settings.VIRTUAL_HEIGHT / 2, 0);
        this.viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
        this.scoreRect = new GameObject(Settings.SCORE_RECT_POS_X, Settings.SCORE_REACT_POS_Y,
                Settings.SCORE_RECT_WIDTH, Settings.SCORE_RECT_HEIGHT);
        this.buttonMenu = new GameObject(Settings.BUTTON_MENU_POS_X, Settings.BUTTON_MENU_POS_Y,
                Settings.BUTTON_MENU_WIDTH, Settings.BUTTON_MENU_HEIGHT);
        this.touchPoint = new Vector3();

        this.settings = new Settings();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonMenu.getBounds().contains(touchPoint.x, touchPoint.y)) {
                settings.saveRecord(score);
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
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
        game.getGameBatch().draw(Assets.backgroundNightRegion, 0, 0, Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT);
        game.getGameBatch().end();

        game.getGameBatch().enableBlending();
        game.getGameBatch().begin();
        game.getGameBatch().draw(Assets.gameOverRegion, Settings.VIRTUAL_WIDTH / 2 - 200,
                Settings.VIRTUAL_HEIGHT / 2 + 200, 400, 200);

        game.getGameBatch().draw(Assets.scoreRegion, scoreRect.getPosition().x,
                scoreRect.getPosition().y, scoreRect.getBounds().getWidth(),
                scoreRect.getBounds().getHeight());

        Assets.font.draw(game.getGameBatch(), score + "", scoreRect.getBounds().x + 290,
                scoreRect.getBounds().y + 220);

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