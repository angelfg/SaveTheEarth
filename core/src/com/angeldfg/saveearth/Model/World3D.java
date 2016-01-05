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

    private Array<Planet> planets = new Array<Planet>();
    public World3D(){
        initPlanet();
    }

    /**
     * Crea os planetas no array
     */
    private void initPlanet(){
        planets.add(new Planet(0,0,0,1f,0f,Planet.PLANET_NAMES.SOL,SCALE_SUN));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+100*PIXEL_PER_2UNIT3D,0,0,1f,20f,Planet.PLANET_NAMES.MERCURIO,1f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+200*PIXEL_PER_2UNIT3D,0,0,0.1f,7f,Planet.PLANET_NAMES.VENUS,3f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+300*PIXEL_PER_2UNIT3D,0,0,10f,5f,Planet.PLANET_NAMES.TIERRA,3f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+450*PIXEL_PER_2UNIT3D,0,0,11f,3f,Planet.PLANET_NAMES.MARTE,2f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+1000*PIXEL_PER_2UNIT3D,0,0,30f,2f,Planet.PLANET_NAMES.JUPITER,25f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+2000*PIXEL_PER_2UNIT3D,0,0,25f,1f,Planet.PLANET_NAMES.SATURNO,20f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+4000*PIXEL_PER_2UNIT3D,0,0,22f,0.5f,Planet.PLANET_NAMES.URANO,12f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+8000*PIXEL_PER_2UNIT3D,0,0,21f,0.1f,Planet.PLANET_NAMES.NEPTUNO,11f));
        planets.add(new Planet(SCALE_SUN*PIXEL_PER_2UNIT3D+9000*PIXEL_PER_2UNIT3D,0,0,3f,0.05f,Planet.PLANET_NAMES.PLUTON,0.5f));
    }
    /**
     * Devolve o array cos planetas e o sol do sistema solar
     * @return the planetas
     */
    public Array<Planet> getPlanets() {

        return planets;
    }

}
