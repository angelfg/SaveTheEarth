package com.angeldfg.saveearth.Screen;


import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.angeldfg.saveearth.View.GameRenderer;
import com.badlogic.gdx.Screen;

/**
 * Created by angel on 03/01/2016.
 */
public class GameScreen implements Screen {

    private World3D world3d;

    private GameRenderer gameRenderer;
    private SaveEarth principal;

    public GameScreen(SaveEarth principal){
        LoadAssets.loadGraphics();

        world3d = new World3D();
        this.principal=principal;
        gameRenderer = new GameRenderer(world3d);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        LoadAssets.disposeGraphics();

    }
}
