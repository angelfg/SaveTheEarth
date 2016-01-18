package com.angeldfg.saveearth;

import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Screen.ControlsScreen;
import com.angeldfg.saveearth.Screen.CreditScreen;
import com.angeldfg.saveearth.Screen.GameScreen;
import com.angeldfg.saveearth.Screen.PrincipalScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SaveEarth extends Game {


	@Override
	public void create() {
		Sounds.initSoundsMusic();
		setScreen(new PrincipalScreen(this));

	}

	@Override
	public void dispose(){
		Sounds.dispose();
	}
}
