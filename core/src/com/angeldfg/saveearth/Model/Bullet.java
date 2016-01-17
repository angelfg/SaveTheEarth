package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;

/**
 * Created by angel on 03/01/2016.
 */
public class Bullet {

    private float scale = 0.5f;

    private Matrix4 matrix;
    private Vector3 position;
    private float velocity;
    private Vector3 direction;

    /**
     * Detect impact
     */
    private Sphere sphere;


    private Vector3 temp;

    private float chrono=5;


    public Bullet(){

    }
    /**
     *
     * @param position: position spaceship
     * @param direction: direction spaceship
     * @param velocity: velocity spaceship +50
     * @param angle_rot: angle rot spaceship
     */
    public Bullet(Matrix4 matrix, Vector3 direction,float velocity) {
        this.velocity = velocity+300;
        this.direction = direction;
        this.matrix=matrix;

        temp = new Vector3();
        position = new Vector3();
        position = matrix.getTranslation(position);


        position.add(direction);
        sphere = new Sphere(new Vector3(position.x,position.y,position.z),scale*2);

    }

    public Vector3 getPosition() {
        return position;
    }


    public void update(float delta) {

        temp.set(direction).scl(velocity * delta);


    //    matrix.getTranslation(position);
        // Update sphere
        position.add(temp);
        sphere.center.set(position);

//        matrix.setToTranslation(position);
//        matrix.scale(scale, scale, scale);
        matrix.idt();
        matrix.translate(position);
        matrix.scale(scale,scale,scale);

        chrono-=delta;
    }

    public Matrix4 getMatrix() {

        return matrix;
    }

    public float getChrono(){
        return chrono;
    }

    public float getVelocity() {
        return velocity;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Sphere getSphere() {
        return sphere;
    }

}
