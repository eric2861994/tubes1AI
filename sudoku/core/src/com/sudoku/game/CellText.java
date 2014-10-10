package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class CellText {
	private BitmapFont cellFont;
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
	private FreeTypeFontParameter parameter;
		
	public CellText() {
		parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		cellFont = generator.generateFont(parameter);
		generator.dispose();
		cellFont.setColor(0.01f,0.3f,0.3f,1f);
	}
	
	public BitmapFont getFont() {
		return cellFont;
	}
	
	public void draw(SpriteBatch batch, int nilai, float x, float y) {
		String strNilai = (nilai==0)?"":(nilai+"");
		cellFont.draw(batch, strNilai, x, y);
	}
}
