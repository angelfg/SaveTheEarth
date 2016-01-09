package com.angeldfg.saveearth.Controller;

import com.angeldfg.saveearth.Model.Planet;
import com.angeldfg.saveearth.Model.SpaceShip;
import com.angeldfg.saveearth.Model.World3D;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by angel on 08/01/2016.
 */
public class GameController {

    public static enum Keys {TURN_LEFT, TURN_RIGHT, UP, DOWN, ACCELERATE, BRAKE, STOP};

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.DOWN, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.TURN_RIGHT, false);
        keys.put(Keys.TURN_LEFT, false);
        keys.put(Keys.ACCELERATE, false);
        keys.put(Keys.BRAKE, false);
        keys.put(Keys.STOP, false);
    };

    private World3D world3D;
    private SpaceShip spaceShip;

    public GameController(World3D world3D){
        this.world3D = world3D;
        spaceShip = world3D.getSpaceShip();
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
    public void updateSpaceShip(float delta){
        spaceShip.update(delta);
    }


    public void update(float delta) {

        updatePlanets(delta);
        updateSpaceShip(delta);
        processInput();
    }

    private void processInput(){
        if (keys.get(Keys.ACCELERATE)){
            spaceShip.incVelocity();
        }else if (keys.get(Keys.BRAKE)){
            spaceShip.decVelocity();
        }

        if (keys.get(Keys.DOWN)){
            spaceShip.rotate(SpaceShip.Keys.DOWN);
        }
        if (keys.get(Keys.UP)){
            spaceShip.rotate(SpaceShip.Keys.UP);
        }
        if (keys.get(Keys.TURN_RIGHT)){
            spaceShip.rotate(SpaceShip.Keys.TURN_RIGHT);
        }
        if (keys.get(Keys.TURN_LEFT)){
            spaceShip.rotate(SpaceShip.Keys.TURN_LEFT);
        }


        if ((!keys.get(Keys.DOWN)) && (!keys.get(Keys.UP)) && (!keys.get(Keys.TURN_RIGHT)) && (!keys.get(Keys.TURN_LEFT)))
            spaceShip.setState(SpaceShip.Keys.IDLE);

        if (keys.get(Keys.STOP)){
            world3D.getSpaceShip().stop();
        }

    }


}
