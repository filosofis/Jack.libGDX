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
    public static final float JUMP_SPEED = 300;
    public static final float JACK_WIDTH = 20;
    public static final float JACK_HEIGHT = 32;
    public static final float JACK_DAMPING = 0.75f;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(200, 200);
    public static final float MAX_JUMP_DURATION = .2f;

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
