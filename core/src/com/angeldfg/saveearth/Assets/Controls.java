package com.angeldfg.saveearth.Assets;

import com.angeldfg.saveearth.View.GameRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumMap;

/**
 * Created by angel on 08/01/2016.
 */
public class Controls {

    public static enum CONTROLS {UP, DOWN, LEFT, RIGHT, ACCELERATE, BRAKE, STOP, FIRE};
    public final static float SIZE = 40;

    public static EnumMap<CONTROLS,Rectangle> size_controls = new EnumMap <CONTROLS,Rectangle>(CONTROLS.class);

    public static void changeSizeControls(){

        OrthographicCamera cam2d= GameRenderer.getCamera2D();

        size_controls.put(CONTROLS.DOWN,new Rectangle(cam2d.viewportWidth-SIZE*2,10,SIZE,SIZE));
        size_controls.put(CONTROLS.UP,new Rectangle(cam2d.viewportWidth-SIZE*2,SIZE*2+10,SIZE,SIZE));
        size_controls.put(CONTROLS.RIGHT,new Rectangle(cam2d.viewportWidth-SIZE,SIZE+10,SIZE,SIZE));
        size_controls.put(CONTROLS.LEFT,new Rectangle(cam2d.viewportWidth-SIZE*4,SIZE+10,SIZE,SIZE));
        size_controls.put(CONTROLS.FIRE,new Rectangle(cam2d.viewportWidth-SIZE*3f,SIZE-10,SIZE*2,SIZE*2));

        size_controls.put(CONTROLS.ACCELERATE,new Rectangle(5,SIZE*3,SIZE,SIZE));
        size_controls.put(CONTROLS.BRAKE,new Rectangle(5,SIZE,SIZE,SIZE));


    }
}
