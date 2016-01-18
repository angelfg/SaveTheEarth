package com.angeldfg.saveearth.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.angeldfg.saveearth.SaveEarth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=800;
		config.height=480;
		config.addIcon("planets/iconearth.jpg", Files.FileType.Internal);
		new LwjglApplication(new SaveEarth(), config);
	}
}
