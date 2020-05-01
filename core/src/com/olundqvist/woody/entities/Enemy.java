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
import static com.olundqvist.woody.util.Constants.JACK_WIDTH;
import static com.olundqvist.woody.util.Enums.Direction.LEFT;
import static com.olundqvist.woody.util.Enums.Direction.RIGHT;

//:TODO Handle damage
//:TODO More "ai"
public class Enemy {
    public static final String TAG = Jack.class.getName();
    private Vector2 position;
    private Vector2 velocity;
    private Enums.AnimState animState;
    private Enums.EnemyState actionState;
    private Rectangle bounds;
    private long idleStartTime;
    private long actionStartTime;
    private Enums.Direction direction;
    private float idleTimeSec;
    private Vector2 spawnPosition;

    public Enemy(Vector2 spawnLocation){
        position = new Vector2();
        velocity = new Vector2(10, 0);
        bounds = new Rectangle(position.x, position.y, ANDRO_WIDTH,ANDRO_height);
        spawnPosition = position;
        init(spawnLocation);
    }

    public void init(Vector2 spawnLocation){
        position = spawnLocation;
        animState = Enums.AnimState.IDLE;
        idleStartTime = TimeUtils.nanoTime();
        actionState = Enums.EnemyState.IDLE;
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
                region = Assets.instance.enemyAssets.androIdleAnimation.getKeyFrame(
                        idleTimeSec
                );
                break;
        }
        Utils.drawTextureRegion(
                batch,
                region,
                position,
                direction
        );
    }

    private void  move(float delta){
        if(direction == LEFT){
            position.mulAdd(velocity, -delta);
        }else{
            position.mulAdd(velocity, delta);
        }
    }

    public void update(float delta){
        switch (actionState){
            case TURNING:
                turn();
                break;
            case MOVING:
                move(delta);
                break;
            default:
                animState = Enums.AnimState.IDLE;
        }
    }

    public void turn(){
        actionState = Enums.EnemyState.TURNING;
        animState = Enums.AnimState.TURN;
        if(Utils.secondsSince(actionStartTime) > 0.5){
            actionState = Enums.EnemyState.MOVING;
            animState = Enums.AnimState.IDLE;
        }
    }

    public void facing(Vector2 focus){
        //Check if jack has passed the middle of the enemy
        if(focus.x < (position.x + (bounds.width - JACK_WIDTH)/2)){
            if(direction == RIGHT){
                actionStartTime = TimeUtils.nanoTime();
                actionState = Enums.EnemyState.TURNING;
                System.out.println("Turning Left");
                turn();
            }
            direction = LEFT;
        }else{
            if(direction == LEFT){
                actionStartTime = TimeUtils.nanoTime();
                actionState = Enums.EnemyState.IDLE;
                System.out.println("Turning Right");
                turn();
            }
            direction = RIGHT;
        }
    }
    public Vector2 getPosition() {
        return position;
    }
}
