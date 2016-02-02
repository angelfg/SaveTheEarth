package com.angeldfg.saveearth;

import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Pruebas.Screen1;
import com.angeldfg.saveearth.Screen.PrincipalScreen;
import com.badlogic.gdx.Game;


public class SaveEarth extends Game {


	@Override
	public void create() {
		Sounds.initSoundsMusic();
		setScreen(new PrincipalScreen(this));
		//setScreen(new Screen1(this));

	}

	@Override
	public void dispose(){
		Sounds.dispose();
	}
}
