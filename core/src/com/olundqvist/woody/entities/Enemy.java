package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Enums;
import com.olundqvist.woody.util.Utils;
import static com.olundqvist.woody.util.Constants.JACK_WIDTH;
import static com.olundqvist.woody.util.Enums.Direction.LEFT;
import static com.olundqvist.woody.util.Enums.Direction.RIGHT;

//:TODO Handle damage
//:TODO More "ai"
public class Enemy {
    public static final String TAG = Enemy.class.getName();
    private Vector2 position;
    private Vector2 velocity;
    private Enums.AnimState animState;
    private Enums.EnemyState actionState;
    private Enums.Direction direction;
    private Enums.EnemyType enemyType;
    private Rectangle bounds;
    private long idleStartTime;
    private long actionStartTime;
    private float idleTimeSec;
    private int hitpoints;

    public Enemy(Vector2 spawnLocation, Enums.EnemyType enemyType, Rectangle bounds){
        position = new Vector2();
        this.enemyType = enemyType;
        velocity = new Vector2(10, 0);
        hitpoints = 3;
        this.bounds = bounds;
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
        switch(enemyType){
            case ANDROMALIUS:
                region = renderAndromalius();
                break;
            default:
                region = renderHellHound();
        }

        Utils.drawTextureRegion(
                batch,
                region,
                position,
                direction
        );
    }

    private TextureAtlas.AtlasRegion renderAndromalius(){
        switch(animState){
            case TURN:
                return Assets.instance.andromaliusAssets.androTurnAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime)
                );
            default:
                return Assets.instance.andromaliusAssets.androIdleAnimation.getKeyFrame(
                        idleTimeSec
                );
        }
    }
    private TextureAtlas.AtlasRegion renderHellHound(){
        return Assets.instance.hellHoundAssets.idleAnimation.getKeyFrame(idleTimeSec);
    }

    private void  move(float delta){
        if(direction == LEFT){
            position.mulAdd(velocity, -delta);
        }else{
            position.mulAdd(velocity, delta);
        }
        bounds.setPosition(position);
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

    public void hit(){
        hitpoints--;
        Gdx.app.log(TAG, "Hitpoints: " + hitpoints);
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
                turn();
            }
            direction = LEFT;
        }else{
            if(direction == LEFT){
                actionStartTime = TimeUtils.nanoTime();
                actionState = Enums.EnemyState.IDLE;
                turn();
            }
            direction = RIGHT;
        }
    }
    public Vector2 getPosition() {
        return position;
    }
    public int getHitpoints(){
        return hitpoints;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "position=" + position +
                ", direction=" + direction +
                ", enemyType=" + enemyType +
                ", bounds=" + bounds +
                ", hitpoints=" + hitpoints +
                '}';
    }
}
