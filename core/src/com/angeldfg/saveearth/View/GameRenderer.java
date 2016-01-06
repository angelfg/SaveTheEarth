package com.angeldfg.saveearth.View;


import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Model.Planet;
import com.angeldfg.saveearth.Model.World3D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by angel on 03/01/2016.
 */
public class GameRenderer {

    private PerspectiveCamera camara3D;
    private Array<ModelInstance> instances;
    private ModelInstance instanceSun;  // Envrironment not affect => render
    private Array<ModelInstance> planetsInstances;


    private ModelBatch modelBatch;
    private Environment environment;

    private CameraInputController camController;

    private World3D world3d;
    private Vector3 temp;

    private PointLight pointLight;

    public GameRenderer(World3D world3d){

        camara3D = new PerspectiveCamera();
        instances = new Array<ModelInstance>();
        planetsInstances = new Array<ModelInstance>();

        camController = new CameraInputController(camara3D);
        Gdx.input.setInputProcessor(camController);

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new PointLight().set(1050, 0f, 0f, 2000f, 0f, 0f, 10f));

        this.world3d = world3d;
        temp = new Vector3();

        Model modelPlanet = LoadAssets.assets.get("planets/baseplanet.g3db",Model.class);
        // Load planets
        for (Planet planet : world3d.getPlanets()){

            ModelInstance modelInstance = new ModelInstance(modelPlanet);
            //modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material.set(TextureAttribute.createDiffuse(LoadAssets.textura_Planets.get(planet.getName_planet())));
            modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material= new Material(TextureAttribute.createDiffuse(LoadAssets.textura_Planets.get(planet.getName_planet())));

            if (planet.getName_planet()== Planet.PLANET_NAMES.SOL){
                instanceSun = modelInstance;
            }
            else {
//                ModelInstance temp =new ModelInstance(modelInstance);
                instances.add(modelInstance);
            }
            planetsInstances.add(modelInstance);

//            Material material = new Material(TextureAttribute.createDiffuse(LoadAssets.textura_Planets.get(planet.getName_planet())));
            //modelInstance.transform.setToTranslationAndScaling(planet.getPosition().x,planet.getPosition().y,planet.getPosition().z,planet.getScale().x,planet.getScale().y,planet.getScale().z);

//            modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material.set(material);

        }

        pointLight = new PointLight();
        pointLight.set(Color.YELLOW,0,0,0,999999999);

        environment.add(pointLight);

    }
   
    private void updatePlanets(float delta){
        for (int cont = 0; cont < world3d.getPlanets().size ; cont++){
            world3d.getPlanets().get(cont).update(delta);
            planetsInstances.get(cont).transform.set(world3d.getPlanets().get(cont).getMatrix4());
        }

    }

    public void render(float delta){

        // TODO Auto-generated method stub
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        camController.update();

       world3d.getPlanets().get(5).getMatrix4().getTranslation(temp);
        //camara3D.lookAt(temp);

        camara3D.position.set(temp.sub(4200,0,0));
        camara3D.update();

        modelBatch.begin(camara3D);
        updatePlanets(delta);

        modelBatch.render(instanceSun);
        modelBatch.render(instances,environment);

        modelBatch.end();


    }

    public void resize(int width, int height){

        camara3D.fieldOfView=67;
        camara3D.viewportHeight=height;
        camara3D.viewportWidth=width;



        camara3D.position.set(38900f,0f,200f);
        camara3D.lookAt(0,0,0);
        camara3D.near=1;
        camara3D.far=250000f;
        camara3D.update();



    }

    public void dispose() {
        modelBatch.dispose();
        instances.clear();

    }



}
