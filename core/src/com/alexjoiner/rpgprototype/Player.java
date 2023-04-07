package com.alexjoiner.rpgprototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private SpriteBatch batch;
    private SpriteProcessor spriteProcessor;

    private Rectangle playerBoundingBox;

    private float walkingTimer;

    private Animation<TextureRegion> currentAnimation;

    private String direction;

    private MapObjects mapObjects;



    public Player(SpriteBatch batch, SpriteProcessor spriteProcessor, MapObjects mapObjects) {

        this.batch = batch;

        this.spriteProcessor = spriteProcessor;

        this.mapObjects = mapObjects;

        playerBoundingBox = new Rectangle(2, 2, 16, 16);

        currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        direction = "down";

        walkingTimer = 0;
    }

    public void renderAndUpdate(float delta){
        walkingTimer += delta;
        batch.draw(currentAnimation.getKeyFrame(walkingTimer, true), playerBoundingBox.x, playerBoundingBox.y);

    }

    public void detectInput(float delta) {

        float speed = 48f;
        for (RectangleMapObject rectangleObject : mapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerBoundingBox)) {
                if (direction.equals("up")) {
                    playerBoundingBox.y -= speed * delta;
                } else if (direction.equals("down")) {
                    playerBoundingBox.y += speed * delta;
                } else if (direction.equals("left")) {
                    playerBoundingBox.x += speed * delta;
                } else if (direction.equals("right")) {
                    playerBoundingBox.x -= speed * delta;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerBoundingBox.y += speed * delta;
            direction = "up";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingUp");
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBoundingBox.y -= speed * delta;
            direction = "down";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBoundingBox.x -= speed * delta;
            direction = "left";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingLeft");
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBoundingBox.x += speed * delta;
            direction = "right";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingRight");
        } else {
            if (direction.equals("up")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleUp");
            } else if (direction.equals("down")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleDown");
            } else if (direction.equals("right")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleRight");
            } else if (direction.equals("left")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleLeft");
            }
        }



    }

    public Rectangle getPlayerBoundingBox() {
        return playerBoundingBox;
    }
}
