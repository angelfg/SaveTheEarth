package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by angel on 07/01/2016.
 */
public class SpaceShip {
    public static enum Keys {TURN_LEFT, TURN_RIGHT, UP, DOWN, ACCELERATE, BRAKE, STOP,IDLE};

    private float scale = World3D.SCALE_SUN/900;
    /**
     * State of spaceship
     */
    private Keys state;

    /**
     * Spaceship position
     */
    private Vector3 position;

    /**
     * Rotation Angle Direction
     */
    private Vector3 angle_rot;

    /**
     * SpaceShip velocity
     */
    private float velocity;

    /**
     * Max velocity
     */
    public final static float MAX_VELOCITY =300f;
    /**
     * Rotation velocity
     */
    private final static float ROTATION_VELOCITY =25f;

    /**
     * Spaceship direction
     */
    private Vector3 direction;

    /**
     * Max velocity up
     */
    private final float UP_VELOCITY =1f;

    /**
     * Vector temp
     */
    private Vector3 vectortemp;


    private Matrix4 matrix;


    public SpaceShip(){
        position = new Vector3(3600,0,0);
        angle_rot = new Vector3(0,0,0);
        velocity =0f;
        state = Keys.STOP;
        direction = new Vector3(0,0,1);
        vectortemp = new Vector3();
        matrix = new Matrix4();
    }


    public Vector3 getPosition() {
        return position;
    }
    public Vector3 getDirection() {
        return direction;
    }


    public Keys getState() {
        return state;
    }

    public void setState(Keys state) {
        this.state = state;
    }


    public Vector3 getAngle_rot() {
        return angle_rot;
    }
    public float getVelocity() {
        return velocity;
    }


    /**
     * Inc. velocity until MAX_VELOCITY
     */
    public void incVelocity(){
        if (velocity < MAX_VELOCITY) velocity +=15*Gdx.graphics.getDeltaTime();
        state = Keys.ACCELERATE;
    }
    /**
     * Dec  velocity .
     */
    public void decVelocity(){
        if(velocity >0) velocity -=15*Gdx.graphics.getDeltaTime();

        if (velocity < 0) velocity=0;
        state = Keys.BRAKE;
    }

    /**
     * Stop
     */
    public void stop(){
        velocity =0;
        state = Keys.STOP;
    }

    /**
     * Rotate Spaceship
     */
    public void rotate(Keys key){

        state =key;
        if (Keys.TURN_LEFT ==key) {
            angle_rot.y += ROTATION_VELOCITY * Gdx.graphics.getDeltaTime();
        }
        if (Keys.TURN_RIGHT ==key) {
            angle_rot.y -= ROTATION_VELOCITY *Gdx.graphics.getDeltaTime();
        }
        if (Keys.UP ==key) {
            position.y+= UP_VELOCITY *Gdx.graphics.getDeltaTime();
        }
        if (Keys.DOWN ==key) {
            position.y-= UP_VELOCITY *Gdx.graphics.getDeltaTime();
        }


        direction.set(0,0,1);
        direction.rotate(angle_rot.y, 0,1,0);
        direction.nor();
    }

    /**
     * Update position spaceship
     * @param delta
     */
    public void update(float delta){


        vectortemp.set(direction);
        position.add(vectortemp.scl(velocity *delta));



        matrix.idt();
        matrix.translate(position);
        matrix.rotate(0,1,0,angle_rot.y);
        if (getState()==Keys.TURN_LEFT){
            matrix.rotate(0,0,1,-10);
        }
        if (getState()==Keys.TURN_RIGHT){
            matrix.rotate(0,0,1,10);
        }
        matrix.scale(scale,scale,scale);


    }

    public Matrix4 getMatrix() {
        return matrix;
    }

}
