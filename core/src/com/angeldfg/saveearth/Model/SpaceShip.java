package com.angeldfg.saveearth.Model;

import com.angeldfg.saveearth.Assets.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.utils.Array;

/**
 * Created by angel on 07/01/2016.
 */
public class SpaceShip {
    public static enum Keys {TURN_LEFT, TURN_RIGHT, UP, DOWN, ACCELERATE, BRAKE, STOP,IDLE};

    public final static int MAX_NUM_BULLETS=10;

    public static final float SCALE = World3D.SCALE_SUN/900;
    public static final float SCALE_FROM_BLENDER = 4.707f;

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
    public final static float MAX_VELOCITY =500f;
    /**
     * Rotation velocity
     */
    private final static float ROTATION_VELOCITY =35f;

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

    /**
     * Bullets
     */
    private Array<Bullet> bullets;

    /**
     * Detect impact
     */
    private Sphere sphere;

    private int numBulletsActive=0;
    private float chronoNextFire=1; // wait 1 second to the next fire

    private Matrix4 matrix;


    public SpaceShip(){
        //position = new Vector3(World3D.SCALE_SUN*World3D.PIXEL_PER_2UNIT3D+60*World3D.PIXEL_PER_2UNIT3D+500,0,0);
        position = new Vector3(4000,0,1000);
        angle_rot = new Vector3(0,0,0);
        velocity =0f;
        state = Keys.STOP;
        direction = new Vector3(0,0,1);
        vectortemp = new Vector3();
        matrix = new Matrix4();


        bullets = new Array<Bullet>();

        sphere = new Sphere(new Vector3(0,0,0),SCALE*SCALE_FROM_BLENDER/2);

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
        if (velocity < MAX_VELOCITY) velocity +=25*Gdx.graphics.getDeltaTime();
        state = Keys.ACCELERATE;
    }
    /**
     * Dec  velocity .
     */
    public void decVelocity(){
        if(velocity >0) velocity -=25*Gdx.graphics.getDeltaTime();

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
            position.y+= UP_VELOCITY*Gdx.graphics.getDeltaTime();
        }
        if (Keys.DOWN ==key) {
            position.y-= UP_VELOCITY*Gdx.graphics.getDeltaTime();
        }

        direction.set(0,0,1);
        direction.rotate(angle_rot.y, 0,1,0);
        direction.nor();


    }

    public void addBullet(Bullet bullet) {
        if (chronoNextFire > 0 && chronoNextFire < 0.5f)
            return;
        Sounds.shoot.play();

        chronoNextFire=0.5f;
        bullets.add(bullet);
        numBulletsActive++;
    }

    /**
     * Update position spaceship
     * @param delta
     */
    public void update(float delta){
        vectortemp.set(direction);
        position.add(vectortemp.scl(velocity *delta));

        chronoNextFire-=delta;

        // Update fires
        for (Bullet bullet : bullets){
            // wait 0.5f second to next fire
            bullet.update(delta);
            if (bullet.getChrono()<=0) {
                bullets.removeValue(bullet,true);
                numBulletsActive--;
            }
        }


        matrix.idt();
        matrix.translate(position);
        sphere.center.set(position);

        matrix.rotate(0,1,0,angle_rot.y);
        if (getState()==Keys.TURN_LEFT){
            matrix.rotate(0,0,1,-10);
        }
        if (getState()==Keys.TURN_RIGHT){
            matrix.rotate(0,0,1,10);
        }
        matrix.scale(SCALE, SCALE, SCALE);


    }

    public Array<Bullet> getBullets() {
        return bullets;
    }
    public int getNumBulletsActive() {
        return numBulletsActive;
    }

    public void setNumBulletsActive(int numBulletsActive) {
        this.numBulletsActive = numBulletsActive;
    }

    public Sphere getSphere() {
        return sphere;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }

}
