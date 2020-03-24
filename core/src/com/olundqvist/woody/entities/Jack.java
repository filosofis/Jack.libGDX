package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import static com.olundqvist.woody.util.Constants.RVOS_WIDTH;
import static com.olundqvist.woody.util.Enums.Direction.LEFT;
import static com.olundqvist.woody.util.Enums.Direction.RIGHT;
import static com.olundqvist.woody.util.Enums.JumpState.CLIMBING;
import static com.olundqvist.woody.util.Enums.JumpState.FALLING;
import static com.olundqvist.woody.util.Enums.JumpState.GRABING;
import static com.olundqvist.woody.util.Enums.JumpState.GROUNDED;


public class Jack {
    private static final String TAG = Jack.class.getName();

    private Vector2 spawnLocation;
    private Vector2 position;
    private Vector2 velocity;
    private Direction facing;
    private WalkState walkState;
    private JumpState jumpState;
    private AnimState animationState;
    private long idleStartTime;
    private long actionStartTime;
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

    void render(Batch batch){
        TextureRegion region;
       float idleTimeSec = Utils.secondsSince(idleStartTime);
        switch (animationState){
            case RUN:
                region = Assets.instance.rvrosAssets.runAnimation.getKeyFrame(idleTimeSec);
                break;
            case FALL:
                region = Assets.instance.rvrosAssets.fallAnimation.getKeyFrame(idleTimeSec);
                break;
            case GRAB:
                region = Assets.instance.rvrosAssets.grabAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                break;
            case JUMP:
                region = Assets.instance.rvrosAssets.jumpSprite;
                break;
            case CLIMB:
                region = Assets.instance.rvrosAssets.climbAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                break;
            default:
                region = Assets.instance.rvrosAssets.idleAnimation.getKeyFrame(idleTimeSec);
                break;
        }
        Utils.drawTextureRegion(batch, region, position, facing);
    }

    public void init(){
        position = spawnLocation;
        velocity.setZero();
        facing = RIGHT;
        // TODO: Review Animation start times
        idleStartTime = TimeUtils.nanoTime();
        animationState = AnimState.IDLE;
        jumpState = FALLING;
        bounds = new Rectangle(position.x, position.y, Constants.JACK_WIDTH, Constants.JACK_HEIGHT);
    }

    private void updateState(){
        if (velocity.y < 0) {
            animationState = AnimState.FALL;
        } else if (velocity.y > 0) {
            animationState = AnimState.JUMP;
        } else {
            animationState = AnimState.IDLE;
        }
        switch (jumpState) {
            case GROUNDED:
                if (Math.abs(velocity.x) > 0) {
                    animationState = AnimState.RUN;
                }
                break;
            case GRABING:
                animationState = AnimState.GRAB;
                break;
            case CLIMBING:
                animationState = AnimState.CLIMB;
                break;
        }
    }

    void update(float delta){
        handleInput();
        velocity.y -= Constants.GRAVITY;
        velocity.scl(delta);
        bounds.setPosition(position);
        switch(jumpState){
            case GRABING:
                velocity.setZero();
                break;
            case CLIMBING:
                climb(delta);
                break;
            case FALLING:
                collisionCheck();
                break;
            case GROUNDED:
                collisionCheck();
                break;
        }
        updateState();
        position.add(velocity);
        velocity.scl(1/delta);
        dampen();
    }

    private void ledge(Rectangle rect){
        Gdx.app.log(TAG, "Rectangle " + (int)rect.y );
        Gdx.app.log(TAG, "Position "  + (int)(position.y+16) );
        Gdx.app.log(TAG, "------------------");
        //Grab ledge if the top of the corner is below jacks height
        //and within 4 units reach
        if (((position.y + 16) > (rect.y)) &&
                ((position.y + 12) < (rect.y))
        ) {
            switch (facing) {
                case LEFT:
                    position.x = rect.x + 15;
                    break;
                case RIGHT:
                    //Workaround for better appearance of mirrored grab animation
                    position.x = rect.x - 14;
                    break;
            }
            velocity.setZero();
            position.y = rect.y - 16;
            jumpState = GRABING;
            actionStartTime = TimeUtils.nanoTime();
        }
    }

