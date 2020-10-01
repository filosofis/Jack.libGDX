package com.olundqvist.woody;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.olundqvist.woody.util.Constants;

//:TODO Improve the font
//:TODO Sprites representing Lives
class HUD {
    public final Viewport viewport;
    final BitmapFont font;

    public HUD(){
        this.viewport = new ExtendViewport(Constants.HUD_WIDTH, Constants.HUD_HIEHGT);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int lives, int score){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        final String hudString = "Score: \n Lives: ";
        font.draw(batch, hudString, 5, viewport.getWorldHeight()-5);
        batch.end();
    }
}
