package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by angel on 10/01/2016.
 */
public class Ufo {
    /**
     * NUMBER OF UFOS
     */
    public static final int NUM_UFOS = 5;


    private float MIN_VELOCITY=SpaceShip.MAX_VELOCITY-200f;
    private float MAX_VELOCITY=SpaceShip.MAX_VELOCITY-100f;


    /**
     * If ufo is going to crash with a Planet, change direction
     */
    private boolean crashPlanet;

    /**
     * Crono when UFO exploit
     */
    private float cronoExplosion;

    private boolean isDead;


    public static final float SCALE_FROM_BLENDER=100f;

    /**
     * POSITION
     */
    private Vector3 position;


    /**
     * direction to Earth
     */
    private Vector3 direction;

    /**
     * Velocity
     */
    private float velocity;

    private float angle_rot;
    private float rot_velocity;
    private Vector3 rotation;

    /**
     * SCALE
     */
    private float scale;

    private Matrix4 matrix;

    private Vector3 tempvector;

    /**
     * Detect impact
     */
    private Sphere sphere;

    public Ufo(Vector3 earthPosition){

        this.position = new Vector3(MathUtils.random(World3D.SOLARSYSTEM_SIZE+50,World3D.SOLARSYSTEM_SIZE+500),0,MathUtils.random(World3D.SOLARSYSTEM_SIZE,World3D.SOLARSYSTEM_SIZE+100));
        position.rotate(MathUtils.random(1,360),0,1,0);
        this.velocity =MathUtils.random(MIN_VELOCITY,MAX_VELOCITY);
        this.scale=1;
        this.direction = (earthPosition.cpy().sub(position)).nor();
        tempvector=new Vector3(0,0,0);

        rotation = new Vector3(0,1,0);
        rot_velocity = MathUtils.random(10,50);

        matrix = new Matrix4();

        sphere = new Sphere(new Vector3(0,0,0),scale*SCALE_FROM_BLENDER);

        crashPlanet = false;

        cronoExplosion=3;       // 3 sec
        isDead=false;


       // Gdx.app.log("DATOS:", "UFO DATA: Position:" + String.valueOf(position)+"- Velocity:" + String.valueOf(velocity)+" -SCALE:"+ String.valueOf(SCALE));
    }

    public Ufo(SpaceShip spaceship,Vector3 earthPosition){

        this.position = spaceship.getPosition().cpy().add(0,0,100);
        this.velocity =MathUtils.random(MIN_VELOCITY,MAX_VELOCITY);
        this.scale=MathUtils.random(World3D.SCALE_SUN/40,World3D.SCALE_SUN/60);
        this.direction = (earthPosition.cpy().sub(position)).nor();
        tempvector=new Vector3(0,0,0);

        rotation = new Vector3(0,1,0);
        rot_velocity = MathUtils.random(50,100);

        matrix = new Matrix4();

        sphere = new Sphere(new Vector3(0,0,0),scale*SCALE_FROM_BLENDER);

        cronoExplosion=3;       // 3 sec
        isDead=false;
    }


    public float getScale(){
        return scale;
    }

    public Vector3 getPosition(){
        return position;
    }

    /**
     * Update direction
     * @param earthPosition: new position of Earth
     */
    public void updateDirection(Vector3 earthPosition){
        tempvector.set(earthPosition);
        direction.set((tempvector.sub(position)).nor());
    }
    /**
     * Move o asteroide
     * @param delta
     */
    public void update(float delta){
        tempvector.set(direction);
/*
       if (crashPlanet){
            position.sub(direction.scl(velocity *delta));
        }
        else{
            position.add(direction.scl(velocity *delta));
        }
*/
        if (isDead){
            cronoExplosion-=delta;

        }
        else{
            angle_rot += rot_velocity*delta;
            if (angle_rot>360) angle_rot=0;

            matrix.setToTranslation(position);
            sphere.center.set(position);

            matrix.scale(scale,scale,scale);
            matrix.rotate(rotation,angle_rot);

        }
 


    }

    public Matrix4 getMatrix(){
        return matrix;

    }

    public Sphere getSphere() {
        return sphere;
    }

    public boolean isCrashPlanet() {
        return crashPlanet;
    }

    public void setCrashPlanet(boolean crashPlanet) {
        this.crashPlanet = crashPlanet;
    }

    public float getVelocity() {
        return velocity;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
    public float getCronoExplosion() {
        return cronoExplosion;
    }



}
