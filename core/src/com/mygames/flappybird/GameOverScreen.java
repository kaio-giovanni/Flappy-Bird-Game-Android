package com.mygames.flappybird;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends ScreenAdapter
{
    private FlappyBird game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameObject buttonMenu;
    private GameObject scoreRect;
    private Vector3 touchPoint;
    private int score;

    public GameOverScreen(FlappyBird game, int score){
        this.game = game;
        this.score = score;
        this.camera = new OrthographicCamera();
        this.camera.position.set(Settings.VIRTUAL_WIDTH/2, Settings.VIRTUAL_HEIGHT/2, 0);
        this.viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
        this.scoreRect = new GameObject(Settings.VIRTUAL_WIDTH/2 - 200, Settings.VIRTUAL_HEIGHT/2 - 150, 400, 300);
        this.buttonMenu = new GameObject(Settings.VIRTUAL_WIDTH/2 - 100, Settings.VIRTUAL_HEIGHT/2 - 50, 200, 100);
        this.touchPoint = new Vector3();
    }

    public void update(){
        if (Gdx.input.justTouched())
        {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonMenu.bounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    public void draw()
    {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundNightRegion, 0, 0, Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.gameOverRegion, Settings.VIRTUAL_WIDTH/2 - 150, Settings.VIRTUAL_HEIGHT/2 + 200, 300, 200);

        game.batch.draw(Assets.scoreRegion, scoreRect.position.x,
                scoreRect.position.y,
                scoreRect.bounds.getWidth(),
                scoreRect.bounds.getHeight());

        Assets.font.draw(game.batch, score+"", scoreRect.bounds.x + 290, scoreRect.bounds.y + 220);

        game.batch.draw(Assets.menuMainRegion, buttonMenu.position.x,
                buttonMenu.position.y,
                buttonMenu.bounds.getWidth(),
                buttonMenu.bounds.getHeight());
        game.batch.end();
    }

    @Override
    public void render(float delta)
    {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

}