package com.angeldfg.saveearth.Model;

import com.angeldfg.saveearth.View.GameRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by angel on 13/01/2016.
 */
public class Radar {

    public static Rectangle size_radar;
    public static Rectangle size_radar_minimized;



    /**
     * Indicate radar must be minimized in Render class
     */
    public static boolean minimize;


    /**
     * Change radar image size in function camera2d size
     */
    public static void changeSizeRadar(){

        OrthographicCamera cam2d= GameRenderer.getCamera2D();

        size_radar = new Rectangle(0,cam2d.viewportHeight/2,cam2d.viewportWidth/4,cam2d.viewportHeight-cam2d.viewportHeight/2);
        size_radar_minimized = new Rectangle(0,cam2d.viewportHeight-30,30,30);

    }

    public static boolean isMinimize() {
        return minimize;
    }

    public static void setMinimize(boolean minimize) {
        Radar.minimize = minimize;
    }


}
