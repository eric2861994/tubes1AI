package com.sudokuX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SudokuLoader {
	private static SudokuLoader instance;

	private SudokuLoader() {
	}

	public SudokuX getSudokuFromFile(String fileName) {
		SudokuX sudokuX = new SudokuX();

		FileHandle handle = Gdx.files.internal(fileName);
		String string = handle.readString();
		String[] numberStrings = string.split("\\s+");

		fillSudoku(numberStrings, sudokuX);

		return sudokuX;
	}

	private void fillSudoku(String[] numberStrings, SudokuX sudokuX) {
		for (int i = 0; i < numberStrings.length; i++) {
			int row = i / 6;
			int column = i % 6;
			if (numberStrings[i].equals("*")) {
				sudokuX.setCellToEmpty(row, column);
			} else {
				sudokuX.setCellValue(row, column,
						Integer.parseInt(numberStrings[i]));
			}
		}
	}

	public static SudokuLoader getInstance() {
		if (instance == null) {
			instance = new SudokuLoader();
		}
		return instance;
	}
}
