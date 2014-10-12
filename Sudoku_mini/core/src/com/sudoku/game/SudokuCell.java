package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SudokuCell {
	public static Texture squareImage = new Texture(
			Gdx.files.internal("square.png"));
	public static Texture blackBomb = new Texture(Gdx.files.internal("black.png"));
	public static Texture orangeBomb = new Texture(Gdx.files.internal("orange.png"));
	private Texture bombColor;
	private final Rectangle cell;
	private final int nilai;
	private final CellText cellText;

	public SudokuCell(int nilai, float x, float y) {
		this.cell = new Rectangle();
		this.cell.x = x;
		this.cell.y = y;
		this.cell.width = 100;
		this.cell.height = 100;
		this.nilai = nilai;
		System.out.println(this.nilai);
		this.cellText = new CellText();
	}

	public float getX() {
		return this.cell.x;
	}

	public float getY() {
		return this.cell.y;
	}

	public String getNilai() {
		return this.nilai + "";
	}

	public CellText getText() {
		return this.cellText;
	}

	public void drawText(SpriteBatch batch) {
		/*
		 * System.out.println("x y"); System.out.println(this.nilai);
		 * System.out.println(this.cell.x + 40); System.out.println(this.cell.y
		 * + 60);
		 */
		this.cellText.draw(batch, this.nilai, this.cell.x+27,
				this.cell.y+45);
	}
	
	public void setBombColor(String bombColor) {
		this.bombColor =  new Texture(bombColor);
	}
	
	public Texture getTexture() {
		return bombColor;
	}

}
