package com.angeldfg.saveearth.Screen;

import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by angel on 17/01/2016.
 */
public class ControlsScreen implements Screen {

    private SaveEarth principal;

    private OrthographicCamera camera2D;
    private SpriteBatch spriteBatch;

    private Texture infControlMovil;
    private Texture infRadar;

    private boolean closeScreen=false;

    public ControlsScreen(SaveEarth principal){

        this.principal=principal;

        camera2D = new OrthographicCamera();
        spriteBatch = new SpriteBatch();

        infControlMovil = new Texture("manual/informationcontrol.jpg");
        infRadar = new Texture("manual/informationradar.jpg");


    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        spriteBatch.begin();

        if (!closeScreen)
            spriteBatch.draw(infControlMovil,0,0,World3D.WOLRD2D_WIDTH,World3D.WOLRD2D_HEIGHT);
        else
            spriteBatch.draw(infRadar,0,0,World3D.WOLRD2D_WIDTH,World3D.WOLRD2D_HEIGHT);

        if (Gdx.app.getInput().justTouched()){
            if (closeScreen){
                dispose();
                principal.setScreen(new PrincipalScreen(principal));
            }
            else
                closeScreen=true;
        }

        spriteBatch.end();

    }
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

        camera2D.setToOrtho(false,World3D.WOLRD2D_WIDTH,World3D.WOLRD2D_HEIGHT);
        camera2D.update();

        spriteBatch.setProjectionMatrix(camera2D.combined);


    }

    @Override
    public void dispose() {

        Gdx.input.setInputProcessor(null);

        infControlMovil.dispose();
        infRadar.dispose();

    }



    @Override
    public void show() {

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
}
