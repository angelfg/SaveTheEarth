package com.angeldfg.saveearth.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by angel on 17/01/2016.
 */
public class Sounds {


    public static Sound shoot;
    public static Sound explosion;
    public static Sound finalsound;
    public static Sound applause;

    public static Music music;


    public static void initSoundsMusic(){


        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        applause = Gdx.audio.newSound(Gdx.files.internal("sounds/applause.mp3"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        finalsound = Gdx.audio.newSound(Gdx.files.internal("sounds/finalsound.mp3"));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/spacetrip.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);



    }
    public static void playMusic(){
        music.play();
    }

    public static void stopMusic(){

        if (music.isPlaying())
            music.stop();
    }

    public static void dispose(){
        music.dispose();
        shoot.dispose();
        applause.dispose();
        explosion.dispose();
        finalsound.dispose();

    }

}
