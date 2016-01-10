package com.angeldfg.saveearth.View;


import com.angeldfg.saveearth.Assets.Controls;
import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Model.Planet;
import com.angeldfg.saveearth.Model.SpaceShip;
import com.angeldfg.saveearth.Model.Ufo;
import com.angeldfg.saveearth.Model.World3D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by angel on 03/01/2016.
 */
public class GameRenderer {

    private PerspectiveCamera camera3D;

    private static OrthographicCamera camera2D;

    private Array<ModelInstance> instances;
    private ModelInstance instanceSun;  // Envrironment not affect => render
    private ModelInstance instanceSpaceShip;
    private Array<ModelInstance> planetsInstances;
    private Array<ModelInstance> instancesUfos;

    private ModelBatch modelBatch;
    private Environment environment;

    private CameraInputController camController;

    private World3D world3d;
    private SpaceShip spaceShip;
    private Vector3 temp;

    private PointLight pointLight;



    private final float CAMARE3D_NEAR = 0.1f;
    private final float CAMARE3D_FAR = 500000;
    private final float CAMARE3D_ANGLE = 67f;


    // 2D
    private SpriteBatch sprite;

    private float CAMARE2D_ANCHO;
    private float CAMARE2D_ALTO;


    private ShapeRenderer shapeRenderer;


    PointLight pointLightSun;

    private AnimationController controller;

    public GameRenderer(World3D world3d){

        camera3D = new PerspectiveCamera();
        camera2D = new OrthographicCamera();

        sprite = new SpriteBatch();

        instances = new Array<ModelInstance>();
        planetsInstances = new Array<ModelInstance>();
        instancesUfos =  new Array<ModelInstance>();

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new PointLight().set(1050, 0f, 0f, 2000f, 0f, 0f, 10f));

        this.world3d = world3d;
        spaceShip = world3d.getSpaceShip();

      //  pointLight = new PointLight().set(0f, 1f, 0f, 2000000f, 0f, 0f, 10f);
      //  pointLight.position.set(spaceShip.getPosition());
      //  environment.add(pointLight);


        temp = new Vector3();
        shapeRenderer = new ShapeRenderer();


        //Load instancesUfos
        Model modelUfo = LoadAssets.assets.get("ufo/ufo.g3db",Model.class);

        for (Ufo ufo : world3d.getUfos()){

            ModelInstance modelInstance = new ModelInstance(modelUfo);
            instancesUfos.add(modelInstance);
            instances.add(modelInstance);
        }

        // Load planets
        Model modelPlanet = LoadAssets.assets.get("planets/baseplanet.g3db",Model.class);
        for (Planet planet : world3d.getPlanets()){

            ModelInstance modelInstance = new ModelInstance(modelPlanet);
            //modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material.set(TextureAttribute.createDiffuse(LoadAssets.texture_planets.get(planet.getName_planet())));
            modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material= new Material(TextureAttribute.createDiffuse(LoadAssets.texture_planets.get(planet.getName_planet())));

            if (planet.getName_planet()== Planet.PLANET_NAMES.SOL){
                instanceSun = modelInstance;
            }
            else {
                instances.add(modelInstance);
            }
            planetsInstances.add(modelInstance);

        }
        // Load SpaceShip and animation
        Model modelSpaceShip = LoadAssets.assets.get("spaceship/spaceship.g3db",Model.class);
        instanceSpaceShip = new ModelInstance(modelSpaceShip);
        instances.add(instanceSpaceShip);


        pointLightSun = new PointLight();
        pointLightSun.set(Color.YELLOW,0,0,0,999999999);

        environment.add(pointLightSun);

    }

    private void updatePlanets(float delta){
        for (int cont = 0; cont < world3d.getPlanets().size ; cont++){
            planetsInstances.get(cont).transform.set(world3d.getPlanets().get(cont).getMatrix4());
        }

    }

    private void updateUfos(float delta){
        for (int cont = 0; cont < world3d.getUfos().size ; cont++){
            instancesUfos.get(cont).transform.set(world3d.getUfos().get(cont).getMatrix4());
        }


    }

    private void updateSpaceShip(float delta){
        instanceSpaceShip.transform.set(spaceShip.getMatrix());

      //  pointLight.position.set(spaceShip.getPosition().cpy().sub(spaceShip.getDirection()).sub(0,0,2));




    }




    public void render(float delta){

        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        updatePlanets(delta);
        updateSpaceShip(delta);
        updateUfos(delta);

        temp.set(spaceShip.getPosition());
        camera3D.position.set(temp.sub(spaceShip.getDirection().cpy().scl(10)));
        camera3D.position.add(0,3,0);

        temp.set(spaceShip.getPosition());
        camera3D.direction.set(temp.sub(camera3D.position));



       // Gdx.app.log("DATOS",String.valueOf("POSITION:" + spaceShip.getPosition())+"---ANGLE:"+spaceShip.getAngle_rot().y+"-----DIRECTION:"+spaceShip.getDirection()+"----VELOCITY:" + spaceShip.getVelocity());
       // Gdx.app.log("DATOS",String.valueOf("POSITION:" + spaceShip.getPosition())+"---CAMERA:"+camera3D.position);
        camera3D.update();



    /*   world3d.getPlanets().get(3).getMatrix4().getTranslation(temp);

        //camera3D.position.set(temp.sub(4200,0,0));
        camera3D.position.set(23100,100,0);
        camera3D.update();
*/
        modelBatch.begin(camera3D);

        modelBatch.render(instanceSun);
        modelBatch.render(instances,environment);

        modelBatch.end();


        // CONTROLS 2D
        sprite.begin();
        drawControls();
        sprite.end();



    }

    /**
     * Draw Controls
     */
    private void drawControls(){

        Rectangle control = Controls.size_controls.get(Controls.CONTROLS.UP);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.UP),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.DOWN);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.DOWN),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.LEFT);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.LEFT),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.RIGHT);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.RIGHT),control.x,control.y,control.width,control.height);

        control = Controls.size_controls.get(Controls.CONTROLS.ACCELERATE);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.ACCELERATE),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.BRAKE);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.BRAKE),control.x,control.y,control.width,control.height);

        // Velocity
        control = Controls.size_controls.get(Controls.CONTROLS.STOP);

    }
    public void resize(int width, int height){

        camera3D.fieldOfView=CAMARE3D_ANGLE;
        camera3D.viewportHeight=height;
        camera3D.viewportWidth=width;

        camera3D.position.set(38900f,0f,200f);
        camera3D.lookAt(0,0,0);
        camera3D.near=CAMARE3D_NEAR;
        camera3D.far=CAMARE3D_FAR;
        camera3D.update();

        float aspectRatio = (float) width / (float) height;
        CAMARE2D_ANCHO=400*aspectRatio;
        CAMARE2D_ALTO=400;

        camera2D.setToOrtho(false, CAMARE2D_ANCHO, CAMARE2D_ALTO);
        camera2D.update();

        sprite.setProjectionMatrix(camera2D.combined);
        shapeRenderer.setProjectionMatrix(camera2D.combined);

        Controls.changeSizeControls();

    }

    public static OrthographicCamera getCamera2D() {
        return camera2D;
    }


    private void debugger(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        temp.set(spaceShip.getPosition());

        camera3D.project(temp,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
        Vector3 temp2 = new Vector3(spaceShip.getPosition().cpy().add(spaceShip.getDirection().cpy().scl(5)));
        camera3D.project(temp2,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
        shapeRenderer.line(temp.x,temp.y,temp2.x,temp2.y);

        shapeRenderer.end();
    }


    public void dispose() {
        modelBatch.dispose();
        instances.clear();


    }



}
