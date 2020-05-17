package com.mygames.flappybird;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen extends ScreenAdapter
{
    private FlappyBird game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameObject playBounds;
    private GameObject highscoresBounds;
    private Vector3 touchPoint;

    float virtual_width = Settings.VIRTUAL_WIDTH;
    float virtual_height = Settings.VIRTUAL_HEIGHT;


    public MainMenuScreen(FlappyBird game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        camera.position.set(virtual_width / 2, virtual_height / 2, 0);
        viewport = new StretchViewport(virtual_width, virtual_height, camera);

        playBounds       = new GameObject(virtual_width / 2 - 100,virtual_height / 2 - 50, 200, 100);
        highscoresBounds = new GameObject(virtual_width / 2 - 100, virtual_height/ 2 - 180, 200, 100);

        touchPoint = new Vector3();
    }

    public void update()
    {
        if (Gdx.input.justTouched())
        {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.bounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (highscoresBounds.bounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                //game.setScreen(new HighScoreScreen(game));
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
        game.batch.draw(Assets.backgroundDayRegion, 0, 0, virtual_width, virtual_height);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.logo, virtual_width/2 - 200, virtual_height/2 + 200, 400, 250);

        game.batch.draw(Assets.playRegion, playBounds.position.x,
                playBounds.position.y,
                playBounds.bounds.getWidth(),
                playBounds.bounds.getHeight());

        game.batch.draw(Assets.highScoreRegion,
                highscoresBounds.position.x,
                highscoresBounds.position.y,
                highscoresBounds.bounds.getWidth(),
                highscoresBounds.bounds.getHeight()) ;
        game.batch.end();
    }

    @Override
    public void render(float delta)
    {
        update();
        draw();
    }

    @Override
    public void pause()
    {
        //Settings.save();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }


}