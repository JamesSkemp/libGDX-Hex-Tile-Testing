package com.jamesrskemp.hextiletest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Author: James Skemp ( jamesrskemp.com / strivinglife.com / github.com/JamesSkemp ), based upon libGDX 1.6.2.
 *
 * This orthographic camera controller will handle both inputs and gestures, and can log when it's triggered.
 *
 * Usage example:

 camera = new OrthographicCamera();
 camera.setToOrtho(false, Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
 camera.update();

 cameraController = new OrthographicCameraController(camera);

 // Handle inputs.
 InputMultiplexer inputMultiplexer = new InputMultiplexer();
 // TODO add any game-related actions you want to handle before the camera.
 inputMultiplexer.addProcessor(new GestureDetector(cameraController));
 inputMultiplexer.addProcessor(cameraController);

 Gdx.input.setInputProcessor(inputMultiplexer);
 */
public class OrthographicCameraController implements InputProcessor, GestureDetector.GestureListener {
	private static final String TAG = OrthographicCameraController.class.getName();

	final OrthographicCamera camera;

	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	final Vector3 selectedPosition = new Vector3();

	float initialZoomScale = 1;

	/**
	 * Enables logging. Setting this to false will override all options below.
	 */
	boolean logActivity = true;
	/**
	 * Log verbose activities, such as moving the mouse. These tend to generate a lot of log entries.
	 */
	boolean logVerboseActivity = false;
	/**
	 * Log all activity, overriding all options below.
	 */
	boolean logAllActivity = true;
	/**
	 * Log activity from InputProcessor
	 */
	boolean logInputProcessorActivity = false;
	/**
	 * Log activity from GestureListener.
	 */
	boolean logGestureListenerActivity = true;

	public OrthographicCameraController(OrthographicCamera camera) {
		this.camera = camera;

		initialZoomScale = camera.zoom;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "fling triggered. velocityX " + velocityX + " velocityY " + velocityY + " button " + button);
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "keyDown triggered. keycode " + keycode + " (" + Input.Keys.toString(keycode) + ")");
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "keyTyped triggered. keycode " + character);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "keyUp triggered. keycode " + keycode + " (" + Input.Keys.toString(keycode) + ")");
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "longPress triggered. x " + x + " y " + y);
		}
		camera.unproject(selectedPosition.set(x, y, 0));
		Gdx.app.log(TAG, "selectedPosition: " + selectedPosition);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (logActivity && (logAllActivity || logInputProcessorActivity) && logVerboseActivity) {
			Gdx.app.log(TAG, "mouseMoved triggered. screenX " + screenX + " screenY " + screenY);
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "pan triggered. x " + x + " y " + y + " deltaX " + deltaX + " deltaY " + deltaY);
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "panStop triggered. x " + x + " y " + y + " pointer " + pointer + " button " + button);
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "pinch triggered. initialPointer1 " + initialPointer1 + " initialPointer2 " + initialPointer2 + " pointer1 " + pointer1 + " pointer2 " + pointer2);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "scrolled triggered. amount " + amount);
		}
		camera.zoom = MathUtils.clamp(initialZoomScale + (amount * 0.1f), Constants.MAXIMUM_ZOOM_IN, Constants.MAXIMUM_ZOOM_OUT);
		initialZoomScale = camera.zoom;
		Gdx.app.log(TAG, "camera zoom: " + camera.zoom);
		return true;
	}
	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "tap triggered. x " + x + " y " + y + " count " + count + " button " + button);
		}
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "touchDown (Gesture) triggered. x " + x + " y " + y + " pointer " + pointer + " button " + button);
		}
		camera.unproject(selectedPosition.set(x, y, 0));
		Gdx.app.log(TAG, "selectedPosition: " + selectedPosition);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "touchDown (Input) triggered. screenX " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "touchDragged triggered. screenX " + screenX + " screenY " + screenY + " pointer " + pointer);
		}
		camera.unproject(curr.set(screenX, screenY, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			camera.position.add(delta.x, delta.y, 0);
		}
		last.set(screenX, screenY, 0);
		Gdx.app.log(TAG, "current position: " + curr);
		Gdx.app.log(TAG, "last position: " + last);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (logActivity && (logAllActivity || logInputProcessorActivity)) {
			Gdx.app.log(TAG, "touchUp triggered. screenX " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);
		}
		last.set(-1, -1, -1);
		initialZoomScale = camera.zoom;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (logActivity && (logAllActivity || logGestureListenerActivity)) {
			Gdx.app.log(TAG, "zoom triggered. initialDistance " + initialDistance + " distance " + distance);
		}
		camera.zoom = MathUtils.clamp(initialZoomScale * initialDistance / distance, Constants.MAXIMUM_ZOOM_IN, Constants.MAXIMUM_ZOOM_OUT);
		Gdx.app.log(TAG, "camera zoom: " + camera.zoom);
		return true;
	}
}