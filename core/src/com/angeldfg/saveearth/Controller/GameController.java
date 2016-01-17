package com.angeldfg.saveearth.Controller;

import com.angeldfg.saveearth.Model.Bullet;
import com.angeldfg.saveearth.Model.Planet;
import com.angeldfg.saveearth.Model.SpaceShip;
import com.angeldfg.saveearth.Model.Ufo;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.Screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.utils.Array;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by angel on 08/01/2016.
 */
public class GameController {

    public static enum Keys {TURN_LEFT, TURN_RIGHT, UP, DOWN, ACCELERATE, BRAKE, FIRE};

    /**
     * Use to stop ufo when it´s going to crash with a planet
     */
    private Sphere sphere;

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.DOWN, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.TURN_RIGHT, false);
        keys.put(Keys.TURN_LEFT, false);
        keys.put(Keys.ACCELERATE, false);
        keys.put(Keys.BRAKE, false);
        keys.put(Keys.FIRE, false);
    };

    private World3D world3D;
    private SpaceShip spaceShip;

    public GameController(World3D world3D){
        this.world3D = world3D;
        spaceShip = world3D.getSpaceShip();

        sphere = new Sphere(new Vector3(0,0,0),spaceShip.getSphere().radius);
    }


    public void pressKey(Keys key) {
        keys.put(key, true);
    }

    public void releaseKey(Keys key) {
        keys.put(key, false);
    }




    /**
     * Update planets
     * @param delta
     */
    public void updatePlanets(float delta){
        for(Planet planet : world3D.getPlanets()){
            planet.update(delta);
        }
    }

    /**
     * Update spaceship position
     * @param delta
     */
    private void updateSpaceShip(float delta){

        spaceShip.update(delta);
    }

    private void updateUfos(float delta){

        Vector3 tmp = new Vector3();
        for (Ufo ufo : world3D.getUfos()){
            tmp.set(world3D.getPlanets().get(3).getPosition());
            tmp.rotate(world3D.getPlanets().get(3).getAngle_tran(),0,1,0);
            ufo.updateDirection(tmp);
            ufo.update(delta);
        }

    }


    private void controlBullets(){

        for (Bullet bullet : spaceShip.getBullets()){

            for (Ufo ufo : world3D.getUfos()){
                if (bullet.getSphere().overlaps(ufo.getSphere())){      // IMPACT UFO - BULLET
                    Gdx.app.log("BALA BULLET-UFO:",String.valueOf(bullet.getSphere().center)+String.valueOf(bullet.getSphere().radius)+"---UFO:" + String.valueOf(ufo.getSphere().center)+":"+String.valueOf(ufo.getSphere().radius));
                    spaceShip.getBullets().removeValue(bullet,true);
                    world3D.addNumAlienDead(1);
                    ufo.setDead(true);
                }


                }
            for(Planet planet : world3D.getPlanets()){              // IMPACT BULLET-PLANET
                if (planet.getSphere().overlaps(bullet.getSphere())){
                    spaceShip.getBullets().removeValue(bullet,true);
                }


            }
        }


    }
    private void controlUfos(){
        Vector3 tmp = new Vector3();

        if (world3D.getUfos().size==0){
            if (world3D.getNumAlienDead()==World3D.NUMBER_ALIENS_GAME){
                GameScreen.winGame =true;
                GameScreen.endGame =true;
                return;
            }
            world3D.initUfos();
        }


        for (Ufo ufo : world3D.getUfos()){

            if (ufo.isDead() && ufo.getCronoExplosion()<=0){
                world3D.getUfos().removeValue(ufo,true);
            }

            for(Planet planet : world3D.getPlanets()){

                sphere.center.set(ufo.getSphere().center);
                sphere.radius = ufo.getSphere().radius + 100;

                if (sphere.overlaps(planet.getSphere())){
                    ufo.setCrashPlanet(true);
                }
                else {
                    ufo.setCrashPlanet(false);
                }


                if (planet.getSphere().overlaps(ufo.getSphere())){   // IMPACT UFO-PLANET
                    world3D.getUfos().removeValue(ufo,true);
                    world3D.addNumAlienDead(1);
                }

            }

            if (ufo.getSphere().overlaps(spaceShip.getSphere())) {      // IMPACT UFO-SPACESHIP
                Gdx.app.log("DATOS","CHOCANDO UFO - NAVE");
            }
        }
    }

    private void controlSpaceShip(){
        Vector3 tmp = new Vector3();
        for (Planet planet : world3D.getPlanets()){

                if (planet.getSphere().overlaps(spaceShip.getSphere())){      // IMPACT PLANET - SPACESHIP
                    Gdx.app.log("DATOS","CHOCANDO");
                    GameScreen.winGame =false;
                    GameScreen.endGame=true;
                    return;

                }
        }
        for (Ufo ufo : world3D.getUfos()){
            if (ufo.getSphere().overlaps(spaceShip.getSphere())){       // IMPACT UFO - Spacechip
                GameScreen.winGame =false;
                GameScreen.endGame=true;
                return;

            }

        }


//        Gdx.app.log("SOL:", String.valueOf(world3D.getPlanets().get(0).getSphere().center)+ "- NAVE:"+String.valueOf(world3D.getSpaceShip().getSphere().center));
//        Gdx.app.log("RADIO SOL:", String.valueOf(world3D.getPlanets().get(0).getSphere().radius)+ "- NAVE:"+String.valueOf(world3D.getSpaceShip().getSphere().radius));


    }


    public void update(float delta) {

      //  Gdx.app.log("DATOS:",String.valueOf(world3D.getUfos().get(0).getPosition())+"VELOC:" + String.valueOf(world3D.getUfos().get(0).getVelocity()) + "-"+String.valueOf(world3D.getSpaceShip().getPosition()));

        updatePlanets(delta);
        updateSpaceShip(delta);
        updateUfos(delta);
        processInput();

        controlUfos();
        controlSpaceShip();
        controlBullets();
    }

    private void processInput(){
        if (keys.get(Keys.ACCELERATE)){
            spaceShip.incVelocity();
        }else if (keys.get(Keys.BRAKE)){
            spaceShip.decVelocity();
        }

        /*  DIFICULT TO PLAY
        if (keys.get(Keys.DOWN)){
            spaceShip.rotate(SpaceShip.Keys.DOWN);
        }
        if (keys.get(Keys.UP)){
            spaceShip.rotate(SpaceShip.Keys.UP);
        }
        */
        if (keys.get(Keys.TURN_RIGHT)){
            spaceShip.rotate(SpaceShip.Keys.TURN_RIGHT);
        }
        if (keys.get(Keys.TURN_LEFT)){
            spaceShip.rotate(SpaceShip.Keys.TURN_LEFT);
        }


        if ((!keys.get(Keys.DOWN)) && (!keys.get(Keys.UP)) && (!keys.get(Keys.TURN_RIGHT)) && (!keys.get(Keys.TURN_LEFT)))
            spaceShip.setState(SpaceShip.Keys.IDLE);

        if (keys.get(Keys.FIRE)){
            if (world3D.getSpaceShip().getNumBulletsActive() < SpaceShip.MAX_NUM_BULLETS)
                world3D.getSpaceShip().addBullet(new Bullet(spaceShip.getMatrix().cpy(),spaceShip.getDirection().cpy(),spaceShip.getVelocity()));
               // world3D.getSpaceShip().addBullet(new Bullet(spaceShip.getPosition().cpy(),spaceShip.getDirection().cpy(),spaceShip.getVelocity(),spaceShip.getAngle_rot().y));
        }

    }


}
