package com.olundqvist.woody.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public WoodyAssets woodyAssets;
    public BackgroundAssets backgroundAssets;

    public Assets() {
    }

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        woodyAssets = new WoodyAssets(atlas);
        backgroundAssets = new BackgroundAssets(atlas);
    }

    public class BackgroundAssets{

        public final AtlasRegion layer0;
        public final AtlasRegion layer1;
        public final AtlasRegion layer2;
        public final AtlasRegion layer3;
        public final AtlasRegion layer4;
        public final AtlasRegion layer5;
        public final AtlasRegion layer6;
        public final AtlasRegion layer7;
        public final AtlasRegion layer8;
        public final AtlasRegion layer9;
        public final AtlasRegion layer10;

        public BackgroundAssets(TextureAtlas atlas) {

            layer0 = atlas.findRegion(Constants.BG_LAYER_10);
            layer1 = atlas.findRegion(Constants.BG_LAYER_9);
            layer2 = atlas.findRegion(Constants.BG_LAYER_8);
            layer3 = atlas.findRegion(Constants.BG_LAYER_7);
            layer4 = atlas.findRegion(Constants.BG_LAYER_6);
            layer5 = atlas.findRegion(Constants.BG_LAYER_5);
            layer6 = atlas.findRegion(Constants.BG_LAYER_4);
            layer7 = atlas.findRegion(Constants.BG_LAYER_3);
            layer8 = atlas.findRegion(Constants.BG_LAYER_2);
            layer9 = atlas.findRegion(Constants.BG_LAYER_1);
            layer10 = atlas.findRegion(Constants.BG_LAYER_0);

        }
    }

    public class WoodyAssets {
        public final Animation<AtlasRegion> idleAnimation;
        public final Animation<AtlasRegion> runAnimation;
        public final Animation<AtlasRegion> fallingAnimation;
        public final Animation<AtlasRegion> grabAnimation;
        public final TextureRegion jumpSprite;

        public WoodyAssets(TextureAtlas atlas) {
            idleAnimation = initIdleFrames(atlas);
            runAnimation = initRunAnimation(atlas);
            fallingAnimation = initFallingAnimation(atlas);
            grabAnimation = initGrabAnimation(atlas);
            jumpSprite = atlas.findRegion(Constants.WOODY_JUMP);
        }
        private Animation<AtlasRegion> initGrabAnimation(TextureAtlas atlas){
            Array<AtlasRegion> grabAnimation = new Array<>();
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_0));
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_1));
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_2));
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_3));
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_4));
            grabAnimation.add(atlas.findRegion(Constants.WOODY_GRAB_5));

            return new Animation<AtlasRegion>(
                    Constants.GRAB_ANIMATION_DURATION,
                    grabAnimation,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initFallingAnimation(TextureAtlas atlas){
            Array<AtlasRegion> fallingAnimation = new Array<>();
            fallingAnimation.add(atlas.findRegion(Constants.WOODY_FALL_0));
            fallingAnimation.add(atlas.findRegion(Constants.WOODY_FALL_1));

            return new Animation<AtlasRegion>(
                    Constants.FALL_ANIMATION_DURATION,
                    fallingAnimation,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initRunAnimation(TextureAtlas atlas){
            Array<AtlasRegion> runAnimation = new Array<>();
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_0));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_1));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_2));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_3));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_4));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_5));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_6));
            runAnimation.add(atlas.findRegion(Constants.WOODY_RUN_7));

            return new Animation<AtlasRegion>(
                    Constants.RUN_ANIMATION_DURATION,
                    runAnimation,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initIdleFrames(TextureAtlas atlas){
            Array<AtlasRegion> idleFrames = new Array<>();
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_0));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_1));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_2));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_3));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_4));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_5));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_6));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_7));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_8));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_9));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_10));
            idleFrames.add(atlas.findRegion(Constants.WOODY_IDLE_11));
            return new Animation<AtlasRegion>(
                    Constants.IDLE_ANIMATION_DURATION,
                    idleFrames,
                    Animation.PlayMode.LOOP
            );
        }

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Failed to load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
