package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;

public class SudokuButton {
	
	private static TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
	private static Skin skin = new Skin(buttonAtlas);
	private  ImageButtonStyle buttonStyle;
	private ImageButton button;
	
	public SudokuButton(String upFilename) {
		buttonStyle = new ImageButtonStyle();
		buttonStyle.up = skin.getDrawable(upFilename);
		button = new ImageButton(buttonStyle);
	}
	
	public void setFilename(String downFilename, String overFilename) {
		buttonStyle.down = skin.getDrawable(downFilename);
		buttonStyle.over = skin.getDrawable(overFilename);
	}
	
	public void setPosition(float x, float y) {
		button.setX(x);
		button.setY(y);
		
	}
	
	public float getWidth() {
		return button.getWidth();
	}
	
	public ImageButton getButton() {
		return button;
	}
	
}
