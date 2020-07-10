package com.olundqvist.woody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.olundqvist.woody.entities.Jack;
import com.olundqvist.woody.entities.Level;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.ChaseCam;
import com.olundqvist.woody.util.Constants;

import static com.olundqvist.woody.util.Constants.BACKGROUND_COLOR;

public class GameplayScreen extends ScreenAdapter {
    public static final String TAG = GameplayScreen.class.getName();
    private SpriteBatch batch;
    private ChaseCam chaseCam;
    private Level level;

    @Override
    public void show(){
        Gdx.graphics.setWindowedMode(1280, 720);
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        chaseCam = new ChaseCam();
        startLevel();
    }

    @Override
    public void render(float delta) {
        level.update(delta);
        chaseCam.update(delta);
        Gdx.gl.glClearColor(
                BACKGROUND_COLOR.r,
                BACKGROUND_COLOR.g,
                BACKGROUND_COLOR.b,
                BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.render();
    }

    @Override
    public void resize(int width, int height) {
        level.viewport.update(width, height, true);
        chaseCam.camera = level.viewport.getCamera();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    private void startLevel(){
        level = new Level();
        //level.setJack(new Jack(Constants.DEFAULT_SPAWN_LOCATION, level));
        chaseCam.camera = level.viewport.getCamera();
        chaseCam.target = level.getJack();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
