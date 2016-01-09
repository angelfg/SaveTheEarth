package com.angeldfg.saveearth.Screen;


import com.angeldfg.saveearth.Assets.Controls;
import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Controller.GameController;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.angeldfg.saveearth.View.GameRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by angel on 03/01/2016.
 */
public class GameScreen implements Screen, InputProcessor {

    private World3D world3d;

    private GameRenderer gameRenderer;
    private SaveEarth principal;
    private GameController gameController;

    public GameScreen(SaveEarth principal){
        LoadAssets.loadGraphics();

        world3d = new World3D();
        this.principal=principal;
        gameRenderer = new GameRenderer(world3d);
        gameController = new GameController(world3d);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        gameController.update(delta);
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width,height);
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        LoadAssets.disposeGraphics();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        Vector3 coord = new Vector3(screenX,screenY,0);
        GameRenderer.getCamera2D().unproject(coord);
        Circle press = new Circle(coord.x,coord.y, Controls.SIZE/4);

        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.ACCELERATE))){
            gameController.pressKey(GameController.Keys.ACCELERATE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.BRAKE))){
            gameController.pressKey(GameController.Keys.BRAKE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.STOP))){
            gameController.pressKey(GameController.Keys.STOP);
        }

        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.LEFT))){
            gameController.pressKey(GameController.Keys.TURN_LEFT);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.RIGHT))){
            gameController.pressKey(GameController.Keys.TURN_RIGHT);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.UP))){
            gameController.pressKey(GameController.Keys.UP);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.DOWN))){
            gameController.pressKey(GameController.Keys.DOWN);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub

        Vector3 coord = new Vector3(screenX,screenY,0);
        GameRenderer.getCamera2D().unproject(coord);
        Circle press = new Circle(coord.x,coord.y, Controls.SIZE/4);

        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.ACCELERATE))){
            gameController.releaseKey(GameController.Keys.ACCELERATE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.BRAKE))){
            gameController.releaseKey(GameController.Keys.BRAKE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.STOP))){
            gameController.releaseKey(GameController.Keys.STOP);
        }

        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.LEFT))){
            gameController.releaseKey(GameController.Keys.TURN_LEFT);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.RIGHT))){
            gameController.releaseKey(GameController.Keys.TURN_RIGHT);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.UP))){
            gameController.releaseKey(GameController.Keys.UP);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.DOWN))){
            gameController.releaseKey(GameController.Keys.DOWN);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
