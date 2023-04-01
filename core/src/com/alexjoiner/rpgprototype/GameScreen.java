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


    private final float playerSize = 16;
    private final float WORLD_WIDTH = playerSize * 15;
    private final float WORLD_HEIGHT = playerSize * 15;

    private TextureAtlas textureAtlas;
    private TextureRegion[] playerWalkingDownTexReg, playerWalkingUpTexReg,
            playerWalkingLeftTexReg, playerWalkingRightTexReg,
            playerIdleUpTexReg,playerIdleDownTexReg,
            playerIdleRightTexReg,playerIdleLeftTexReg;
    private float playerWalkingAnimSpeed = 0.20f;
    private float walkingTimer;
    private Animation<TextureRegion> playerWalkingDownAnim, playerWalkingUpAnim,
            playerWalkingRightAnim, playerWalkingLeftAnim, playerIdleDownAnim,
    playerIdleUpAnim,playerIdleRightAnim,playerIdleLeftAnim;
    private Animation<TextureRegion> currentAnimation;
    private String direction;

    public GameScreen() {

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        playerBoundingBox = new Rectangle(2, 2, 16, 16);

        textureAtlas = new TextureAtlas("player_atlas.atlas");
        playerWalkingDownTexReg = createSpriteTextureRegion("walking_down", 1, 4);
        playerWalkingUpTexReg = createSpriteTextureRegion("walking_up", 1, 4);
        playerWalkingRightTexReg = createSpriteTextureRegion("walking_right", 1, 4);
        playerWalkingLeftTexReg = createSpriteTextureRegion("walking_left", 1, 4);

        playerWalkingDownAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingDownTexReg);
        playerWalkingUpAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingUpTexReg);
        playerWalkingRightAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingRightTexReg);
        playerWalkingLeftAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingLeftTexReg);


        playerIdleUpTexReg = new TextureRegion[1];
        playerIdleDownTexReg = new TextureRegion[1];
        playerIdleRightTexReg = new TextureRegion[1];
        playerIdleLeftTexReg = new TextureRegion[1];
        playerIdleUpTexReg[0] = textureAtlas.findRegion("walking_up4");
        playerIdleDownTexReg[0] = textureAtlas.findRegion("walking_down4");
        playerIdleRightTexReg[0] = textureAtlas.findRegion("walking_right4");
        playerIdleLeftTexReg[0] = textureAtlas.findRegion("walking_left4");
        playerIdleUpAnim = new Animation<TextureRegion>(0, playerIdleUpTexReg);
        playerIdleDownAnim = new Animation<TextureRegion>(0, playerIdleDownTexReg);
        playerIdleRightAnim = new Animation<TextureRegion>(0, playerIdleRightTexReg);
        playerIdleLeftAnim = new Animation<TextureRegion>(0, playerIdleLeftTexReg);

        walkingTimer = 0;

        currentAnimation = playerIdleDownAnim;
        direction = "down";

        batch = new SpriteBatch();


    }


    public TextureRegion[] createSpriteTextureRegion(String spriteSheetName, int startNumber, int count) {
        TextureRegion[] tempTextureRegion = new TextureRegion[count];
        for (int i = 0; i < count; i++) {
            tempTextureRegion[i] = textureAtlas.findRegion(spriteSheetName + startNumber);
            startNumber++;
        }
        return tempTextureRegion;
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
            currentAnimation = playerWalkingUpAnim;
            direction = "up";
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBoundingBox.y -= speed * delta;
            currentAnimation = playerWalkingDownAnim;
            direction = "down";
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBoundingBox.x -= speed * delta;
            currentAnimation = playerWalkingLeftAnim;
            direction = "left";
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBoundingBox.x += speed * delta;
            currentAnimation = playerWalkingRightAnim;
            direction = "right";
        }else{
            if(direction.equals("up")){
                currentAnimation = playerIdleUpAnim;
            } else if(direction.equals("down")){
                currentAnimation = playerIdleDownAnim;
            } else if(direction.equals("right")){
                currentAnimation = playerIdleRightAnim;
            } else if(direction.equals("left")){
                currentAnimation = playerIdleLeftAnim;
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
