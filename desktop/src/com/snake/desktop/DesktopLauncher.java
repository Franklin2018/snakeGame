package com.snake.desktop;

//import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.snake.MySnakeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 700;
		config.height = 700;
		config.resizable = false;
		config.title = "Snake - Programaci�n Gr�fica";
		new LwjglApplication(new MySnakeGame(), config);
	}
}
