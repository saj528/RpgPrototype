package com.alexjoiner.rpgprototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    Rectangle playerBoundingBox;


    SpriteProcessor spriteProcessor;

    private float walkingTimer;
    private final float playerSize = 16;
    private final float WORLD_WIDTH = playerSize * 15;
    private final float WORLD_HEIGHT = playerSize * 15;


    private Animation<TextureRegion> currentAnimation;
    private String direction;

    public GameScreen() {

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteProcessor = new SpriteProcessor();

        playerBoundingBox = new Rectangle(2, 2, 16, 16);



        walkingTimer = 0;

        currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        direction = "down";

        batch = new SpriteBatch();


    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        walkingTimer += delta;

        detectInput(delta);

        batch.begin();
        batch.draw(currentAnimation.getKeyFrame(walkingTimer, true), playerBoundingBox.x, playerBoundingBox.y);
        batch.end();

    }

    private void detectInput(float delta) {
        float speed = 48f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerBoundingBox.y += speed * delta;
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingUp");
            direction = "up";
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBoundingBox.y -= speed * delta;
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
            direction = "down";
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBoundingBox.x -= speed * delta;
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingLeft");
            direction = "left";
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBoundingBox.x += speed * delta;
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingRight");
            direction = "right";
        }else{
            if(direction.equals("up")){
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleUp");
            } else if(direction.equals("down")){
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleDown");
            } else if(direction.equals("right")){
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleRight");
            } else if(direction.equals("left")){
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleLeft");
            }
        }
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
