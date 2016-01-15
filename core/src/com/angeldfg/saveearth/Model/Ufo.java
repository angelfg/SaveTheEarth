package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by angel on 10/01/2016.
 */
public class Ufo {
    /**
     * NUMBER OF UFOS
     */
    public static final int NUM_UFOS = 5;

    /**
     * MIN SIZE
     */
    private static final float MIN_SIZE=World3D.SCALE_SUN/30;
    /**
     * MAX_SIZE
     */
    private static final float MAX_SIZE=World3D.SCALE_SUN/50;

    private float MIN_VELOCITY=SpaceShip.MAX_VELOCITY-200;
    private float MAX_VELOCITY=SpaceShip.MAX_VELOCITY-100;


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


    public Ufo(Vector3 earthPosition){

        this.position = new Vector3(MathUtils.random(World3D.SOLARSYSTEM_SIZE+3000,World3D.SOLARSYSTEM_SIZE+500),0,MathUtils.random(World3D.SOLARSYSTEM_SIZE+3000,500));
        position.rotate(MathUtils.random(1,360),0,1,0);
        this.velocity =MathUtils.random(MIN_VELOCITY,MAX_VELOCITY);
        this.scale=MathUtils.random(MIN_SIZE,MAX_SIZE);
        this.direction = (earthPosition.cpy().sub(position)).nor();
        tempvector=new Vector3(0,0,0);

        rotation = new Vector3(0,1,0);
        rot_velocity = MathUtils.random(1,10);

        matrix = new Matrix4();

       // Gdx.app.log("DATOS:", "UFO DATA: Position:" + String.valueOf(position)+"- Velocity:" + String.valueOf(velocity)+" -SCALE:"+ String.valueOf(SCALE));
    }

    public Ufo(SpaceShip spaceship,Vector3 earthPosition){

        this.position = spaceship.getPosition().cpy().add(0,0,100);
        this.velocity =MathUtils.random(MIN_VELOCITY,MAX_VELOCITY);
        this.scale=MathUtils.random(MIN_SIZE,MAX_SIZE);
        this.direction = (earthPosition.cpy().sub(position)).nor();
        tempvector=new Vector3(0,0,0);

        rotation = new Vector3(0,1,0);
        rot_velocity = MathUtils.random(50,100);

        matrix = new Matrix4();

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
        position.add(direction.scl(velocity *delta));

        angle_rot += rot_velocity*delta;
        if (angle_rot>360) angle_rot=0;

        matrix.setToTranslation(position);
        matrix.scale(scale,scale,scale);
        matrix.rotate(rotation,angle_rot);



    }

    public Matrix4 getMatrix4(){
        return matrix;

    }
}
