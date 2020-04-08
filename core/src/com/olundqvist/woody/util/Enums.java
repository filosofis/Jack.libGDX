package com.olundqvist.woody.util;

public class Enums {

    public enum Direction{
        LEFT, RIGHT
    }

    public enum EnemyState{
        IDLE,
        TURNING,
        MOVING
    }

    public enum EnemyType{

    }

    public enum ActionState{
        FALLING,
        GRABBING,
        GROUNDED,
        CLIMBING,
        ATTACKING,
    }

    public enum WH{
        WIDTH,
        HEIGHT
    }

    public enum AnimState{
        RUN,
        IDLE,
        FALL,
        GRAB,
        JUMP,
        CLIMB,
        ATTACK1,
        ATTACK2,
        ATTACK3,
        TURN
    }
}
