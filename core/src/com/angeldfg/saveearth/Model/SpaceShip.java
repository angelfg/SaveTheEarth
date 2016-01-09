package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by angel on 07/01/2016.
 */
public class SpaceShip {
    public static enum Keys {TURN_LEFT, TURN_RIGHT, UP, DOWN, ACCELERATE, BRAKE, STOP,IDLE};
    /**
     * Indica se a nave está rotando ou parada. Usado polo campo de estrelas de fondo
     */
    private Keys state;

    /**
     * Posición da nave
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
    private final float MAX_VELOCIDADE=500f;
    /**
     * Rotation velocity
     */
    private final float ROTATION_VELOCITY =25f;

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
        position = new Vector3(16000,0,0);
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
     * Inc. velocity until MAX_VELOCIDADE
     */
    public void incVelocity(){
        if (velocity <MAX_VELOCIDADE) velocity +=20;
        state = Keys.ACCELERATE;
    }
    /**
     * Dec  velocity .
     */
    public void decVelocity(){
        if(velocity >0) velocity -=20;

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
        matrix.scale(0.1f,0.1f,0.1f);

    }

    public Matrix4 getMatrix() {
        return matrix;
    }

}
