package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Constants;
import com.olundqvist.woody.util.Enums;
import com.olundqvist.woody.util.Enums.Direction;
import com.olundqvist.woody.util.Enums.AnimState;
import com.olundqvist.woody.util.Utils;

import java.util.Random;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

import static com.olundqvist.woody.util.Constants.JACK_DAMPING;
import static com.olundqvist.woody.util.Constants.JACK_HEIGHT;
import static com.olundqvist.woody.util.Constants.JACK_WIDTH;
import static com.olundqvist.woody.util.Constants.JUMP_SPEED;
import static com.olundqvist.woody.util.Constants.RVOS_WIDTH;
import static com.olundqvist.woody.util.Enums.Direction.LEFT;
import static com.olundqvist.woody.util.Enums.Direction.RIGHT;
import static com.olundqvist.woody.util.Enums.ActionState.*;

public class Jack {
    private static final String TAG = Jack.class.getName();

    private Vector2 spawnLocation;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 offset;
    Direction facing;
    Enums.ActionState actionState;
    private AnimState animationState;
    private long idleStartTime;
    private long actionStartTime;
    private long deathStartTime;
    private float idleTimeSec;
    boolean left, right, attack;
    private Level level;
    private Rectangle bounds, attackBounds;
    private Random random;
    private int hp, lives;


    public Jack(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        position = new Vector2();
        velocity = new Vector2();
        offset = new Vector2();
        this.level = level;
        init();
    }

    private void init() {
        position = spawnLocation;
        velocity.setZero();
        facing = RIGHT;
        random = new Random();
        // TODO: Review Animation start times
        idleStartTime = TimeUtils.nanoTime();
        animationState = AnimState.IDLE;
        actionState = FALLING;
        bounds = new Rectangle(position.x, position.y, Constants.JACK_WIDTH, JACK_HEIGHT);
        hp = Constants.JACK_HP;
        lives = Constants.JACK_LIVES;
    }

    void render(Batch batch) {
        TextureRegion region;
        idleTimeSec = Utils.secondsSince(idleStartTime);
        offset.setZero();
        switch (animationState) {
            case RUN:
                region = Assets.instance.rvrosAssets.runAnimation.getKeyFrame(idleTimeSec);
                break;
            case FALL:
                region = Assets.instance.rvrosAssets.fallAnimation.getKeyFrame(idleTimeSec);
                break;
            case GRAB:
                region = Assets.instance.rvrosAssets.grabAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                if (facing == RIGHT) {
                    offset.x = 5;
                }
                break;
            case JUMP:
                region = Assets.instance.rvrosAssets.jumpSprite;
                break;
            case CLIMB:
                region = Assets.instance.rvrosAssets.climbAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                if (facing == RIGHT) {
                    offset.x = 5;
                }
                break;
            case ATTACK1:
                region = Assets.instance.rvrosAssets.attack1Animation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                if (facing == LEFT) {
                    offset.x = -25;
                }
                break;
            case ATTACK2:
                region = Assets.instance.rvrosAssets.attack2Animation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                if (facing == LEFT) {
                    offset.x = -25;
                }
                break;
            case ATTACK3:
                region = Assets.instance.rvrosAssets.attack3Animation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                if (facing == LEFT) {
                    offset.x = -30;
                }
                break;
            case DIE:
                region = Assets.instance.rvrosAssets.deathAnimation.getKeyFrame(
                        Utils.secondsSince(actionStartTime));
                break;
            default:
                region = Assets.instance.rvrosAssets.idleAnimation.getKeyFrame(idleTimeSec);
                break;
        }
        Utils.drawTextureRegion(batch, region, position, facing, offset);
    }

    void update(float delta) {
        handleInput();
        velocity.y -= Constants.GRAVITY;
        velocity.scl(delta);
        bounds.setPosition(position);
        switch (actionState) {
            case GRABBING:
                velocity.setZero();
                break;
            case CLIMBING:
                climb(delta);
                break;
            case ATTACKING:
                attack();
            case FALLING:
            case RECOILING:
            case GROUNDED:
                collisionCheck();
                break;
            case DIEING:
                dieing();
                collisionCheck();
                break;
        }
        updateState();
        position.add(velocity);
        velocity.scl(1 / delta);
        dampen();
    }

