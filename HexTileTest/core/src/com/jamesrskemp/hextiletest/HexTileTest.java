package com.jamesrskemp.hextiletest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class HexTileTest extends ApplicationAdapter {
	TiledMap map;
	OrthographicCamera camera;
	HexagonalTiledMapRenderer renderer;
	Texture texture;

	//SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 480, 480);
		camera.update();

		texture = new Texture(Gdx.files.internal("hexes.png"));
		TextureRegion[][] hexes = TextureRegion.split(texture, 112, 97);

		map = new TiledMap();
		MapLayers layers = map.getLayers();
		TiledMapTile[] tiles = new TiledMapTile[3];
		tiles[0] = new StaticTiledMapTile(new TextureRegion(hexes[0][0]));
		tiles[1] = new StaticTiledMapTile(new TextureRegion(hexes[0][1]));
		tiles[2] = new StaticTiledMapTile(new TextureRegion(hexes[1][0]));

		for (int l = 0; l < 1; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(45, 30, 112, 97);
			for (int y = 0; y < 30; y++) {
				for (int x = 0; x < 45; x++) {
					int id = (int)(Math.random() * 3);
					TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
					cell.setTile(tiles[id]);
					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}

		renderer = new HexagonalTiledMapRenderer(map);

		//batch = new SpriteBatch();
		//img = new Texture("hexes.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		texture.dispose();
		map.dispose();
	}
}
