package com.olundqvist.woody.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    //world
    public static final String TEXTURE_ATLAS = "images/woody.pack.atlas";
    public static final float WORLD_WIDTH = 384;
    public static final float WORLD_HEIGHT = 216;
    public static final Vector2 DEFAULT_SPAWN_LOCATION = new Vector2(150, 20);
    public static final Color BACKGROUND_COLOR = new Color(151826);
    public static final float GRAVITY = 8;

    //Camera
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_WIDTH;
    public static final float CAMERA_PADDING = 80;

    //Jack
    public static final float JACK_MOVE_SPEED = 150;
    public static final float JUMP_SPEED = 200;
    public static final float JACK_WIDTH = 20;
    public static final float RVOS_WIDTH = 20;
    public static final float JACK_HEIGHT = 32;
    public static final float JACK_DAMPING = 0.75f;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(200, 200);
    public static final float MAX_JUMP_DURATION = .2f;

    /*
        Andromalius (enemy)
     */
    //Idle
    static final String ANDRO_IDLE_0 = "andromalius/idle-0";
    static final String ANDRO_IDLE_1 = "andromalius/idle-1";
    static final String ANDRO_IDLE_2 = "andromalius/idle-2";
    static final String ANDRO_IDLE_3 = "andromalius/idle-3";
    static final String ANDRO_IDLE_4 = "andromalius/idle-4";
    static final String ANDRO_IDLE_5 = "andromalius/idle-5";
    static final String ANDRO_IDLE_6 = "andromalius/idle-6";
    static final String ANDRO_IDLE_7 = "andromalius/idle-7";

    //Action
    static final String ANDRO_ACTION_0 = "andromalius/action-0";
    static final String ANDRO_ACTION_1 = "andromalius/action-1";
    static final String ANDRO_ACTION_2 = "andromalius/action-2";
    static final String ANDRO_ACTION_3 = "andromalius/action-3";
    static final String ANDRO_ACTION_4 = "andromalius/action-4";
    static final String ANDRO_ACTION_5 = "andromalius/action-5";
    static final String ANDRO_ACTION_6 = "andromalius/action-6";
    static final String ANDRO_ACTION_7 = "andromalius/action-7";

    //left turn
    static final String ANDRO_LEFT_0 = "andromalius/left-0";
    static final String ANDRO_LEFT_1 = "andromalius/left-0";

    //right turn
    static final String ANDRO_RIGHT_0 = "andromalius/right-0";
    static final String ANDRO_RIGHT_1 = "andromalius/right-0";

    /*
        Rvros
     */
    //Idle
    static final String RVROS_IDLE_0 = "rvros/adventurer-idle-00";
    static final String RVROS_IDLE_1 = "rvros/adventurer-idle-01";
    static final String RVROS_IDLE_2 = "rvros/adventurer-idle-02";
    static final String RVROS_IDLE_3 = "rvros/adventurer-idle-03";
    static final float RVROS_IDLE_DURATION = 0.10f;

    static final String RVROS_IDLE_2_0 = "rvros/adventurer-idle-2-00";
    static final String RVROS_IDLE_2_1 = "rvros/adventurer-idle-2-01";
    static final String RVROS_IDLE_2_2 = "rvros/adventurer-idle-2-02";
    static final String RVROS_IDLE_2_3 = "rvros/adventurer-idle-2-03";

    //Run
    static final String RVROS_RUN_0 = "rvros/adventurer-run-00";
    static final String RVROS_RUN_1 = "rvros/adventurer-run-01";
    static final String RVROS_RUN_2 = "rvros/adventurer-run-02";
    static final String RVROS_RUN_3 = "rvros/adventurer-run-03";
    static final String RVROS_RUN_4 = "rvros/adventurer-run-04";
    static final String RVROS_RUN_5 = "rvros/adventurer-run-05";
    static final float RVROS_RUN_DURATION = 0.10f;

    //Jump
    static final String RVROS_JUMP_0 = "rvros/adventurer-jump-00";
    static final String RVROS_JUMP_1 = "rvros/adventurer-jump-01";
    static final String RVROS_JUMP_2 = "rvros/adventurer-jump-02";
    static final String RVROS_JUMP_3 = "rvros/adventurer-jump-03";

    //fall
    static final String RVROS_FALL_0 = "rvros/adventurer-fall-00";
    static final String RVROS_FALL_1 = "rvros/adventurer-fall-01";
    static final float RVROS_FALL_DURATION = 0.10f;

    //Corner grab
    static final String RVROS_CRNR_GRAB_0 = "rvros/adventurer-crnr-grb-00";
    static final String RVROS_CRNR_GRAB_1 = "rvros/adventurer-crnr-grb-01";
    static final String RVROS_CRNR_GRAB_2 = "rvros/adventurer-crnr-grb-02";
    static final String RVROS_CRNR_GRAB_3 = "rvros/adventurer-crnr-grb-03";
    static final float RVROS_GRAB_DURATION = 0.10f;

    //Corner climb
    static final String RVROS_CRNR_CLMB_0 = "rvros/adventurer-crnr-clmb-00";
    static final String RVROS_CRNR_CLMB_1 = "rvros/adventurer-crnr-clmb-01";
    static final String RVROS_CRNR_CLMB_2 = "rvros/adventurer-crnr-clmb-02";
    static final String RVROS_CRNR_CLMB_3 = "rvros/adventurer-crnr-clmb-03";
    static final String RVROS_CRNR_CLMB_4 = "rvros/adventurer-crnr-clmb-04";
    static final float RVROS_CLIMB_DURATION = 0.10f;

    //Corner jump
    static final String RVROS_CRNR_JMP_0 = "rvros/adventurer-crnr-jmp-00";
    static final String RVROS_CRNR_JMP_1 = "rvros/adventurer-crnr-jmp-01";
    static final String RVROS_CRNR_JMP_2 = "rvros/adventurer-crnr-jmp-02";

    static final float RVROS_ATTACK_DURATION = 0.10f;

    //Attack1
    static final String RVROS_ATTACK_1_0 = "rvros/adventurer-attack1-00";
    static final String RVROS_ATTACK_1_1 = "rvros/adventurer-attack1-01";
    static final String RVROS_ATTACK_1_2 = "rvros/adventurer-attack1-02";
    static final String RVROS_ATTACK_1_3 = "rvros/adventurer-attack1-03";
    static final String RVROS_ATTACK_1_4 = "rvros/adventurer-attack1-04";

    //Attack2
    static final String RVROS_ATTACK_2_0 = "rvros/adventurer-attack2-00";
    static final String RVROS_ATTACK_2_1 = "rvros/adventurer-attack2-01";
    static final String RVROS_ATTACK_2_2 = "rvros/adventurer-attack2-02";
    static final String RVROS_ATTACK_2_3 = "rvros/adventurer-attack2-03";
    static final String RVROS_ATTACK_2_4 = "rvros/adventurer-attack2-04";
    static final String RVROS_ATTACK_2_5 = "rvros/adventurer-attack2-05";

    //Attack3
    static final String RVROS_ATTACK_3_0 = "rvros/adventurer-attack3-00";
    static final String RVROS_ATTACK_3_1 = "rvros/adventurer-attack3-01";
    static final String RVROS_ATTACK_3_2 = "rvros/adventurer-attack3-02";
    static final String RVROS_ATTACK_3_3 = "rvros/adventurer-attack3-03";
    static final String RVROS_ATTACK_3_4 = "rvros/adventurer-attack3-04";
    static final String RVROS_ATTACK_3_5 = "rvros/adventurer-attack3-05";

    /*
        Woody
     */
    //Jump
    public static final String WOODY_JUMP = "woody/woody_jumping";

    //GrabAnimation
    static final String WOODY_GRAB_0 = "woody/woody_grab0";
    static final String WOODY_GRAB_1 = "woody/woody_grab1";
    static final String WOODY_GRAB_2 = "woody/woody_grab2";
    static final String WOODY_GRAB_3 = "woody/woody_grab3";
    static final String WOODY_GRAB_4 = "woody/woody_grab4";
    static final String WOODY_GRAB_5 = "woody/woody_grab5";
    static final float GRAB_ANIMATION_DURATION = 0.10f;

    //FallAnimation
    static final String WOODY_FALL_0 = "woody/woody_falling0";
    static final String WOODY_FALL_1 = "woody/woody_falling1";
    static final float FALL_ANIMATION_DURATION = 0.10f;

    //RunAnimation
    static final String WOODY_RUN_0 = "woody/woody_run0";
    static final String WOODY_RUN_1 = "woody/woody_run1";
    static final String WOODY_RUN_2 = "woody/woody_run2";
    static final String WOODY_RUN_3 = "woody/woody_run3";
    static final String WOODY_RUN_4 = "woody/woody_run4";
    static final String WOODY_RUN_5 = "woody/woody_run5";
    static final String WOODY_RUN_6 = "woody/woody_run6";
    static final String WOODY_RUN_7 = "woody/woody_run7";
    static final float RUN_ANIMATION_DURATION = 0.10f;

    //IdleAnimation
    static final String WOODY_IDLE_0 = "woody/woody_idle00";
    static final String WOODY_IDLE_1 = "woody/woody_idle01";
    static final String WOODY_IDLE_2 = "woody/woody_idle02";
    static final String WOODY_IDLE_3 = "woody/woody_idle03";
    static final String WOODY_IDLE_4 = "woody/woody_idle04";
    static final String WOODY_IDLE_5 = "woody/woody_idle05";
    static final String WOODY_IDLE_6 = "woody/woody_idle06";
    static final String WOODY_IDLE_7 = "woody/woody_idle07";
    static final String WOODY_IDLE_8 = "woody/woody_idle08";
    static final String WOODY_IDLE_9 = "woody/woody_idle09";
    static final String WOODY_IDLE_10 = "woody/woody_idle10";
    static final String WOODY_IDLE_11 = "woody/woody_idle11";
    static final float IDLE_ANIMATION_DURATION = 0.10f;


    //Parallax Background
    static final String BG_LAYER_0 = "background/Layer_0000";
    static final String BG_LAYER_1 = "background/Layer_0001";
    static final String BG_LAYER_2 = "background/Layer_0002";
    static final String BG_LAYER_3 = "background/Layer_0003";
    static final String BG_LAYER_4 = "background/Layer_0004_Lights";
    static final String BG_LAYER_5 = "background/Layer_0005";
    static final String BG_LAYER_6 = "background/Layer_0006";
    static final String BG_LAYER_7 = "background/Layer_0007_Lights";
    static final String BG_LAYER_8 = "background/Layer_0008";
    static final String BG_LAYER_9 = "background/Layer_0009";
    static final String BG_LAYER_10 = "background/Layer_0010";
}
