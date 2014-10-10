package com.sudoku.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.sudoku.game.SudokuGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Sudoku miniX";
		config.width = 600;
		config.height = 700;
		//TexturePacker.process("../core/assets/button-to-pack", "../core/assets/button", "button.pack");
		new LwjglApplication(new SudokuGame(), config);
	}
}
