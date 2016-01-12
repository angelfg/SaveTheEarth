package com.angeldfg.saveearth.Assets;

import com.angeldfg.saveearth.Model.Planet;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by angel on 03/01/2016.
 */
public class LoadAssets {

    public static AssetManager assets = new AssetManager();

    public static HashMap<Planet.PLANET_NAMES,Texture> texture_planets = new
            HashMap<Planet.PLANET_NAMES,Texture>();

    public static EnumMap<Controls.CONTROLS, Texture> textures_controls = new EnumMap<Controls.CONTROLS, Texture>(Controls.CONTROLS.class);	// Pon o ENUM como key


    public static void loadGraphics(){
        texture_planets.put(Planet.PLANET_NAMES.SOL, new Texture("planets/sun.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.MERCURIO, new Texture("planets/mercury.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.VENUS, new Texture("planets/venus.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.TIERRA, new Texture("planets/earth.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.MARTE, new Texture("planets/mars.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.JUPITER, new Texture("planets/jupiter.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.SATURNO, new Texture("planets/saturn.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.URANO, new Texture("planets/uranus.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.NEPTUNO, new Texture("planets/neptune.jpg"));
        texture_planets.put(Planet.PLANET_NAMES.PLUTON, new Texture("planets/pluton.jpg"));

        //Load controls
        textures_controls.put(Controls.CONTROLS.ACCELERATE,new Texture("control/accelerate.png"));
        textures_controls.put(Controls.CONTROLS.BRAKE,new Texture("control/brake.png"));
        textures_controls.put(Controls.CONTROLS.DOWN,new Texture("control/down.png"));
        textures_controls.put(Controls.CONTROLS.LEFT,new Texture("control/left.png"));
        textures_controls.put(Controls.CONTROLS.RIGHT,new Texture("control/right.png"));
        textures_controls.put(Controls.CONTROLS.UP,new Texture("control/up.png"));

        assets.load("planets/baseplanet.g3db", Model.class);
        assets.load("spaceship/spaceship.g3db", Model.class);

        assets.load("asteriod/asteriod.g3db", Model.class);
        assets.load("ufo/ufo.g3db", Model.class);





        assets.finishLoading();

    }
    public static void disposeGraphics(){

        assets.clear();
        for (Texture textura : texture_planets.values()){
            textura.dispose();
        }
    }
    
}
