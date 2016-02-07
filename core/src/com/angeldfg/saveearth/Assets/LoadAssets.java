package com.angeldfg.saveearth.Assets;

import com.angeldfg.saveearth.Model.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by angel on 03/01/2016.
 */
public class LoadAssets {


    public static HashMap<Planet.PLANET_NAMES,Texture> texture_planets = new
            HashMap<Planet.PLANET_NAMES,Texture>();

    public static Texture texture_radar;
    public static Texture texture_static_earth;
    public static Texture texture_static_ufo;
    public static Texture texture_static_fieldStars;

    public static Skin skin;
    public static ParticleSystem particleSystem;
    public static PointSpriteParticleBatch pointSpriteParticleBatch;

    public static Model modelUfo;
    public static Model modelPlanet;
    public static Model modelSpaceShip;
    public static ParticleEffect effect;

    public static TextureAtlas atlas;

    public static EnumMap<Controls.CONTROLS, Texture> textures_controls = new EnumMap<Controls.CONTROLS, Texture>(Controls.CONTROLS.class);	// Pon o ENUM como key

    private static AssetManager assets;


    public static void loadGraphics(){

        assets = new AssetManager();

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
        textures_controls.put(Controls.CONTROLS.FIRE,new Texture("control/fire.png"));


        texture_radar = new Texture("radar/radar.png");
        texture_static_earth = new Texture("radar/staticearth.png");
        texture_static_ufo = new Texture("ufo/staticufo.png");

        texture_static_fieldStars = new Texture("fieldstars.jpg");
        texture_static_fieldStars.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        assets.load("planets/baseplanet.g3db", Model.class);
        assets.load("spaceship/spaceship.g3db", Model.class);
        assets.load("ufo/ufo.g3db", Model.class);

        assets.load("fonts/uiskin.atlas",TextureAtlas.class);

        // Particle System 3D
        particleSystem = ParticleSystem.get();
        particleSystem.getBatches().clear();
        pointSpriteParticleBatch = new PointSpriteParticleBatch();
        particleSystem.add(pointSpriteParticleBatch);

        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assets.setLoader(ParticleEffect.class, loader);
        assets.load("particle3d/explosion.pfx", ParticleEffect.class,loadParam);

        assets.finishLoading();

        atlas = assets.get("fonts/uiskin.atlas", TextureAtlas.class);

        modelUfo = assets.get("ufo/ufo.g3db",Model.class);
        modelPlanet = assets.get("planets/baseplanet.g3db",Model.class);
        modelSpaceShip=assets.get("spaceship/spaceship.g3db",Model.class);


        ParticleEffect originalEffect = assets.get("particle3d/explosion.pfx");
        effect = originalEffect.copy();
        effect.init();
        effect.start();  // optional: particle will begin playing immediately
        particleSystem.add(effect);


    }


    public static void disposeGraphics(){
        modelUfo.dispose();
        modelPlanet.dispose();
        modelSpaceShip.dispose();
        effect.dispose();

        for (Texture textura : texture_planets.values()){
            textura.dispose();
        }
        atlas.dispose();

    }
    
}
