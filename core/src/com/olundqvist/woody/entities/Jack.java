package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Constants;
import com.olundqvist.woody.util.Enums.Direction;
import com.olundqvist.woody.util.Enums.WalkState;
import com.olundqvist.woody.util.Enums.JumpState;
import com.olundqvist.woody.util.Enums.AnimState;
import com.olundqvist.woody.util.Utils;

import static com.olundqvist.woody.util.Constants.JACK_DAMPING;
import static com.olundqvist.woody.util.Constants.JACK_WIDTH;
import static com.olundqvist.woody.util.Constants.JUMP_SPEED;
import static com.olundqvist.woody.util.Enums.JumpState.GROUNDED;


public class Jack {
    public static final String TAG = Jack.class.getName();

    private Vector2 spawnLocation;
    public Vector2 position;
    public Vector2 velocity;
    private Direction facing;
    private WalkState walkState;
    private JumpState jumpState;
    private AnimState animationState;
    private long idleStartTime;
    private long jumpStartTime;
    private long runStartTime;
    private Direction direction;
    boolean xcollision, left, right;
    private Level level;
    private Rectangle bounds;


    public Jack(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        position = new Vector2();
        velocity = new Vector2();
        this.level = level;
        init();
    }

    public void render(SpriteBatch batch){
        TextureRegion region;
       float idleTimeSec = Utils.secondsSince(idleStartTime);

        switch (animationState){
            case RUN:
                region = Assets.instance.woodyAssets.runAnimation.getKeyFrame(idleTimeSec);
                break;
            case FALL:
                region = Assets.instance.woodyAssets.fallingAnimation.getKeyFrame(idleTimeSec);
                break;
            case GRAB:
                region = Assets.instance.woodyAssets.grabAnimation.getKeyFrame(idleTimeSec);
                break;
            case JUMP:
                region = Assets.instance.woodyAssets.jumpSprite;
                break;
            default:
                region = Assets.instance.woodyAssets.idleAnimation.getKeyFrame(idleTimeSec);
                break;
        }

        Utils.drawTextureRegion(batch, region, position, facing);
    }

    public void init(){
        position = spawnLocation;
        velocity.setZero();
        facing = Direction.RIGHT;
        // TODO: Review Animation start times
        idleStartTime = TimeUtils.nanoTime();
        animationState = AnimState.IDLE;
        bounds = new Rectangle(position.x, position.y, Constants.JACK_WIDTH, Constants.JACK_HEIGHT);
    }

    // TODO: Fix the landing bounce
    public void update(float delta){
        velocity.y -= Constants.GRAVITY;
        Vector2 bouncePosition = position;
        bounds.setPosition(position.mulAdd(velocity, delta));

        Rectangle collisionRect = level.collideY(velocity, bounds, delta);
        //Vertical Collision
        if(collisionRect != null){
            //Gdx.app.log(TAG, "Collision Y" + position.y);
            //falling
            if(velocity.y < 0){
                position.y = collisionRect.y + collisionRect.height;
                //Gdx.app.log(TAG, "Landed on platform");
                land();
            }else{ //jumping
                position.y = collisionRect.y - Constants.JACK_HEIGHT;
                velocity.y = 0;
            }
        }else if(position.y < 10){
            position.y = 10;
            land();
        }

        //Animation states
        animationState = AnimState.IDLE;
        if(velocity.y < 0){
            animationState = AnimState.FALL;
        }else if(velocity.y > 0){
            animationState = AnimState.JUMP;
        }
        handleInput(delta);

        collisionRect = level.collideX(velocity, bounds, delta);
        if(collisionRect != null){
            //Gdx.app.log(TAG, "Collision X " + position.x);
            //Moving left
            if(velocity.x < 0){
                position.x = collisionRect.x + collisionRect.width;
                velocity.x = 0;
            }else{//moving right
                position.x = collisionRect.x - (JACK_WIDTH);
                velocity.x = 0;
            }

        }

        dampen();
    }
    private void dampen(){
        velocity.x *= JACK_DAMPING;
    }

    private void handleInput(float delta){
        left = Gdx.input.isKeyPressed(Keys.LEFT);
        right = Gdx.input.isKeyPressed(Keys.RIGHT);

        //If both left and right keys are active dont move
        if (left && !right) {
            move(Direction.LEFT);
        } else if (right && !left) {
            move(Direction.RIGHT);
        }

        if(Gdx.input.isKeyPressed(Keys.UP)){
            if(jumpState == GROUNDED){
                jumpState = JumpState.JUMPING;
                velocity.y += JUMP_SPEED;
                Gdx.app.log(TAG, "Jumped");
            }
        }
        if(Gdx.input.isKeyJustPressed(Keys.X)){
            level.testTiles();
        }
    }

    private void move(Direction direction){
        switch(direction){
            case LEFT:
                velocity.x = -Constants.JACK_MOVE_SPEED;
                facing = Direction.LEFT;
                break;
            case RIGHT:
                velocity.x = Constants.JACK_MOVE_SPEED;
                facing = Direction.RIGHT;
                break;
        }

        if(jumpState == GROUNDED){
            animationState = AnimState.RUN;
        }
    }

    public Direction getFacing(){
        return facing;
    }
    public Vector2 getPosition(){
        return position;
    }
    public Vector2 getVelocity(){
        return velocity;
    }
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }
    public void land() {
        velocity.y = 0;
        jumpState = GROUNDED;
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }
}
