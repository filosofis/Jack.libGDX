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
    public static final float JACK_WIDTH = 24;
    public static final float JACK_HEIGHT = 32;
    public static final float JACK_DAMPING = 0.75f;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(200, 200);
    public static final float MAX_JUMP_DURATION = .2f;

    //Jump
    public static final String WOODY_JUMP = "woody/woody_jumping";

    //GrabAnimation
    public static final String WOODY_GRAB_0 = "woody/woody_grab0";
    public static final String WOODY_GRAB_1 = "woody/woody_grab1";
    public static final String WOODY_GRAB_2 = "woody/woody_grab2";
    public static final String WOODY_GRAB_3 = "woody/woody_grab3";
    public static final String WOODY_GRAB_4 = "woody/woody_grab4";
    public static final String WOODY_GRAB_5 = "woody/woody_grab5";
    public static final float GRAB_ANIMATION_DURATION = 0.10f;

    //FallAnimation
    public static final String WOODY_FALL_0 = "woody/woody_falling0";
    public static final String WOODY_FALL_1 = "woody/woody_falling1";
    public static final float FALL_ANIMATION_DURATION = 0.10f;

    //RunAnimation
    public static final String WOODY_RUN_0 = "woody/woody_run0";
    public static final String WOODY_RUN_1 = "woody/woody_run1";
    public static final String WOODY_RUN_2 = "woody/woody_run2";
    public static final String WOODY_RUN_3 = "woody/woody_run3";
    public static final String WOODY_RUN_4 = "woody/woody_run4";
    public static final String WOODY_RUN_5 = "woody/woody_run5";
    public static final String WOODY_RUN_6 = "woody/woody_run6";
    public static final String WOODY_RUN_7 = "woody/woody_run7";
    public static final float RUN_ANIMATION_DURATION = 0.10f;

    //IdleAnimation
    public static final String WOODY_IDLE_0 = "woody/woody_idle00";
    public static final String WOODY_IDLE_1 = "woody/woody_idle01";
    public static final String WOODY_IDLE_2 = "woody/woody_idle02";
    public static final String WOODY_IDLE_3 = "woody/woody_idle03";
    public static final String WOODY_IDLE_4 = "woody/woody_idle04";
    public static final String WOODY_IDLE_5 = "woody/woody_idle05";
    public static final String WOODY_IDLE_6 = "woody/woody_idle06";
    public static final String WOODY_IDLE_7 = "woody/woody_idle07";
    public static final String WOODY_IDLE_8 = "woody/woody_idle08";
    public static final String WOODY_IDLE_9 = "woody/woody_idle09";
    public static final String WOODY_IDLE_10 = "woody/woody_idle10";
    public static final String WOODY_IDLE_11 = "woody/woody_idle11";
    public static final float IDLE_ANIMATION_DURATION = 0.10f;


    //Parallax Background
    public static final String BG_LAYER_0 = "background/Layer_0000";
    public static final String BG_LAYER_1 = "background/Layer_0001";
    public static final String BG_LAYER_2 = "background/Layer_0002";
    public static final String BG_LAYER_3 = "background/Layer_0003";
    public static final String BG_LAYER_4 = "background/Layer_0004_Lights";
    public static final String BG_LAYER_5 = "background/Layer_0005";
    public static final String BG_LAYER_6 = "background/Layer_0006";
    public static final String BG_LAYER_7 = "background/Layer_0007_Lights";
    public static final String BG_LAYER_8 = "background/Layer_0008";
    public static final String BG_LAYER_9 = "background/Layer_0009";
    public static final String BG_LAYER_10 = "background/Layer_0010";
}