    private void updateState() {
        if (actionState != ATTACKING) {
            if (velocity.y < 0) {
                animationState = AnimState.FALL;
            } else if (velocity.y > 0) {
                animationState = AnimState.JUMP;
            } else {
                animationState = AnimState.IDLE;
            }
        }

        switch (actionState) {
            case ATTACKING:
                break;
            case GROUNDED:
                if (Math.abs(velocity.x) > 0) {
                    animationState = AnimState.RUN;
                }
                break;
            case GRABBING:
                animationState = AnimState.GRAB;
                break;
            case CLIMBING:
                animationState = AnimState.CLIMB;
                break;
            case DIEING:
                animationState = AnimState.DIE;
                break;
        }
    }

    public void knockback(Rectangle enemyBounds){
        Gdx.app.log(TAG, "Knockback");
        //If jack is on the right side of the enemy
        if((enemyBounds.x+(enemyBounds.width/2)) < position.x + (bounds.width/2)){
            position.x = enemyBounds.x + enemyBounds.width + 5;
            velocity.x = 250;
        }else{
            position.x = enemyBounds.x - bounds.width - 5;
            velocity.x = -250;
        }
        velocity.y += JUMP_SPEED/2;
        actionState = RECOILING;
        takeDamage(1);
    }

    public void takeDamage(int dmg){
        this.hp -= dmg;
        if(hp < 0){
            die();
        }
        Gdx.app.log(TAG, "took " + dmg + " dmg  HP:" + this.hp);
    }

    public void die(){
        Gdx.app.log(TAG, "Died " + lives + " Lives remaining");
        this.lives -= 1;
        actionState = DIEING;
        actionStartTime = TimeUtils.nanoTime();
        if(this.lives < 0){
            Gdx.app.log(TAG, "Game over");
        }else{
            this.hp = Constants.JACK_HP;
        }
    }

    private void dieing(){
        if(Utils.secondsSince(actionStartTime) > 2){
            if(this.lives < 0){
                Gdx.app.log(TAG, "Game over");
            }else{
                this.hp = Constants.JACK_HP;
            }
            actionState = FALLING;
        }
    }

    private void ledge(Rectangle rect) {
        /*Gdx.app.log(TAG, "Rectangle " + (int)rect.y );
        Gdx.app.log(TAG, "Position "  + (int)(position.y+16) );
        Gdx.app.log(TAG, "------------------");*/
        //Grab ledge if the top of the corner is below jacks height
        //and within 4 units reach
        if (((position.y + 16) > (rect.y)) &&
                ((position.y + 12) < (rect.y))
        ) {
            switch (facing) {
                case LEFT:
                    position.x = rect.x + 16;
                    break;
                case RIGHT:
                    //Workaround for better appearance of mirrored grab animation
                    position.x = rect.x - RVOS_WIDTH;
                    break;
            }
            velocity.setZero();
            position.y = rect.y - 16;
            actionState = GRABBING;
            actionStartTime = TimeUtils.nanoTime();
        }
    }

