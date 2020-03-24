package com.olundqvist.woody.util;

public class Enums {

    public enum Direction{
        LEFT, RIGHT
    }

    public enum WalkState {
        WALKING,
        NOT_WALKING
    }

    public enum JumpState{
        FALLING,
        GRABING,
        GROUNDED,
        CLIMBING
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
        CLIMB
    }
}
