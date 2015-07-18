package com.jamesrskemp.hextiletest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jamesrskemp.hextiletest.objects.HexBoard;
import com.jamesrskemp.hextiletest.utils.OrthographicCameraController;

public class HexTileTest extends ApplicationAdapter {
	OrthographicCamera camera;
	OrthographicCameraController cameraController;

	Stage stage;
	HexBoard hexBoard;

	//SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 480, 480);
		camera.update();

		cameraController = new OrthographicCameraController(camera);

		//stage = new Stage(new FitViewport(w, h));
		stage = new Stage();

		stage.getViewport().setCamera(camera);

		hexBoard = new HexBoard(camera);

		stage.addActor(hexBoard);

		//batch = new SpriteBatch();
		//img = new Texture("hexes.png");

		// Handle inputs.
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// TODO add any game-related actions you want to handle before the camera.
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(cameraController));
		inputMultiplexer.addProcessor(cameraController);

		Gdx.input.setInputProcessor(inputMultiplexer);

		stage.setDebugAll(true);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		stage.act();
		stage.draw();

		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		stage.dispose();
		hexBoard.dispose();
	}
}
