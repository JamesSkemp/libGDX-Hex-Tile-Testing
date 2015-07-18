package com.jamesrskemp.hextiletest.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by James on 7/11/2015.
 */
public class HexBoard extends Actor implements Disposable {
	private final static String TAG = HexBoard.class.getName();

	OrthographicCamera camera;

	TiledMap map;
	HexagonalTiledMapRenderer renderer;
	Texture texture;

	public HexBoard(OrthographicCamera camera) {
		this.camera = camera;

		texture = new Texture(Gdx.files.internal("hexes.png"));
		TextureRegion[][] hexes = TextureRegion.split(texture, 112, 97);

		map = new TiledMap();
		MapLayers layers = map.getLayers();
		TiledMapTile[] tiles = new TiledMapTile[4];
		tiles[0] = new StaticTiledMapTile(new TextureRegion(hexes[0][0]));
		tiles[1] = new StaticTiledMapTile(new TextureRegion(hexes[0][1]));
		tiles[2] = new StaticTiledMapTile(new TextureRegion(hexes[1][0]));
		// This is actually an empty space, but we'll use it as a clear.
		tiles[3] = new StaticTiledMapTile(new TextureRegion(hexes[1][1]));

		int tilesWide = 15;
		int tilesHigh = 10;
		int tileWidth = 112;
		int tileHeight = 97;

		for (int l = 0; l < 1; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(tilesWide, tilesHigh, tileWidth, tileHeight);
			for (int y = 0; y < tilesHigh; y++) {
				for (int x = 0; x < tilesWide; x++) {
					int id = (int) (Math.random() * 3);
					TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
					cell.setTile(tiles[id]);
					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}

		//
		setBounds(
				getX(), getY()
				, (tilesWide * tileWidth), (tilesHigh * tileHeight)
		);
		setTouchable(Touchable.enabled);

		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// For now just log that we were touched.
				Gdx.app.log(TAG, "touchDown " + event + " <" + x + "," + y + " ptr " + pointer + " by " + button);
				return false;
				//return super.touchDown(event, x, y, pointer, button);
			}
		});

		renderer = new HexagonalTiledMapRenderer(map);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		texture.dispose();
		map.dispose();
	}
}
