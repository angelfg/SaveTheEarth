package com.angeldfg.saveearth.Model;

import com.badlogic.gdx.utils.Array;

/**
 * Created by angel on 04/01/2016.
 */
public class World3D {
    /**
     * Os planetas e o sol do sistema solar
     */

    public final static int PIXEL_PER_2UNIT3D=50;
    public final static float SCALE_SUN=10f;    // 1 unit3d = 100000km
    public final static float SOLARSYSTEM_SIZE = 350*PIXEL_PER_2UNIT3D+SCALE_SUN*PIXEL_PER_2UNIT3D;    // PLUTON POSITION

    public final static int NUMBER_ALIENS_GAME=20;
    private Array<Planet> planets = new Array<Planet>();

    private Array<Ufo> ufos = new Array<Ufo>();

    private SpaceShip spaceShip;

    private int numAlienDead;

    public World3D(){

        initPlanet();
        initSpaceShips();
        initUfos();

        numAlienDead=0;

    }

    private void initSpaceShips(){
        spaceShip = new SpaceShip();

    }

    public void initUfos(){

        numAlienDead=0;
        for (int cont = 0; cont < Ufo.NUM_UFOS; cont++){
            ufos.add( new Ufo(planets.get(3).getPosition()));
        }
        ufos.add(new Ufo(spaceShip,planets.get(3).getPosition()));

    }
    /**
     * Create planets and sun
     */
    private void initPlanet(){

        planets.add(new Planet(0,0,0,0.01f,0f,Planet.PLANET_NAMES.SOL,SCALE_SUN));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+20*PIXEL_PER_2UNIT3D,0,0,0.02f,4f,Planet.PLANET_NAMES.MERCURIO,SCALE_SUN/90));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+40*PIXEL_PER_2UNIT3D,0,0,0.2f,3f,Planet.PLANET_NAMES.VENUS,SCALE_SUN/30));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+60*PIXEL_PER_2UNIT3D,0,0,20f,1.2f,Planet.PLANET_NAMES.TIERRA,SCALE_SUN/30));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+90*PIXEL_PER_2UNIT3D,0,0,22f,1f,Planet.PLANET_NAMES.MARTE,SCALE_SUN/64));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+150*PIXEL_PER_2UNIT3D,0,0,50f,0.9f,Planet.PLANET_NAMES.JUPITER,SCALE_SUN/3));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+220*PIXEL_PER_2UNIT3D,0,0,45f,0.8f,Planet.PLANET_NAMES.SATURNO,SCALE_SUN/5));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+260*PIXEL_PER_2UNIT3D,0,0,40f,0.4f,Planet.PLANET_NAMES.URANO,SCALE_SUN/9));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+300*PIXEL_PER_2UNIT3D,0,0,34f,0.2f,Planet.PLANET_NAMES.NEPTUNO,SCALE_SUN/10));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+350*PIXEL_PER_2UNIT3D,0,0,6f,0.1f,Planet.PLANET_NAMES.PLUTON,SCALE_SUN/150));
    }
    /**
     * return planets and sun
     * @return  planetas
     */
    public Array<Planet> getPlanets() {

        return planets;
    }

    public int getNumAlienDead() {
        return numAlienDead;
    }

    public void addNumAlienDead(int numAlienDead) {
        this.numAlienDead += 1;
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
