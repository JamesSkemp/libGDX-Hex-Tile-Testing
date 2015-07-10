package com.jamesrskemp.hextiletest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jamesrskemp.hextiletest.HexTileTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hex Tile Test";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new HexTileTest(), config);
	}
}
