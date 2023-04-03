package com.alexjoiner.rpgprototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch batch;

    SpriteProcessor spriteProcessor;

    ShapeRenderer shapeRenderer;

    Player player;


    private final float playerSize = 16;
    private final float WORLD_WIDTH = playerSize * 15;
    private final float WORLD_HEIGHT = playerSize * 15;

    Rectangle wallBoundingox = new Rectangle(-50,-50,16,128);




    public GameScreen() {

        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteProcessor = new SpriteProcessor();

        batch = new SpriteBatch();

        player = new Player(batch,spriteProcessor);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        player.detectInput(delta);



        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(wallBoundingox.x, wallBoundingox.y, wallBoundingox.width, wallBoundingox.height);
        shapeRenderer.setColor(0,0,1,0.1f);

        shapeRenderer.rect(player.getPlayerBoundingBox().x, player.getPlayerBoundingBox().y, player.getPlayerBoundingBox().width, player.getPlayerBoundingBox().height);
        shapeRenderer.end();

        batch.begin();
        player.renderAndUpdate(delta);

        batch.end();

    }




    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
