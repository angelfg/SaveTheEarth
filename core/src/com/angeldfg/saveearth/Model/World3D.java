package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.utils.Array;

/**
 * Created by angel on 04/01/2016.
 */
public class World3D {
    /**
     * Os planetas e o sol do sistema solar
     */

    public final static int PIXEL_PER_2UNIT3D=100;
    public final static float SCALE_SUN=75f;    // 1 unit3d = 4000km
    public final static float SOLARSYSTEM_SIZE = SCALE_SUN*PIXEL_PER_2UNIT3D+9000*PIXEL_PER_2UNIT3D+500;    // PLUTON POSITION

    private Array<Planet> planets = new Array<Planet>();

    private Array<Ufo> ufos = new Array<Ufo>();

    private SpaceShip spaceShip;

    public World3D(){

        initPlanet();
        initSpaceShips();
        initUfos();

    }

    private void initSpaceShips(){
        spaceShip = new SpaceShip();

    }

    private void initUfos(){

        for (int cont = 0; cont < Ufo.NUM_UFOS; cont++){
            ufos.add( new Ufo(planets.get(3).getPosition()));
        }
        ufos.add(new Ufo(spaceShip,planets.get(3).getPosition()));

    }
    /**
     * Create planets and sun
     */
    private void initPlanet(){
        planets.add(new Planet(0,0,0,1f,0f,Planet.PLANET_NAMES.SOL,SCALE_SUN));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+100*PIXEL_PER_2UNIT3D,0,0,1f,2f,Planet.PLANET_NAMES.MERCURIO,1f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+200*PIXEL_PER_2UNIT3D,0,0,0.1f,0.7f,Planet.PLANET_NAMES.VENUS,3f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+300*PIXEL_PER_2UNIT3D,0,0,10f,0.5f,Planet.PLANET_NAMES.TIERRA,3f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+450*PIXEL_PER_2UNIT3D,0,0,11f,0.3f,Planet.PLANET_NAMES.MARTE,2f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+1000*PIXEL_PER_2UNIT3D,0,0,30f,0.2f,Planet.PLANET_NAMES.JUPITER,25f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+2000*PIXEL_PER_2UNIT3D,0,0,25f,0.1f,Planet.PLANET_NAMES.SATURNO,20f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+4000*PIXEL_PER_2UNIT3D,0,0,22f,0.05f,Planet.PLANET_NAMES.URANO,12f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+8000*PIXEL_PER_2UNIT3D,0,0,21f,0.01f,Planet.PLANET_NAMES.NEPTUNO,11f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+9000*PIXEL_PER_2UNIT3D,0,0,3f,0.01f,Planet.PLANET_NAMES.PLUTON,0.5f));
    }
    /**
     * return planets and sun
     * @return  planetas
     */
    public Array<Planet> getPlanets() {

        return planets;
    }

    /**
     * return array ufos
     * @return ufos
     */
    public Array<Ufo> getUfos() {
        return ufos;
    }


    public SpaceShip getSpaceShip() {
        return spaceShip;
    }


}
