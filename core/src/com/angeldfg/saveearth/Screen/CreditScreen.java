package com.angeldfg.saveearth.Screen;

import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by angel on 17/01/2016.
 */
public class CreditScreen implements Screen {

    private Skin skin;
    private Stage stage;

    private SaveEarth principal;
    private FitViewport fitViewPort;

    public CreditScreen(SaveEarth principal){

        this.principal=principal;

        skin = new Skin(Gdx.files.internal("fonts/uiskin.json"), LoadAssets.atlas); // Cargamos os estilos

        stage = new Stage();
        fitViewPort = new FitViewport(World3D.WOLRD2D_WIDTH,World3D.WOLRD2D_HEIGHT);
        loadGraphicsElements();
        stage.setViewport(fitViewPort);

    }

    private void loadGraphicsElements(){

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().space(10);
        table.defaults().height(40);
        table.defaults().fillX();
        table.align(Align.top|Align.center);

        Label title = new Label("SAVE THE EARTH GAME",skin);
        title.setColor(Color.RED);
        title.setFontScale(1f);
        title.setAlignment(Align.center);

        table.add(title);

        table.row();

        title = new Label("Programming by Angel D. Fernández González. http://about.me/angeldfg",skin,"optional");
        title.setColor(Color.YELLOW);
        title.setAlignment(Align.center);

        table.add(title);
        stage.addActor(table);

        title = new Label("Graphics: \n  * Planet textures: http://www.solarsystemscope.com/nexus/textures/planet_textures/ \n  * SpaceShip: http://opengameart.org/content/racing-ship-2d3d (Jonathan Wayman) \n  * Ufo: http://www.3dcadbrowser.com/download.aspx?3dmodel=14165 ( 9KILLA2CALI5) \n  * Radar: http://opengameart.org/content/space-assets (priest865) \n  * Earth Texture: https://static.pexels.com/photos/2422/sky-earth-galaxy-universe.jpg \n  * FieldStar: https://upload.wikimedia.org/wikipedia/commons/8/80/Hyades.jpg \n  * Shoot: www.iconarchive.com/show/captiva-icons-by-bokehlicia/button-icon.html \n        (bokehlicia) \n  * Particle Effect: https://github.com/libgdx/libgdx/wiki/3D-Particle-Effects",skin,"optional");
        title.setColor(Color.CYAN);
        title.setAlignment(Align.left);
        title.setPosition(50, 190);

        stage.addActor(title);

        title = new Label("Music and Sounds: \n  * Shoot: http://www.freesound.org/people/211redman112/sounds/234083/ \n      (211redman112) \n  * Final Sound: http://www.freesound.org/people/cristianob_/sounds/183991/ \n      (cristianob_) \n  * Applause: http://www.freesound.org/people/Littleboot/sounds/198091/ (Littleboot) \n  * Bomb: http://www.freesound.org/people/dkmedic/sounds/104439/ (dkmedic) \n  * Music Space Trip: http://www.dl-sounds.com/royalty-free/space-trip/ (DL Sounds) \n ",skin,"optional");
        title.setColor(Color.CORAL);
        title.setFontScale(1f);
        title.setAlignment(Align.left);
        title.setPosition(50, 5);

        stage.addActor(title);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (Gdx.app.getInput().isTouched()){
            dispose();
            principal.setScreen(new PrincipalScreen(principal));
        }
    }
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        Gdx.input.setInputProcessor(null);
        stage.dispose();
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
