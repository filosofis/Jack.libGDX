package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.olundqvist.woody.background.ParallaxBackground;
import com.olundqvist.woody.background.TextureRegionParallaxLayer;
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Constants;
import com.olundqvist.woody.util.Enums;

public class Level {
    private static final String TAG = Level.class.getName();
    public Viewport viewport;
    public Jack jack;
    private ParallaxBackground parallaxBackground;
    private ParallaxBackground parallaxForground;
    TiledMap map;
    OrthogonalTiledMapRenderer tmr;
    private Array<Rectangle> tiles = new Array<Rectangle>();
    private TiledMapTileLayer tileLayer;
    private ShapeRenderer debugRenderer;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        createLayers();
        map = new TmxMapLoader().load("maps/test_map.tmx");
        tileLayer = (TiledMapTileLayer)map.getLayers().get("TileLayer");
        tmr = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        OrthographicCamera ocam = (OrthographicCamera) viewport.getCamera();
        parallaxBackground.draw(ocam, batch);
        jack.render(batch);
        parallaxForground.draw(ocam, batch);
        batch.end();
        tmr.setView(ocam);
        tmr.render();
        renderDebug(viewport.getCamera());
    }

    public void update(float delta){
        jack.update(delta);
    }

    private void getTiles (int startX, int startY, int endX, int endY) {
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x*16, y*16, 16, 16);
                    tiles.add(rect);
                }
            }
        }
    }

    public Rectangle isCorner(Rectangle rect){
        int x = (int)(rect.x/16);
        int y = (int)(rect.y/16);
        if(tileLayer.getCell(x,y+1) == null){
            Rectangle corner = rectPool.obtain();
            corner.set(x*16, y*16, 16,16);
            Gdx.app.log(TAG, "is a corner " +  x + ", " + y);
            return corner;
        } else if(tileLayer.getCell(x, y+2) == null){
            Rectangle corner = rectPool.obtain();
            corner.set(x*16, (y+1)*16, 16,16);
            Gdx.app.log(TAG, "below a corner " + x + ", " + y);
            return corner;
        }
        return null;
    }

    //TODO: Use rectpool for emeny bounds
    public Rectangle collideY(Vector2 velocity, Rectangle bounds){
        int startX, startY, endX, endY;
        startX = (int)bounds.x;
        endX = (int)(bounds.x + bounds.height);
        if(velocity.y > 0){
            startY = endY = (int)(bounds.y + bounds.height + velocity.y);
        }else{
            startY = endY = (int)(bounds.y + velocity.y);
        }

        getTiles(startX/16, startY/16, endX/16, endY/16);
        for(Rectangle tile : tiles){
            if(bounds.overlaps(tile)){
                return tile;
            }
        }
        return null;
    }

    public Rectangle collideX(Vector2 velocity, Rectangle bounds){
        int startX, startY, endX, endY;
        startY = (int)bounds.y;
        endY = (int)(bounds.y + bounds.width);

        if(velocity.x > 0){
            startX = endX = (int)(bounds.x + bounds.width + velocity.x);
        }else{
            startX = endX = (int)(bounds.x + velocity.x);
        }


        getTiles(startX/16, startY/16, endX/16, endY/16);
        for(Rectangle tile : tiles){
            if(bounds.overlaps(tile)){
                return tile;
            }
        }
        return null;
    }

    public void testTiles(){
        Gdx.app.log(TAG, "test tiles");
        //getTiles(0,0,500,500, tiles);
    }

    public Jack getJack() {
        return jack;
    }

    public void setJack(Jack jack) {
        this.jack = jack;
    }

    public void createLayers(){

        float oneDimen = 928;

        TextureRegionParallaxLayer layer0 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer0,
                oneDimen,
                new Vector2(.3f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer1 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer1,
                oneDimen,
                new Vector2(.5f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer2 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer2,
                oneDimen,
                new Vector2(1f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer3 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer3,
                oneDimen,
                new Vector2(1.1f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer4 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer4,
                oneDimen,
                new Vector2(1.15f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer5 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer5,
                oneDimen,
                new Vector2(1.2f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer6 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer6,
                oneDimen,
                new Vector2(1.25f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer7 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer7,
                oneDimen,
                new Vector2(1.3f, 1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer8 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer8,
                oneDimen,
                new Vector2(1.4f,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer9 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer9,
                oneDimen,
                new Vector2(1,1),
                Enums.WH.HEIGHT
        );
        TextureRegionParallaxLayer layer10 = new TextureRegionParallaxLayer(
                Assets.instance.backgroundAssets.layer10,
                oneDimen,
                new Vector2(0.5f,1),
                Enums.WH.HEIGHT
        );

        parallaxBackground = new ParallaxBackground();
        parallaxForground = new ParallaxBackground();
        parallaxBackground.addLayers(layer0,
                layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8, layer9);
        parallaxForground.addLayers(layer10);
    }

    private void renderDebug (Camera camera) {
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(Color.RED);
        debugRenderer.rect(
                jack.getPosition().x,
                jack.getPosition().y,
                Constants.JACK_WIDTH,
                Constants.JACK_HEIGHT);

        debugRenderer.setColor(Color.YELLOW);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("TileLayer");
        for (int y = 0; y <= layer.getHeight(); y++) {
            for (int x = 0; x <= layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                        debugRenderer.rect(x*16, y*16, 16, 16);
                }
            }
        }
        debugRenderer.end();
    }
}
