package com.psywerx.min;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "min-libgdx";
		cfg.useGL20 = true;
		cfg.width = 1200;
		cfg.height = 780;
		
		new LwjglApplication(new MinGame(), cfg);
	}
}
