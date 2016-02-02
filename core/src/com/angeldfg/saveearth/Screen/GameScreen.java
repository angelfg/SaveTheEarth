package com.angeldfg.saveearth.Screen;


import com.angeldfg.saveearth.Assets.Controls;
import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Controller.GameController;
import com.angeldfg.saveearth.Model.Radar;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.angeldfg.saveearth.View.GameRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private LoadAssets loadAssets;

    private static boolean endGame;
    public static boolean winGame;

    private boolean playFinalBomb;

    public GameScreen(SaveEarth principal){
        loadAssets = new LoadAssets();
        loadAssets.loadGraphics();

        world3d = new World3D();
        this.principal=principal;
        gameRenderer = new GameRenderer(world3d,loadAssets.getAssets());
        gameController = new GameController(world3d);

        endGame=false;
        winGame=false;

        playFinalBomb = false;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);
        if (!Sounds.music.isPlaying())
            Sounds.music.play();



    }

    @Override
    public void render(float delta) {

        if (endGame) {
            if (world3d.getChronoEndGame() <= 0) {
                principal.setScreen(new PrincipalScreen(principal));
                dispose();
            }
            if ((!playFinalBomb) && (world3d.getChronoEndGame() <= 2) && (!winGame)) {
                Sounds.explosion.play(1);
                playFinalBomb = true;
            }
        }

        if (world3d.getChronoEndGame() > 0) {
            gameController.update(delta);
            gameRenderer.render(delta);
        }
    }

    public static void setEndGame(boolean endGame) {
        if (Sounds.music.isPlaying()){
            Sounds.music.stop();
        }
        if (winGame)
        {
            World3D.setChronoEndGame(11);
            Sounds.applause.play();
        }
        else  {
            Sounds.finalsound.play();
        }
        GameScreen.endGame = endGame;
    }

    public static boolean isEndGame() {
        return endGame;
    }




    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width,height);
    }

    @Override
    public void pause() {

        Gdx.input.setInputProcessor(null);
        if (Sounds.music.isPlaying()){
            Sounds.music.pause();
        }

    }

    @Override
    public void resume() {

        Gdx.input.setInputProcessor(this);
        Sounds.music.play();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        loadAssets.disposeGraphics();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode== Input.Keys.RIGHT){
            gameController.pressKey(GameController.Keys.TURN_RIGHT);
        }
        if (keycode== Input.Keys.LEFT){
            gameController.pressKey(GameController.Keys.TURN_LEFT);
        }
        if (keycode== Input.Keys.UP){
            gameController.pressKey(GameController.Keys.ACCELERATE);
        }
        if (keycode== Input.Keys.DOWN){
            gameController.pressKey(GameController.Keys.BRAKE);
        }
        if (keycode== Input.Keys.SPACE){
            gameController.pressKey(GameController.Keys.FIRE);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode== Input.Keys.RIGHT){
            gameController.releaseKey(GameController.Keys.TURN_RIGHT);
        }
        if (keycode== Input.Keys.LEFT){
            gameController.releaseKey(GameController.Keys.TURN_LEFT);
        }
        if (keycode== Input.Keys.UP){
            gameController.releaseKey(GameController.Keys.ACCELERATE);
        }
        if (keycode== Input.Keys.DOWN){
            gameController.releaseKey(GameController.Keys.BRAKE);
        }
        if (keycode== Input.Keys.SPACE){
            gameController.releaseKey(GameController.Keys.FIRE);
        }


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

        if (Intersector.overlaps(press, Radar.size_radar)){
            if (!Radar.isMinimize())
               Radar.setMinimize(true);
        }
        if (Intersector.overlaps(press, Radar.size_radar_minimized)){
            if (Radar.isMinimize())
                Radar.setMinimize(false);
        }


        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.ACCELERATE))){
            gameController.pressKey(GameController.Keys.ACCELERATE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.BRAKE))){
            gameController.pressKey(GameController.Keys.BRAKE);
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.FIRE))){
            gameController.pressKey(GameController.Keys.FIRE);
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
        boolean enter=false;

        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.ACCELERATE))){
            gameController.releaseKey(GameController.Keys.ACCELERATE);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.BRAKE))){
            gameController.releaseKey(GameController.Keys.BRAKE);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.FIRE))){
            gameController.releaseKey(GameController.Keys.FIRE);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.LEFT))){
            gameController.releaseKey(GameController.Keys.TURN_LEFT);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.RIGHT))){
            gameController.releaseKey(GameController.Keys.TURN_RIGHT);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.UP))){
            gameController.releaseKey(GameController.Keys.UP);
            enter=true;
        }
        if (Intersector.overlaps(press,Controls.size_controls.get(Controls.CONTROLS.DOWN))){
            gameController.releaseKey(GameController.Keys.DOWN);
            enter=true;
        }

        if (!enter){
            gameController.releaseKey(GameController.Keys.ACCELERATE);
            gameController.releaseKey(GameController.Keys.BRAKE);
            gameController.releaseKey(GameController.Keys.FIRE);
            gameController.releaseKey(GameController.Keys.TURN_LEFT);
            gameController.releaseKey(GameController.Keys.TURN_RIGHT);
            gameController.releaseKey(GameController.Keys.UP);
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
