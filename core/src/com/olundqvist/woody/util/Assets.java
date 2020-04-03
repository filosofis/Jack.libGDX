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
    public RvrosAssets rvrosAssets;
    public EnemyAssets enemyAssets;

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
        rvrosAssets = new RvrosAssets(atlas);
        enemyAssets = new EnemyAssets(atlas);
    }

    public static class EnemyAssets{
        //TODO: Add turn and attack animations for Andromalius
        //TODO: Add more types of enemies, maybe the hellhound?
        //TODO: Add projectiles
        public final Animation<AtlasRegion> androIdleAnimation;
        public final Animation<AtlasRegion> androActionAnimation;

        EnemyAssets(TextureAtlas atlas){
            androIdleAnimation = initAndroIdleAnimation(atlas);
            androActionAnimation = initAndroActionAnimation(atlas);
        }
        private Animation<AtlasRegion> initAndroIdleAnimation(TextureAtlas atlas){
            Array<AtlasRegion> idleFrames = new Array<>();
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_0));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_1));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_2));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_3));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_4));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_5));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_6));
            idleFrames.add(atlas.findRegion(Constants.ANDRO_IDLE_7));
            return new Animation<>(
                    Constants.IDLE_ANIMATION_DURATION,
                    idleFrames,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initAndroActionAnimation(TextureAtlas atlas){
            Array<AtlasRegion> actionFrames = new Array<>();
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_0));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_1));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_2));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_3));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_4));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_5));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_6));
            actionFrames.add(atlas.findRegion(Constants.ANDRO_ACTION_7));
            return new Animation<>(
                    Constants.IDLE_ANIMATION_DURATION,
                    actionFrames,
                    Animation.PlayMode.NORMAL
            );
        }
    }

    public static class BackgroundAssets{

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

        BackgroundAssets(TextureAtlas atlas) {

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

    public static class RvrosAssets{
        public final Animation<AtlasRegion> idleAnimation;
        public final Animation<AtlasRegion> runAnimation;
        public final Animation<AtlasRegion> fallAnimation;
        public final Animation<AtlasRegion> grabAnimation;
        public final Animation<AtlasRegion> climbAnimation;
        public final Animation<AtlasRegion> attack1Animation;
        public final Animation<AtlasRegion> attack2Animation;
        public final Animation<AtlasRegion> attack3Animation;
        public final TextureRegion jumpSprite;

        RvrosAssets(TextureAtlas atlas){
            idleAnimation = initIdleAnimation(atlas);
            runAnimation = initRunAnimation(atlas);
            fallAnimation = initFallAnimation(atlas);
            grabAnimation = initGrabAnimation(atlas);
            climbAnimation = initClimbAnimation(atlas);
            attack1Animation = initAttack1(atlas);
            attack2Animation = initAttack2(atlas);
            attack3Animation = initAttack3(atlas);
            jumpSprite = atlas.findRegion(Constants.RVROS_JUMP_2);
        }

        private Animation<AtlasRegion> initAttack1(TextureAtlas atlas){
            Array<AtlasRegion> attackFrames = new Array<>();
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_1_0));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_1_1));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_1_2));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_1_3));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_1_4));

            return new Animation<>(
                    Constants.RVROS_ATTACK_DURATION,
                    attackFrames,
                    Animation.PlayMode.NORMAL
            );
        }
        private Animation<AtlasRegion> initAttack2(TextureAtlas atlas){
            Array<AtlasRegion> attackFrames = new Array<>();
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_0));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_1));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_2));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_3));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_4));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_2_5));

            return new Animation<>(
                    Constants.RVROS_ATTACK_DURATION,
                    attackFrames,
                    Animation.PlayMode.NORMAL
            );
        }
        private Animation<AtlasRegion> initAttack3(TextureAtlas atlas){
            Array<AtlasRegion> attackFrames = new Array<>();
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_0));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_1));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_2));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_3));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_4));
            attackFrames.add(atlas.findRegion(Constants.RVROS_ATTACK_3_5));

            return new Animation<>(
                    Constants.RVROS_ATTACK_DURATION,
                    attackFrames,
                    Animation.PlayMode.NORMAL
            );
        }



        private Animation<AtlasRegion> initClimbAnimation(TextureAtlas atlas){
            Array<AtlasRegion> climbFrames = new Array<>();
            climbFrames.add(atlas.findRegion(Constants.RVROS_CRNR_CLMB_0));
            climbFrames.add(atlas.findRegion(Constants.RVROS_CRNR_CLMB_1));
            climbFrames.add(atlas.findRegion(Constants.RVROS_CRNR_CLMB_2));
            climbFrames.add(atlas.findRegion(Constants.RVROS_CRNR_CLMB_3));
            climbFrames.add(atlas.findRegion(Constants.RVROS_CRNR_CLMB_4));

            return new Animation<>(
                    Constants.RVROS_CLIMB_DURATION,
                    climbFrames,
                    Animation.PlayMode.NORMAL
            );
        }
        private Animation<AtlasRegion> initIdleAnimation(TextureAtlas atlas){
            Array<AtlasRegion> idleFrames = new Array<>();
            idleFrames.add(atlas.findRegion(Constants.RVROS_IDLE_0));
            idleFrames.add(atlas.findRegion(Constants.RVROS_IDLE_1));
            idleFrames.add(atlas.findRegion(Constants.RVROS_IDLE_2));
            idleFrames.add(atlas.findRegion(Constants.RVROS_IDLE_3));
            return new Animation<>(
              Constants.RVROS_IDLE_DURATION,
              idleFrames,
              Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initRunAnimation(TextureAtlas atlas){
            Array<AtlasRegion> runFrames = new Array<>();
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_0));
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_1));
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_2));
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_3));
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_4));
            runFrames.add(atlas.findRegion(Constants.RVROS_RUN_5));

            return new Animation<AtlasRegion>(
                    Constants.RVROS_RUN_DURATION,
                    runFrames,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initFallAnimation(TextureAtlas atlas){
            Array<AtlasRegion> fallFrames = new Array<>();
            fallFrames.add(atlas.findRegion(Constants.RVROS_FALL_0));
            fallFrames.add(atlas.findRegion(Constants.RVROS_FALL_1));

            return new Animation<>(
                    Constants.RVROS_FALL_DURATION,
                    fallFrames,
                    Animation.PlayMode.LOOP
            );
        }
        private Animation<AtlasRegion> initGrabAnimation(TextureAtlas atlas){
            Array<AtlasRegion> grabFrames = new Array<>();
            grabFrames.add(atlas.findRegion(Constants.RVROS_CRNR_GRAB_0));
            grabFrames.add(atlas.findRegion(Constants.RVROS_CRNR_GRAB_1));
            grabFrames.add(atlas.findRegion(Constants.RVROS_CRNR_GRAB_2));
            grabFrames.add(atlas.findRegion(Constants.RVROS_CRNR_GRAB_3));

            return new Animation<>(
                    Constants.RVROS_GRAB_DURATION,
                    grabFrames,
                    Animation.PlayMode.LOOP
            );
        }
    }

    public static class WoodyAssets {
        public final Animation<AtlasRegion> idleAnimation;
        public final Animation<AtlasRegion> runAnimation;
        public final Animation<AtlasRegion> fallAnimation;
        public final Animation<AtlasRegion> grabAnimation;
        public final TextureRegion jumpSprite;

        WoodyAssets(TextureAtlas atlas) {
            idleAnimation = initIdleFrames(atlas);
            runAnimation = initRunAnimation(atlas);
            fallAnimation = initFallAnimation(atlas);
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

            return new Animation<>(
                    Constants.GRAB_ANIMATION_DURATION,
                    grabAnimation,
                    Animation.PlayMode.NORMAL
            );
        }
        private Animation<AtlasRegion> initFallAnimation(TextureAtlas atlas){
            Array<AtlasRegion> fallingAnimation = new Array<>();
            fallingAnimation.add(atlas.findRegion(Constants.WOODY_FALL_0));
            fallingAnimation.add(atlas.findRegion(Constants.WOODY_FALL_1));

            return new Animation<>(
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

            return new Animation<>(
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
            return new Animation<>(
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
