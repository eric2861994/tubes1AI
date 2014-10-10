package com.sudoku.game;


import java.io.File;

import javax.swing.JFileChooser;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SudokuGame extends ApplicationAdapter {
	private SudokuButton browseBtn;
	private SudokuButton solveBtn;
	private SpriteBatch batch;
	private SudokuPanel sudokuPanel;
	private OrthographicCamera camera;
	private Stage stage;
	private Texture bgImg;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 700);
		bgImg = new Texture(Gdx.files.internal("bg.jpg"));
		
		// Create the sudoku panel & set tc2.txt as default testcase
		sudokuPanel = new SudokuPanel("tc2.txt");
		
		// Set browse button
		browseBtn = new SudokuButton("browse");
		float x = (camera.viewportWidth-2*browseBtn.getWidth())/2;
		float y = (camera.viewportHeight-600)/4;
		browseBtn.setPosition(x, y);

		// Set solve button
		solveBtn = new SudokuButton("solve");
		int offset = 20;
		x = camera.viewportWidth-x-browseBtn.getWidth()+offset;
		y = (camera.viewportHeight-600)/4;
		solveBtn.setPosition(x, y);
		
		// Add buttons to the stage to be rendered
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(browseBtn.getButton());
		stage.addActor(solveBtn.getButton());
	}

	@Override
	public void render () {
		
		// Clear screen
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render sudoku panel & button
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bgImg, 0, 0);
		sudokuPanel.draw(batch);
		batch.end();

		stage.draw();
		// Browse file
		if(browseBtn.getButton().isPressed())
		{
            String path = null;
			//Create a file chooser
			final JFileChooser fc = new JFileChooser();
		
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file;
	            file = fc.getSelectedFile();
	            try {
	                path = file.getAbsolutePath();
	            } catch (Exception ex) {
	                System.out.println("problem accessing file" + file.getAbsolutePath() + "\n" + ex.getMessage());
	            }
	        } 
			else 
			{
	            System.out.println("File access cancelled by user.");
	        }
			// Update panel w/ the selected test case
			sudokuPanel.updateCell(path);
		}
	}
		
}
