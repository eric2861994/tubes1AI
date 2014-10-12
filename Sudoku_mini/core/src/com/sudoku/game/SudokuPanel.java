package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.sudokuX.SudokuX;

public class SudokuPanel {

	private final Array<Array<SudokuCell>> box;

	public SudokuPanel(SudokuX sudokuX) {
		this.box = new Array<Array<SudokuCell>>();
		updateCell(sudokuX);
	}

	public void updateCell(SudokuX sudokuX) {
		this.box.clear();
		for (int i = 0; i < 6; ++i) {
			Array<SudokuCell> row = new Array<SudokuCell>();
			for (int j = 0; j < 6; ++j) {
				int nilai = (sudokuX.isCellEmpty(i, j) ? 0 : sudokuX
						.getCellValue(i, j));
				int paddingLeft = 20;
				int paddingTop = 10;
				float axis = j * 71 + paddingLeft;
				axis = (j>=3)?axis+30:axis;
				int viewportHeight = 720;
				float ordinat = viewportHeight - 108 - i * 108 - paddingTop ;
				ordinat = (i>=2)?ordinat-24:ordinat;
				ordinat = (i>=4)?ordinat-24:ordinat;
				SudokuCell cell = new SudokuCell(nilai, axis, ordinat);
				if(i==j || i+j==5) {
					cell.setBombColor(Gdx.files.internal("orange.png").path());
				}
				else
				{
					cell.setBombColor(Gdx.files.internal("black.png").path());
				}
				row.add(cell);
			}
			this.box.add(row);
		}
	}

	public Array<Array<SudokuCell>> getBox() {
		return this.box;
	}

	public void draw(SpriteBatch batch) {
		for (Array<SudokuCell> arr : this.box) {
			for (SudokuCell item : arr) {
					batch.draw(item.getTexture(), item.getX(), item.getY());
				item.drawText(batch);
			}
		}
	}
}
