package com.sudokuX;

public class SudokuX {
	private final int[][] cell = new int[6][6];

	public SudokuX() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				this.cell[i][j] = 1;
			}
		}
	}

	public void setCellValue(int row, int column, int value) {
		this.cell[row][column] = value;
	}

	public int getCellValue(int row, int column) {
		return this.cell[row][column];
	}

	public boolean isCellEmpty(int row, int column) {
		if (this.cell[row][column] == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setCellToEmpty(int row, int column) {
		this.cell[row][column] = 0;
	}

	@Override
	public String toString() {
		StringBuilder sudokuInfo = new StringBuilder("");
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				sudokuInfo.append(Integer.toString(this.cell[i][j]));
				if (j == 6 - 1) {
					sudokuInfo.append(" ");
				}
			}
			sudokuInfo.append("\n");
		}
		return sudokuInfo.toString();
	}
}
