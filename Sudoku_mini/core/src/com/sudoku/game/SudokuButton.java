package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SudokuButton {

	private static TextureAtlas buttonAtlas = new TextureAtlas(
			Gdx.files.internal("btn.pack"));
	private static Skin skin = new Skin(buttonAtlas);
	private final ImageButtonStyle buttonStyle;
	private final ImageButton button;

	public SudokuButton(String upFilename) {
		this.buttonStyle = new ImageButtonStyle();
		this.buttonStyle.up = skin.getDrawable(upFilename);
		this.button = new ImageButton(this.buttonStyle);
	}

	public void setFilename(String downFilename, String overFilename) {
		this.buttonStyle.down = skin.getDrawable(downFilename);
		this.buttonStyle.over = skin.getDrawable(overFilename);
	}

	public void setPosition(float x, float y) {
		this.button.setX(x);
		this.button.setY(y);

	}

	public float getWidth() {
		return this.button.getWidth();
	}

	public float getHeight() {
		return this.button.getHeight();
	}
	
	public ImageButton getButton() {
		return this.button;
	}

}
