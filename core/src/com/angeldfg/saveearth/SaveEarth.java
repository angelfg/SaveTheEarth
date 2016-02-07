package com.angeldfg.saveearth;

import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Screen.PrincipalScreen;
import com.badlogic.gdx.Game;


public class SaveEarth extends Game {



	@Override
	public void create() {
		Sounds.initSoundsMusic();

		LoadAssets.loadGraphics();

		setScreen(new PrincipalScreen(this));


	}

	@Override
	public void dispose(){
		Sounds.dispose();
		LoadAssets.disposeGraphics();
	}
}
