package com.alexjoiner.rpgprototype;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.alexjoiner.rpgprototype.RpgPrototypeGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Rpg Prototype");
		config.setWindowedMode(1920,1080);
		new Lwjgl3Application(new RpgPrototypeGame(), config);
	}
}
