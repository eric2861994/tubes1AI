package com.sudoku.game;

import java.io.File;
import java.util.Random;

import javax.swing.JFileChooser;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sudokuX.SudokuLoader;
import com.sudokuX.SudokuX;

public class SudokuGame extends ApplicationAdapter {
	private SudokuButton browseBtn;
	private SudokuButton randomBtn;
	private SudokuButton solveBtn;
	private SpriteBatch batch;
	private SudokuPanel sudokuPanel;
	private OrthographicCamera camera;
	private Stage stage;
	private Texture bgImg;
	private Random generator;;

	@Override
	public void create() {

		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 1280, 720);
		this.bgImg = new Texture(Gdx.files.internal("bg.png"));

		this.sudokuPanel = new SudokuPanel(new SudokuX());
		
		// Set browse button
		this.browseBtn = new SudokuButton("browse");
		int padding = 20;
		float x = this.camera.viewportWidth-browseBtn.getWidth() - padding;
		float y = this.camera.viewportHeight - browseBtn.getHeight()-padding;
		this.browseBtn.setPosition(x, y);
		
		browseBtn.getButton().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x,float y){
				String path = null;
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();

				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file;
					file = fc.getSelectedFile();
					try {
						path = file.getAbsolutePath();
					} catch (Exception ex) {
						System.out.println("problem accessing file"
								+ file.getAbsolutePath() + "\n" + ex.getMessage());
					}
				} else {
					System.out.println("File access cancelled by user.");
				}
				SudokuX sudokuX = SudokuLoader.getInstance()
						.getSudokuFromFile(path);
				// System.out.println(sudokuX);
				SudokuGame.this.sudokuPanel.updateCell(sudokuX);
			}
		});

		// Set random button
		this.randomBtn = new SudokuButton("random");
		x = this.camera.viewportWidth-browseBtn.getWidth()-padding;
		y = this.camera.viewportHeight - browseBtn.getHeight() - randomBtn.getHeight()-2*padding;
		this.randomBtn.setPosition(x, y);

		generator = new Random();
		this.randomBtn.getButton().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x,float y) {
				int random = generator.nextInt(3)+1;
				String path = "tc"+random+".txt";
				SudokuX sudokuX = SudokuLoader.getInstance()
						.getSudokuFromFile(Gdx.files.internal(path).path());
				SudokuGame.this.sudokuPanel.updateCell(sudokuX);
			}
		});
		
		// Set solve button
		this.solveBtn = new SudokuButton("solve");
		x = this.camera.viewportWidth-solveBtn.getWidth()-padding;
		y = this.camera.viewportHeight - browseBtn.getHeight() - randomBtn.getHeight()-solveBtn.getHeight()-3*padding;
		this.solveBtn.setPosition(x, y);

		// Add buttons to the stage to be rendered
		this.stage = new Stage();
		Gdx.input.setInputProcessor(this.stage);
		this.stage.addActor(this.browseBtn.getButton());
		this.stage.addActor(this.randomBtn.getButton());
		this.stage.addActor(this.solveBtn.getButton());
	}

	@Override
	public void render() {

		// Clear screen
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render sudoku panel & button
		this.camera.update();

		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		this.batch.draw(this.bgImg, 0, 0);
		this.sudokuPanel.draw(this.batch);
		this.batch.end();

		this.stage.draw();

	}

}
