package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SudokuCell {
	public static Texture squareImage = new Texture(Gdx.files.internal("square.png"));
	private Rectangle cell;
	private int nilai;
	private CellText cellText;
	
	public SudokuCell(int nilai,float x, float y) 
	{
		cell = new Rectangle();
		cell.x = x;
		cell.y = y;
		cell.width=100;
		cell.height=100;
		this.nilai = nilai;
		cellText = new CellText();
	}
	
	public float getX() {
		return cell.x;
	}
	
	public float getY() {
		return cell.y;
	}
	
	public String getNilai() {
		return nilai+"";
	}
	
	public CellText getText() {
		return cellText;
	}
	
	public void drawText(SpriteBatch batch) {
		cellText.draw(batch, nilai, cell.x+40, cell.y+60);
	}
	
}
