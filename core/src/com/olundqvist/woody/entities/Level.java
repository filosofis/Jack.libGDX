package com.olundqvist.woody.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
import com.olundqvist.woody.util.Assets;
import com.olundqvist.woody.util.Constants;
import com.olundqvist.woody.util.Enums;

public class Level {
    private static final String TAG = Level.class.getName();
    public Viewport viewport;
    public Jack jack;
    private ParallaxBackground parallaxBackground;
    private ParallaxBackground parallaxForground;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;
    private Array<Rectangle> tiles = new Array<Rectangle>();
    private TiledMapTileLayer tileLayer;
    private ShapeRenderer debugRenderer;
    boolean debug;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };
    private Array<Enemy> enemies = new Array<>();

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        //createLayers();
        parallaxForground = Assets.instance.backgroundAssets.jungleForground;
        parallaxBackground = Assets.instance.backgroundAssets.jungleBackground;
        map = new TmxMapLoader().load("maps/test_map.tmx");
        tileLayer = (TiledMapTileLayer)map.getLayers().get("TileLayer");
        tmr = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new ShapeRenderer();
        debug=false;
        loadEnemies();
        for(Enemy enemy : enemies){
            Gdx.app.log(TAG, enemy.toString());
        }
    }

    //Enemies are stored as objects in the TMX file. These objects have
    // A filed for X, and a for Y coordinate, as well as A Enemy Type
    private void loadEnemies(){
        MapObjects objects = map.getLayers().get("Enemies").getObjects();
        Float x,y;
        Enums.EnemyType enemyType;
        Rectangle bounds = rectPool.obtain();
        for(MapObject obj : objects){
            x = (Float)obj.getProperties().get("x");
            y = (Float)obj.getProperties().get("y");
            bounds.x = x;
            bounds.y = y;
            switch((String)obj.getProperties().get("Type")) {
                case Constants.ANDRO_TYPE:
                    enemyType = Enums.EnemyType.ANDROMALIUS;
                    bounds.width = Constants.ANDRO_WIDTH;
                    bounds.height = Constants.ANDRO_HEIGHT;
                    break;
                case Constants.HELL_HOUND_TYPE:
                    enemyType = Enums.EnemyType.HELLHOUND;
                    bounds.width = Constants.HELL_HOUND_WIDTH;
                    bounds.height = Constants.HELL_HOUND_HEIGHT;
                    break;
                default:
                    enemyType = Enums.EnemyType.HELLHOUND;
                    bounds.width = 10;
                    bounds.height = 10;
                    break;
            }
            Enemy enemy = new Enemy(new Vector2(x, y),enemyType, new Rectangle(bounds));
            enemies.add(enemy);
            rectPool.free(bounds);
        }
        Gdx.app.log(TAG, "Enemys loaded: " + enemies.size);
        Gdx.app.log(TAG, enemies.toString());
    }

    public void render() {
        viewport.apply();
        OrthographicCamera ocam = (OrthographicCamera) viewport.getCamera();
        tmr.setView(ocam);
        Batch batch = tmr.getBatch();
        batch.begin();
        parallaxBackground.draw(ocam, batch);
        tmr.setView(ocam);
        batch.end();
        tmr.render();
        batch.begin();
        for(Enemy enemy : enemies){
            enemy.render(batch);
        }
        jack.render(batch);
        parallaxForground.draw(ocam, batch);
        batch.end();
        if(debug){
            renderDebug(viewport.getCamera());
        }
    }

    public void update(float delta){
        jack.update(delta);
        for(Enemy enemy : enemies){
            enemy.facing(jack.getPosition());
            enemy.update(delta);
        }
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

    /*
        Since Jack is larger then 1 tile, Collisions can occur
        on two tiles at the same time, both requiring ledge check
     */
    public Rectangle isCorner(Rectangle rect){
        int x = (int)(rect.x/16);
        int y = (int)(rect.y/16);
        if(tileLayer.getCell(x,y+1) == null){
            Rectangle corner = rectPool.obtain();
            corner.set(x*16, y*16, 16,16);
            //Gdx.app.log(TAG, "is a corner " +  x + ", " + y);
            return corner;
        } else if(tileLayer.getCell(x, y+2) == null){
            Rectangle corner = rectPool.obtain();
            corner.set(x*16, (y+1)*16, 16,16);
            //Gdx.app.log(TAG, "below a corner " + x + ", " + y);
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
        endY = (int)(bounds.y + bounds.height);

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

    private void renderDebug (Camera camera) {
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        //Jack debug box
        debugRenderer.setColor(Color.RED);
        debugRenderer.rect(
                jack.getPosition().x,
                jack.getPosition().y,
                Constants.RVOS_WIDTH,
                Constants.JACK_HEIGHT);

        debugRenderer.setColor(Color.WHITE);
        float offset = Constants.JACK_ATTACK_RANGE;
        if(jack.actionState == Enums.ActionState.ATTACKING){
           switch(jack.facing){
               case LEFT:
                   debugRenderer.rect(
                           jack.getPosition().x,
                           jack.getPosition().y,
                           -offset,
                           Constants.JACK_HEIGHT);
                   break;
               case RIGHT:
                   debugRenderer.rect(
                           jack.getPosition().x + Constants.RVOS_WIDTH,
                           jack.getPosition().y,
                           offset,
                           Constants.JACK_HEIGHT);
                   break;
           }
        }

        //Enemy debug box
        for(Enemy enemy : enemies){
            debugRenderer.setColor(Color.BLUE);
            debugRenderer.rect(
                    enemy.getBounds().x,
                    enemy.getBounds().y,
                    enemy.getBounds().width,
                    enemy.getBounds().height
            );
            //Gdx.app.log(TAG, enemy.toString());
        }
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
