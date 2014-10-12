package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class CellText {
	private final BitmapFont cellFont;
	private final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
			Gdx.files.internal("Roboto-Regular.ttf"));
	private final FreeTypeFontParameter parameter;

	public CellText() {
		this.parameter = new FreeTypeFontParameter();
		this.parameter.size = 30;
		this.cellFont = this.generator.generateFont(this.parameter);
		this.generator.dispose();
		this.cellFont.setColor(1f, 1f, 1f, 1f);
	}

	public BitmapFont getFont() {
		return this.cellFont;
	}

	public void draw(SpriteBatch batch, int nilai, float x, float y) {
		String strNilai = (nilai == 0) ? "" : (nilai + "");
		this.cellFont.draw(batch, strNilai, x, y);
	}
}
