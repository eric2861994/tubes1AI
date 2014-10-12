package com.sudokuX;

import java.util.Iterator;

import jess.Fact;
import jess.JessException;
import jess.Rete;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SudokuSolver {
	private final Rete env = new Rete();
	private String sudokuCLP, solveCLP, outputCLP;

	public SudokuSolver() {
		loadRuleFromExternalFile();
	}

	private void loadRuleFromExternalFile() {
		FileHandle sudokuCLPHandle = Gdx.files.internal("data/sudoku.clp");
		FileHandle solveCLPHandle = Gdx.files.internal("data/solve.clp");
		FileHandle outputCLPHandle = Gdx.files.internal("data/output.clp");

		this.sudokuCLP = sudokuCLPHandle.readString();
		this.solveCLP = solveCLPHandle.readString();
		this.outputCLP = outputCLPHandle.readString();
	}

	public void solveSudoku(SudokuX sudokuX) {
		try {
			loadCLIPSRuleToEngine(sudokuX);
			runEngine();
			loadEngineResultToSudoku(sudokuX);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	private void loadCLIPSRuleToEngine(SudokuX sudokuX) throws JessException {
		this.env.eval("(reset)");
		this.env.eval(this.sudokuCLP);
		this.env.eval(this.solveCLP);
		this.env.eval(this.outputCLP);
		this.env.eval(constructRuleInput(sudokuX));
	}

	private void runEngine() throws JessException {
		this.env.eval("(reset)");
		this.env.run();
	}

	private void loadEngineResultToSudoku(SudokuX sudokuX) throws JessException {
		Iterator it = this.env.listFacts();
		while (it.hasNext()) {
			Object obj = it.next();
			Fact currentFact = ((Fact) obj);
			if (currentFact.getName().equals("MAIN::possible")) {
				int row = Integer.parseInt(currentFact.getSlotValue("row")
						.toString()) - 1;
				int column = Integer.parseInt(currentFact
						.getSlotValue("column").toString()) - 1;
				int value = Integer.parseInt(currentFact.getSlotValue("value")
						.toString());

				sudokuX.setCellValue(row, column, value);

			}
		}
	}

	private String constructRuleInput(SudokuX sudokuX) {
		int row = 1;
		int column = 1;
		int group = 0;
		int diag = 0;
		int[] groupcounter = { 0, 1, 7, 13, 19, 25, 31 };
		int id = 1;
		String value = "";
		String parseResult = "(defrule grid-values \n\n?f <- (phase grid-values) \n=>(retract ?f) \n(assert (phase expand-any)) \n";
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (sudokuX.getCellValue(i, j) == 0) {
					value = "any";
				} else {
					value = Integer.toString(sudokuX.getCellValue(i, j));
				}
				if (row == column) {
					diag = 1;
				} else if (row + column == 7) {
					diag = 2;
				} else {
					diag = 0;
				}
				if ((row >= 1 && row <= 2 && column >= 1 && column <= 3)) {
					group = 1;
				} else if ((row >= 1 && row <= 2 && column >= 4 && column <= 6)) {
					group = 2;
				} else if ((row >= 3 && row <= 4 && column >= 1 && column <= 3)) {
					group = 3;
				} else if ((row >= 3 && row <= 4 && column >= 4 && column <= 6)) {
					group = 4;
				} else if ((row >= 5 && row <= 6 && column >= 1 && column <= 3)) {
					group = 5;
				} else if ((row >= 5 && row <= 6 && column >= 4 && column <= 6)) {
					group = 6;
				} else {
					group = 0;
				}
				id = groupcounter[group];
				parseResult = parseResult + "(assert (possible (row " + row
						+ ") (column " + column + ") (value " + value
						+ ") (group " + group + ") (diag " + diag + ") (id "
						+ id + "))) \n";
				if (column == 6) {
					row++;
					column = 0;
				}
				column++;
				groupcounter[group]++;
			}
		}
		parseResult += ")";
		System.out.println(parseResult);
		return parseResult;
	}
}
