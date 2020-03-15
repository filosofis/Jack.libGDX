package com.olundqvist.woody.entities;

import com.badlogic.gdx.math.Vector2;
import com.olundqvist.woody.util.Enums;

public class Bunny {
    public static final String TAG = Jack.class.getName();

    private Vector2 position;
    private Vector2 velocity;
    private Enums.AnimState animState;

    public Bunny(Vector2 spawnLocation){
        position = new Vector2();
        velocity = new Vector2();
        init(spawnLocation);
    }

    public void init(Vector2 spawnLocation){
        position = spawnLocation;
        animState = Enums.AnimState.IDLE;
    }
}
