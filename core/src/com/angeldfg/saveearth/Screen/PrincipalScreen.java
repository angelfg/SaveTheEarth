package com.angeldfg.saveearth.Screen;

import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Assets.Sounds;
import com.angeldfg.saveearth.Model.World3D;
import com.angeldfg.saveearth.SaveEarth;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
public class PrincipalScreen implements Screen {

    private Skin skin;
    private Stage stage;

    private SaveEarth principal;
    private FitViewport fitViewPort;

    public PrincipalScreen(SaveEarth principal){

        this.principal=principal;

        skin = new Skin(Gdx.files.internal("fonts/uiskin.json"), LoadAssets.atlas); // Cargamos os estilos

        Gdx.input.setCatchBackKey(true);
        stage = new Stage(){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    showExitDialog();
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);

        fitViewPort = new FitViewport(World3D.WOLRD2D_WIDTH,World3D.WOLRD2D_HEIGHT);
        stage.setViewport(fitViewPort);
        loadGraphicsElements();

    }

    private void loadGraphicsElements(){

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().space(10);
        table.defaults().height(50);
        table.defaults().width(500);
        table.align(Align.top|Align.center);

        Label title = new Label("SAVE THE EARTH GAME",skin);
        title.setColor(Color.RED);
        title.setFontScale(1f);
//        title.setBounds(0, 100, Gdx.graphics.getWidth(), 10);
        title.setAlignment(Align.center|Align.top);
        table.add(title);

        table.row();
        table.row();

        title = new Label("Libgdx Jam 2015",skin);
        title.setColor(Color.RED);
        title.setFontScale(0.5f);
//        title.setBounds(0, 100, Gdx.graphics.getWidth(), 10);
        title.setAlignment(Align.center);
  //      title.setPosition(20, 40);

        table.add(title);
        table.row();
        table.row();

        TextButton boton = new TextButton("START GAME", skin);
        boton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                principal.setScreen(new GameScreen(principal));
            }
        });
        table.add(boton);

        table.row();
        table.row();

        boton = new TextButton("CONTROLS", skin);
        boton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                principal.setScreen(new ControlsScreen(principal));
            }
        });
        table.add(boton);

        table.row();
        table.row();

        boton = new TextButton("CREDITS", skin);
        boton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                dispose();
                principal.setScreen(new CreditScreen(principal));
            }
        });
        table.add(boton);

        table.row();
        table.row();

        boton = new TextButton("EXIT GAME", skin);
        boton.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                showExitDialog();
            }
        });
        table.add(boton);
        table.row();
        table.row();


        title = new Label("You must stop the aliens arrive on Earth ...",skin);
        title.setColor(Color.GREEN);
        title.setFontScale(0.5f);
        title.setAlignment(Align.left);
        table.add(title);

        table.row();

        title = new Label("We need you",skin);
        title.setColor(Color.GREEN);
        title.setFontScale(1f);
        title.setAlignment(Align.left);
        table.add(title);

        stage.addActor(table);

    }

    private void showExitDialog(){
        Dialog dialog = new Dialog("Exit Game", skin, "dialog") { // Os acentos non aparecen xa que non están no png das letras
            protected void result (Object object) {
                if ((Boolean) object)
                    if (((Boolean) object).booleanValue()) {
                        dispose();
                        Gdx.app.exit();
                    }
            }
        }.text("Are you sure to exit ?")
                .button("Yes", true)
                .button("No",false)
                .key(Input.Keys.ENTER, true)
                .key(Input.Keys.ESCAPE, false);
        dialog.show(stage);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        Sounds.stopMusic();

        Gdx.input.setInputProcessor(null);
        stage.dispose();

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Sounds.playMusic();


    }


    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);
        Sounds.stopMusic();
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
        Sounds.playMusic();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        Sounds.stopMusic();

    }
}
