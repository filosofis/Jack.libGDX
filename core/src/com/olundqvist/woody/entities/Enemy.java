package com.olundqvist.woody.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Enums;
import com.olundqvist.woody.util.Utils;

import static com.olundqvist.woody.util.Constants.ANDRO_WIDTH;
import static com.olundqvist.woody.util.Constants.ANDRO_height;

//:TODO Handle damage
//:TODO More "ai"
public class Enemy {
    public static final String TAG = Jack.class.getName();
    private Vector2 position;
    private Vector2 velocity;
    private Enums.AnimState animState;
    private Enums.ActionState actionState;
    private Rectangle bounds;
    private long idleStartTime;
    private long actionStartTime;
    private Enums.Direction direction;
    private float idleTimeSec;

    public Enemy(Vector2 spawnLocation){
        position = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle(position.x, position.y, ANDRO_WIDTH,ANDRO_height);
        init(spawnLocation);
    }

    public void init(Vector2 spawnLocation){
        position = spawnLocation;
        animState = Enums.AnimState.IDLE;
        idleStartTime = TimeUtils.nanoTime();
        actionState = Enums.ActionState.GROUNDED;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    void render(Batch batch){
        TextureAtlas.AtlasRegion region;
        idleTimeSec = Utils.secondsSince(idleStartTime);
        switch(animState){
            case TURN:
                region = Assets.instance.enemyAssets.androTurnAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime)
                );
                break;
            default:
                region = Assets.instance.enemyAssets.androIdleAnimation.getKeyFrame(idleTimeSec);
                break;
        }
        Utils.drawTextureRegion(
                batch,
                region,
                position,
                direction
        );
    }

    public void update(float delta){
        switch (actionState){
            case TURNING:
                turn();
                break;
            default:
                animState = Enums.AnimState.IDLE;
        }
    }

    public void turn(){
        actionState = Enums.ActionState.TURNING;
        animState = Enums.AnimState.TURN;
        if(Utils.secondsSince(actionStartTime) > 0.5){
            actionState = Enums.ActionState.GROUNDED;
        }
    }
    public void facing(Vector2 focus){
        if(focus.x < position.x){
            if(direction == Enums.Direction.RIGHT){
                actionStartTime = TimeUtils.nanoTime();
                actionState = Enums.ActionState.TURNING;
                System.out.println("Turning Left");
                turn();
            }
            direction = Enums.Direction.LEFT;
        }else{
            if(direction == Enums.Direction.LEFT){
                actionStartTime = TimeUtils.nanoTime();
                actionState = Enums.ActionState.TURNING;
                System.out.println("Turning Right");
                turn();
            }
            direction = Enums.Direction.RIGHT;
        }
    }

    public Vector2 getPosition() {
        return position;
    }
}
