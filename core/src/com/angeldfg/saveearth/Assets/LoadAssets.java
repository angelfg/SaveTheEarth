package com.angeldfg.saveearth.Assets;

import com.angeldfg.saveearth.Model.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.HashMap;

/**
 * Created by angel on 03/01/2016.
 */
public class LoadAssets {

    public static AssetManager assets = new AssetManager();

    public static HashMap<Planet.PLANET_NAMES,Texture> textura_Planets = new
            HashMap<Planet.PLANET_NAMES,Texture>();

    public static void loadGraphics(){
        FileHandle imageFileHandle = Gdx.files.internal("planets/sun.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.SOL, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/mercury.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.MERCURIO, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/venus.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.VENUS, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/earth.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.TIERRA, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/mars.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.MARTE, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/jupiter.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.JUPITER, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/saturn.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.SATURNO, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/uranus.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.URANO, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/neptune.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.NEPTUNO, new Texture(imageFileHandle));
        imageFileHandle = Gdx.files.internal("planets/pluton.jpg");
        textura_Planets.put(Planet.PLANET_NAMES.PLUTON, new Texture(imageFileHandle));

        assets.load("planets/baseplanet.g3db", Model.class);
        assets.finishLoading();

    }
    public static void disposeGraphics(){

        assets.clear();
        for (Texture textura : textura_Planets.values()){
            textura.dispose();
        }
    }
    
}
