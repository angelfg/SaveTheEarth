package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;

import java.util.Random;

/**
 * Created by angel on 03/01/2016.
 */
public class Planet {

    public static final float SCALE_FROM_BLENDER=100;

    public static enum PLANET_NAMES
    {SOL,MERCURIO,VENUS,TIERRA,MARTE,JUPITER,SATURNO,URANO,NEPTUNO,PLUTON};

    private Matrix4 matrix;
    private Vector3 position;
    private Vector3 rotation;
    private float angle_rot =0, angle_tran =0;
    private float rot_velocity =0, trans_velocity =0;
    private Vector3 scale = new Vector3();
    private PLANET_NAMES name_planet;

    private Vector3 temp;

    /**
     * Detect impact
     */
    private Sphere sphere;


    /**
     *
     * @param x: Pos x
     * @param y: Pos y
     * @param z: Pos z
     * @param velocidaderotacion: Rotation velocity
     * @param velocidadetraslacion: Translate velocity
     * @param nomeplaneta: Planet Name
     * @param scale: Scale
     */
    public Planet(float x, float y, float z,float velocidaderotacion,float velocidadetraslacion,
                  PLANET_NAMES nomeplaneta, float scale){

        position = new Vector3(x,y,z);
        this.trans_velocity =velocidadetraslacion;
        this.rot_velocity =velocidaderotacion;
        this.scale.set(scale,scale,scale);
        this.name_planet=nomeplaneta;


        temp = new Vector3();
        rotation = new Vector3(0,1,0);

        angle_tran = MathUtils.random(10,300);
        matrix = new Matrix4();
        sphere = new Sphere(new Vector3(position.x,position.y,position.z), SCALE_FROM_BLENDER*scale);


    }

    public Vector3 getPosition() {
        return position;
    }
    public float getAngle_rot() {
        return angle_rot;
    }
    public float getAngle_tran() {
        return angle_tran;
    }
    public float getRot_velocity() {
        return rot_velocity;
    }
    public float getTrans_velocity() {
        return trans_velocity;
    }
    public Vector3 getScale() {return scale;
    }
    public PLANET_NAMES getName_planet() {
        return name_planet;
    }


    public void update (float delta){
        angle_tran += trans_velocity*delta;
        angle_rot+=delta*rot_velocity;

        if (angle_tran>360) angle_tran=0;
        if (angle_rot>360) angle_rot=0;

        matrix.idt();
        matrix.setToRotation(rotation, angle_tran);
        matrix.translate(position);


        // Localtion sphere center
        matrix.getTranslation(sphere.center);

        matrix.scale(scale.x,scale.y,scale.z);
        matrix.rotate(rotation, angle_rot);


    }

    public Matrix4 getMatrix4(){

        return matrix;
    }
    public Sphere getSphere() {
        return sphere;
    }
}