    private void collisionCheck() {
        //Horizontal Collision check
        bounds.x += velocity.x;
        Rectangle collisionRect = level.collideX(velocity, bounds);
        if (collisionRect != null) {
            Rectangle corner = level.isCorner(collisionRect);
            if (corner != null) {
                ledge(corner);
            }
            velocity.x = 0;
        }
        bounds.x = position.x;

        //Vertical collision check
        if (Math.abs(velocity.y) > 0) {
            bounds.y += velocity.y;
            collisionRect = level.collideY(velocity, bounds);
            if (collisionRect != null) {
                if (velocity.y < 0) {
                    position.y = collisionRect.y + collisionRect.height;
                    land();
                } else { //jumping
                    position.y = collisionRect.y - JACK_HEIGHT;
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
        if (actionState != ATTACKING && actionState != DIEING) {
            actionState = GROUNDED;
        }
    }

    private void dampen() {
        if (Math.abs(velocity.x) < 1) {
            velocity.x = 0;
        } else {
            velocity.x *= JACK_DAMPING;
        }
    }

    private void handleInput() {
        left = Gdx.input.isKeyPressed(Keys.LEFT);
        right = Gdx.input.isKeyPressed(Keys.RIGHT);
        attack = Gdx.input.isKeyPressed(Keys.X);

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            level.debug = true;
        }

        switch (actionState) {
            //Only allow attack initiation While grounded?
            case GROUNDED:
                if (attack) {
                    Gdx.app.log(TAG, "Initiating attack from Grounded");
                    initAttack();
                }
                else {
                    if (left && !right) {
                        move(LEFT);
                    } else if (right && !left) {
                        move(RIGHT);
                    }
                }
                break;
            case FALLING:
                //Allow air control?
                if (attack) {
                    Gdx.app.log(TAG, "Initiating attack from Falling");
                    initAttack();
                }
                if (left && !right) {
                    move(LEFT);
                } else if (right && !left) {
                    move(RIGHT);
                }
                break;
            case GRABBING:
                if (facing == RIGHT && left) {
                    actionState = FALLING;
                    //Gdx.app.log(TAG, "Let go");
                } else if (facing == LEFT && right) {
                    //Gdx.app.log(TAG,"let go");
                }
                break;
            case ATTACKING:
                //Allow moving attacks?
                if (left && !right) {
                    move(LEFT);
                } else if (right && !left) {
                    move(RIGHT);
                }
                break;
        }

        //If both left and right keys are active don't move
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            switch (actionState) {
                case GROUNDED:
                    actionState = FALLING;
                    velocity.y += JUMP_SPEED;
                    //Gdx.app.log(TAG, "Jumped");
                    break;
                case GRABBING:
                    switch (facing) {
                        case LEFT:
                            initClimb();
                            //Gdx.app.log(TAG, "Climbed Left");
                            break;
                        case RIGHT:
                            initClimb();
                            //Gdx.app.log(TAG, "Climbed Right");
                            break;
                    }
                    break;
            }
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            switch (actionState) {
                case GROUNDED:
                    //Gdx.app.log(TAG, "Ducking?");
                    break;
                case GRABBING:
                    actionState = FALLING;
                    //Gdx.app.log(TAG, "Let go of ledge");
                    break;
            }
        }
    }

    private void initAttack() {
        actionStartTime = TimeUtils.nanoTime();
        actionState = ATTACKING;
        switch (random.nextInt(3) + 1) {
            case 1:
                animationState = AnimState.ATTACK1;
                //Gdx.app.log(TAG, "Attack 1");
                break;
            case 2:
                animationState = AnimState.ATTACK2;
                //Gdx.app.log(TAG, "Attack 2");
                break;
            case 3:
                animationState = AnimState.ATTACK3;
                //Gdx.app.log(TAG, "Attack 3");
                break;
        }
        //TODO: Clean this up
        Rectangle rect = new Rectangle();
        switch (facing) {
            case LEFT:
                rect.setPosition(position.x - Constants.JACK_ATTACK_RANGE, position.y);
                rect.setWidth(Constants.JACK_ATTACK_RANGE);
                rect.setHeight(JACK_HEIGHT);
                break;
            case RIGHT:
                rect.setPosition(position.x + Constants.RVOS_WIDTH, position.y);
                rect.setWidth(Constants.JACK_ATTACK_RANGE);
                rect.setHeight(JACK_HEIGHT);
                break;
        }
        level.attack(rect);
    }

    private void attack() {
        //End attack after 0.5s
        if (Utils.secondsSince(actionStartTime) > 0.5) {
            actionState = GROUNDED;
        }
        attackBounds = bounds;
        switch (facing) {
            case LEFT:
                attackBounds.x -= 10;
                break;
            case RIGHT:
                attackBounds.x += 10;
                break;
        }
    }

    private void climb(float delta) {
        position.add(0, 60 * delta);
        if (Utils.secondsSince(actionStartTime) > 0.5) {
            //Gdx.app.log(TAG, "Climb ended");
            actionState = FALLING;
            switch (facing) {
                case LEFT:
                    position.add(-8, 0);
                    break;
                case RIGHT:
                    position.add(8, 0);
                    break;
            }
        }
        velocity.setZero();
    }

    private void initClimb() {
        actionStartTime = TimeUtils.nanoTime();
        animationState = AnimState.CLIMB;
        actionState = CLIMBING;
    }

    private void move(Direction direction) {
        switch (direction) {
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

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }
}
