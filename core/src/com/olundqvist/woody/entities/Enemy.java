package com.olundqvist.woody.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Enums;
import com.olundqvist.woody.util.Utils;

public class Enemy {
    public static final String TAG = Jack.class.getName();

    private Vector2 position;
    private Vector2 velocity;
    private Enums.AnimState animState;
    private Rectangle bounds;
    private long idleStartTime;

    public Enemy(Vector2 spawnLocation){
        position = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle(position.x, position.y, 16,16);
        init(spawnLocation);
    }

    public void init(Vector2 spawnLocation){
        position = spawnLocation;
        animState = Enums.AnimState.IDLE;
        idleStartTime = TimeUtils.nanoTime();
    }

    void render(Batch batch){
        float idleTimeSec = Utils.secondsSince(idleStartTime);
        TextureAtlas.AtlasRegion region =
                Assets.instance.enemyAssets.idleAnimation.getKeyFrame(idleTimeSec);
        Utils.drawTextureRegion(
                batch,
                region,
                position,
                Enums.Direction.RIGHT
        );
    }

    public void update(float delta){
        
    }
}