    private void collisionCheck(){
        //Horizontal Collision check
        bounds.x += velocity.x;
        Rectangle collisionRect = level.collideX(velocity, bounds);
        if(collisionRect != null){
            Rectangle corner = level.isCorner(collisionRect);
            if(corner != null){
                ledge(corner);
            }
            velocity.x = 0;
        }
        bounds.x = position.x;

        //Vertical collision check
        if(Math.abs(velocity.y)>0) {
            bounds.y += velocity.y;
            collisionRect = level.collideY(velocity, bounds);
            if (collisionRect != null) {
                if (velocity.y < 0) {
                    position.y = collisionRect.y + collisionRect.height;
                    land();
                } else { //jumping
                    position.y = collisionRect.y - Constants.JACK_HEIGHT;
                    velocity.y = 0;
                }
            } else if (bounds.y < 10) {
                position.y = 10;
                land();
            }
        }
    }

    private void land() {
        velocity.y = 0;
        jumpState = GROUNDED;
    }

    private void dampen(){
        if(Math.abs(velocity.x) < 1){
            velocity.x = 0;
        }else{
            velocity.x *= JACK_DAMPING;
        }
    }

    private void handleInput(){
        left = Gdx.input.isKeyPressed(Keys.LEFT);
        right = Gdx.input.isKeyPressed(Keys.RIGHT);

        if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
            level.debug = true;
        }

        switch(jumpState){
            case GROUNDED:
            case FALLING:
                //Allow air control?
                if (left && !right) {
                    move(LEFT);
                } else if (right && !left) {
                    move(RIGHT);
                }
                break;
            case GRABING:
                if(facing == RIGHT && left){
                    jumpState = FALLING;
                    Gdx.app.log(TAG, "Let go");
                }else if(facing == LEFT && right){
                    Gdx.app.log(TAG,"let go");
                }
                break;
        }

        //If both left and right keys are active dont move
        if(Gdx.input.isKeyPressed(Keys.UP)){
            switch (jumpState){
                case GROUNDED:
                    jumpState = FALLING;
                    velocity.y += JUMP_SPEED;
                    Gdx.app.log(TAG, "Jumped");
                    break;
                case GRABING:
                    switch(facing){
                        case LEFT:
                            initClimb();
                            Gdx.app.log(TAG, "Climbed Left");
                            break;
                        case RIGHT:
                            initClimb();
                            Gdx.app.log(TAG, "Climbed Right");
                            break;
                    }
                    break;
            }
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            switch(jumpState){
                case GROUNDED:
                    Gdx.app.log(TAG, "Ducking?");
                    break;
                case GRABING:
                    jumpState = FALLING;
                    Gdx.app.log(TAG, "Let go of ledge");
                    break;
            }
        }
    }
    private void climb(float delta){
        position.add(0,60*delta);
        if(Utils.secondsSince(actionStartTime) > 0.5){
            Gdx.app.log(TAG, "Climb ended");
            jumpState = FALLING;
            switch(facing){
                case LEFT:
                    position.add(-8,0);
                    break;
                case RIGHT:
                    position.add(8,0);
                    break;
            }
        }

        velocity.setZero();
    }
    private void initClimb(){
        actionStartTime = TimeUtils.nanoTime();
        animationState = AnimState.CLIMB;
        jumpState = CLIMBING;
    }

    private void move(Direction direction){
        switch(direction){
            case LEFT:
                velocity.x = -Constants.JACK_MOVE_SPEED;
                facing = LEFT;
                break;
            case RIGHT:
                velocity.x = Constants.JACK_MOVE_SPEED;
                facing = RIGHT;
                break;
        }
    }

    public Vector2 getPosition(){
        return position;
    }
}
