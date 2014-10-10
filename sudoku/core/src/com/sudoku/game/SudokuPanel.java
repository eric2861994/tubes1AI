package com.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SudokuPanel {

	private Array<Array<SudokuCell>> box;
	
	public SudokuPanel(String filepath) {
		box = new Array<Array <SudokuCell>>();
		updateCell(filepath);
	}
	
	public void updateCell(String filepath) {
		FileHandle testcaseFile = Gdx.files.internal(filepath);
		String buff = testcaseFile.readString();
		
		int k=0;
		for(int i=0;i<6;++i)
		{
			Array<SudokuCell> row;
			row = new Array<SudokuCell>();
			for(int j=0;j<6;++j)
			{
				char ch = buff.charAt(k);
				int nilai=(ch=='*'?0:Character.getNumericValue(ch));
				float axis=j*100;
				int viewportHeight = 700;
				float ordinat=viewportHeight-100-i*100;
				SudokuCell cell = new SudokuCell(nilai, axis, ordinat);
				row.add(cell);
				k+=2;
			}
			box.add(row);
		}
	}
	
	public Array<Array <SudokuCell>> getSudokuCell() {
		return box;
	}
	
	public void draw(SpriteBatch batch) {
		for(Array<SudokuCell> arr:box)
		{
			for(SudokuCell item:arr)
			{
				batch.draw(SudokuCell.squareImage, item.getX(), item.getY());
				item.drawText(batch);
			}
		}
	}
}
