package com.olundqvist.woody.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.olundqvist.woody.entities.Jack;

import static com.olundqvist.woody.util.Constants.CAMERA_PADDING;

public class ChaseCam {
    public static final String TAG = ChaseCam.class.getName();

    public Camera camera;
    public Jack target;
    public boolean following;

    public ChaseCam( ) {
        following = true;
    }


    public void update(float delta){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            following = !following;
        }
        if(following){
            camera.position.x = target.getPosition().x + CAMERA_PADDING;
            camera.position.y = target.getPosition().y + CAMERA_PADDING;
        }else{
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                camera.position.x -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                camera.position.x += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                camera.position.y += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                camera.position.y -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
                Gdx.app.log(TAG,
                        "Camera X = " + camera.position.x +
                                "Camera Y = " + camera.position.y);
            }
        }

    }
}
