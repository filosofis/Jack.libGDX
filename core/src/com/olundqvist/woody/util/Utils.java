package com.olundqvist.woody.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static com.olundqvist.woody.util.Enums.Direction;

public class Utils {

    public static void drawTextureRegion(Batch batch, TextureRegion region, Vector2 position) {
        drawTextureRegion(batch, region, position.x, position.y);
    }
    public static void drawTextureRegion(Batch batch,
                                         TextureRegion region,
                                         Vector2 position,
                                         Enums.Direction direction) {
        boolean flipX = false;
        if(direction == Direction.LEFT){
            flipX = true;
        }

        batch.draw(
                region.getTexture(),
                position.x,
                position.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                flipX,
                false);
    }

    public static void drawTextureRegion(Batch batch, TextureRegion region, Vector2 position, Vector2 offset) {
        drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y);
    }

    public static void drawTextureRegion(Batch batch, TextureRegion region, float x, float y) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static float secondsSince(long timeNanos) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos);
    }

    public static float calculateOtherDimension(Enums.WH wh, float oneDimen, TextureRegion region){
        float result=0;
        switch (wh){
            // height_specified
            case WIDTH:
                result = region.getRegionHeight()*(oneDimen/region.getRegionWidth());
                break;
            // width_specified
            case HEIGHT:
                result = region.getRegionWidth()*(oneDimen/region.getRegionHeight());
                break;

        }

        return result;

    }

    /**
     * calculate new width/height maintaining aspect ratio
     * @param wh what oneDimen represents
     * @param oneDimen either width or height
     * @param originalWidth the original width
     * @param originalHeight the original height
     * @return if oneDimen is width then height else width
     */
    public static float calculateOtherDimension(Enums.WH wh, float oneDimen, float originalWidth, float originalHeight){
        float result=0;
        switch (wh){
            case WIDTH:
                result = originalHeight*(oneDimen/originalWidth);
                break;
            case HEIGHT:
                result = originalWidth*(oneDimen/originalHeight);
                break;
        }

        return result;

    }
}
