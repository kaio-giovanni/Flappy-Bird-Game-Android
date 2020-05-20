package com.mygames.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HighScoreScreen extends ScreenAdapter {
    private FlappyBird game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Vector3 touchPoint;
    //private int[] highScores;

    public HighScoreScreen(FlappyBird game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.position.set(Settings.VIRTUAL_WIDTH/2, Settings.VIRTUAL_HEIGHT/2, 0);
        this.viewport = new StretchViewport(Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT, camera);
        this.touchPoint = new Vector3();
        //this.highScores = getHighScores(): int[]
    }

    public void update(){
        if (Gdx.input.justTouched())
        {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            // button back main menu touched
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundDayRegion, 0, 0, Settings.VIRTUAL_WIDTH, Settings.VIRTUAL_HEIGHT);
        game.batch.end();

        // draw highScores
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
