package com.angeldfg.saveearth.View;


import com.angeldfg.saveearth.Assets.Controls;
import com.angeldfg.saveearth.Assets.LoadAssets;
import com.angeldfg.saveearth.Model.Bullet;
import com.angeldfg.saveearth.Model.Planet;
import com.angeldfg.saveearth.Model.Radar;
import com.angeldfg.saveearth.Model.SpaceShip;
import com.angeldfg.saveearth.Model.Ufo;
import com.angeldfg.saveearth.Model.World3D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by angel on 03/01/2016.
 */
public class GameRenderer {

    private static PerspectiveCamera camera3D;
    private static OrthographicCamera camera2D;

    private Array<ModelInstance> instances;
    private ModelInstance instanceSun;  // Envrironment not affect => render
    private ModelInstance instanceSpaceShip;
    private Array<ModelInstance> planetsInstances;
    private Array<ModelInstance> instancesUfos;

    private ModelBatch modelBatch;
    private Environment environment;
    private Environment environmentBullets;
    private Environment environmentUfos;

    private World3D world3d;
    private SpaceShip spaceShip;
    private Vector3 temp1;
    private Vector3 temp2;

    // To move field of stars
    private Vector2 scrollTimer=new Vector2(0,0);
    private Sprite fieldStars;


    private final float CAMARE3D_NEAR = 0.1f;
    private final float CAMARE3D_FAR = 100000;
    private final float CAMARE3D_ANGLE = 67f;


    // 2D
    private SpriteBatch sprite;

    private StringBuffer stringBuffer;

    private float CAMARE2D_ANCHO;
    private float CAMARE2D_ALTO;


    private ShapeRenderer shapeRenderer;


    private PointLight pointLightSpaceShip;
    private PointLight pointLightSun;
    private PointLight pointLightUfo;

    private BitmapFont bitMapFont;

    private ModelInstance modelInstanceBullet;
    private ModelInstance modelInstanceUfo;

    private ParticleEffect effect;
    private ParticleSystem particleSystem;

    public GameRenderer(World3D world3d){

        camera3D = new PerspectiveCamera();
        camera2D = new OrthographicCamera();

        sprite = new SpriteBatch();
        stringBuffer = new StringBuffer();

        bitMapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);

        fieldStars = new Sprite(LoadAssets.texture_static_fieldStars);

        instances = new Array<ModelInstance>();
        planetsInstances = new Array<ModelInstance>();
        instancesUfos =  new Array<ModelInstance>();

        modelBatch = new ModelBatch();
        environment = new Environment();
        environmentBullets = new Environment();
        environmentUfos = new Environment();

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environmentBullets.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environmentUfos.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));

        //environment.add(new PointLight().set(1050, 0f, 0f, 2000f, 0f, 0f, 10f));

        this.world3d = world3d;
        spaceShip = world3d.getSpaceShip();

        pointLightSpaceShip = new PointLight().set(1f, 0.5f, 0.5f, 100f, 0f, 0f, 2f);
        pointLightSpaceShip.position.set(spaceShip.getPosition());
        environment.add(pointLightSpaceShip);

        temp1 = new Vector3();
        temp2 = new Vector3();
        shapeRenderer = new ShapeRenderer();


        //Load instancesUfos
        Model modelUfo = LoadAssets.assets.get("ufo/ufo.g3db",Model.class);
        modelInstanceUfo =  new ModelInstance(modelUfo);

        pointLightUfo = new PointLight().set(0.5f, 1f, 0f, 2000f, 0f, 0f, 10f);
        pointLightUfo.setIntensity(500);
        environmentUfos.add(pointLightUfo);

/*        for (Ufo ufo : world3d.getUfos()){

            ModelInstance modelInstance = new ModelInstance(modelUfo);
            instancesUfos.add(modelInstance);
            instances.add(modelInstance);

            PointLight p = new PointLight().set(0.5f, 1f, 0f, 2000f, 0f, 0f, 10f);
            p.setIntensity(5000);
            pointLightUfos.add(p);
            environment.add(p);
        }
*/

        // Load planets
        Model modelPlanet = LoadAssets.assets.get("planets/baseplanet.g3db",Model.class);
        for (Planet planet : world3d.getPlanets()){

            ModelInstance modelInstance = new ModelInstance(modelPlanet);
            modelInstance.getNode("sphere6_sphere6_auv").parts.get(0).material= new Material(TextureAttribute.createDiffuse(LoadAssets.texture_planets.get(planet.getName_planet())));

            if (planet.getName_planet()== Planet.PLANET_NAMES.SOL){
                instanceSun = modelInstance;
            }
            else {
                instances.add(modelInstance);
            }
            planetsInstances.add(modelInstance);

        }

        // BULLETS
        ModelBuilder modelBuilder = new ModelBuilder();
        Texture textura = LoadAssets.texture_planets.get(Planet.PLANET_NAMES.SOL);
        Material material = new Material(TextureAttribute.createDiffuse(textura), ColorAttribute.createSpecular(0,0, 1, 1),
                FloatAttribute.createShininess(8f));
        long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        Model sphere = modelBuilder.createSphere(2f, 2f, 2f, 24, 24, material, attributes);
        modelInstanceBullet = new ModelInstance(sphere);


        // Load SpaceShip
        Model modelSpaceShip = LoadAssets.assets.get("spaceship/spaceship.g3db",Model.class);
        instanceSpaceShip = new ModelInstance(modelSpaceShip);
       // instances.add(instanceSpaceShip);


        pointLightSun = new PointLight();
        pointLightSun.set(Color.YELLOW,0,0,0,19999999);

        environment.add(pointLightSun);


        // Change text color
        bitMapFont.setColor(Color.RED);



        particleSystem = ParticleSystem.get();
        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
        pointSpriteBatch.setCamera(camera3D);
        particleSystem.add(pointSpriteBatch);

        LoadAssets.loadParticleEffects3D(particleSystem);
        ParticleEffect originalEffect = LoadAssets.assets.get("particle3d/explosion.pfx");
        effect = originalEffect.copy();
        effect.init();
        effect.start();  // optional: particle will begin playing immediately
        particleSystem.add(effect);

    }

    private void renderParticleEffects() {
        for (Ufo ufo : world3d.getUfos()){

            if (ufo.isDead()){
                effect.setTransform(ufo.getMatrix());
                effect.scale(ufo.getScale()*100,ufo.getScale()*100,ufo.getScale()*100);
                particleSystem.update(); // technically not necessary for rendering
                particleSystem.begin();
                particleSystem.draw();
                particleSystem.end();
                modelBatch.render(particleSystem);
            }
        }
    }

    private void updatePlanets(float delta){
        for (int cont = 0; cont < world3d.getPlanets().size ; cont++){
            planetsInstances.get(cont).transform.set(world3d.getPlanets().get(cont).getMatrix4());
        }

    }

    private void updateUfos(float delta){


        for (int cont = 0; cont < world3d.getUfos().size ; cont++){

            if (!world3d.getUfos().get(cont).isDead()) {
                modelBatch.begin(camera3D);
                world3d.getUfos().get(cont).getMatrix().getTranslation(temp1);
                modelInstanceUfo.transform.set(world3d.getUfos().get(cont).getMatrix());
                pointLightUfo.setPosition(temp1);
                modelBatch.render(modelInstanceUfo,environmentUfos);
                modelBatch.end();
            }
        }


    }


    boolean changeColor=false;

    /**
     * Update model spaceship and light
     * @param delta
     */
    private void updateSpaceShip(float delta){
        instanceSpaceShip.transform.set(spaceShip.getMatrix());

        temp1.set(spaceShip.getPosition());
        temp2.set(spaceShip.getDirection());
        pointLightSpaceShip.position.set(temp1.sub(spaceShip.getDirection().cpy().scl(0.8f)));

        float red = pointLightSpaceShip.color.r;
        if (changeColor) {
            red += 0.2f * delta;
            pointLightSpaceShip.setIntensity(pointLightSpaceShip.intensity+0.5f*delta);
        }
        else
        {
            red -= 0.2f * delta;
            pointLightSpaceShip.setIntensity(pointLightSpaceShip.intensity-0.5f*delta);
        }

        if (red>0.8f) {
            red =0.8f;
            changeColor=false;
        }
        if (red<0.2f){
            red =0.2f;
            changeColor=true;
        }

        pointLightSpaceShip.setColor(red,pointLightSpaceShip.color.g,pointLightSpaceShip.color.b,1);

        modelBatch.render(instanceSpaceShip,environment);


    }


    private void updateBullets(float delta){
        int cont;

        for (Bullet bullet : spaceShip.getBullets()){

            modelInstanceBullet.transform.set(bullet.getMatrix());
            modelBatch.render(modelInstanceBullet);

        }

    }


    public void render(float delta){

        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        updatePlanets(delta);


        // Update camera3d position => Back of Spaceship
        temp1.set(spaceShip.getPosition());
        camera3D.position.set(temp1.sub(spaceShip.getDirection().cpy().scl(1)));
        camera3D.position.add(0,0.3f,0);

        temp1.set(spaceShip.getPosition());
        camera3D.direction.set(temp1.sub(camera3D.position));

        camera3D.update();



        sprite.begin();
            drawFieldStars(delta);
        sprite.end();


        updateUfos(delta);
        modelBatch.begin(camera3D);
            updateBullets(delta);
            updateSpaceShip(delta);
            modelBatch.render(instanceSun);
            modelBatch.render(instances,environment);
            renderParticleEffects();


        modelBatch.end();

        //debugger();

        // CONTROLS 2D
        sprite.begin();
            drawControls();
            drawText();
            drawRadar();
        sprite.end();



    }

    private void drawRadar(){

        if (Radar.isMinimize()){
            sprite.draw(LoadAssets.texture_radar,Radar.size_radar_minimized.x,Radar.size_radar_minimized.y,Radar.size_radar_minimized.width,Radar.size_radar_minimized.height);
            return;
        }

        sprite.draw(LoadAssets.texture_radar,Radar.size_radar.x,Radar.size_radar.y,Radar.size_radar.width,Radar.size_radar.height);
        float radar_distance_detect=World3D.SOLARSYSTEM_SIZE+World3D.SOLARSYSTEM_SIZE/2;

        // DRAW UFOS IN RADAR
        for (Ufo ufo : world3d.getUfos()){
            temp1.set(ufo.getPosition());
            temp2.set(spaceShip.getPosition());

            float distance = temp1.dst(temp2);
            temp1.sub(temp2);   // SUB  Ufos position from  SpaceShip position

            if (distance<radar_distance_detect){
                float translateValue = (Radar.size_radar.width/2)/radar_distance_detect;
                temp1.set(temp1.x*translateValue,0,-temp1.z*translateValue);
                temp1.rotate(spaceShip.getAngle_rot().y,0,1,0);

                float pointYradarcentral = camera2D.viewportHeight-(Radar.size_radar.height/2);
                float pointXradarcentral = (Radar.size_radar.width/2);
                temp1.z = pointYradarcentral - temp1.z;
                temp1.x = pointXradarcentral - temp1.x;

                sprite.draw(LoadAssets.texture_static_ufo,temp1.x-4,temp1.z-4,10,10);

            }

        }


        // Draw Earth in radar
        Planet earth= world3d.getPlanets().get(3);

        temp1.set(earth.getPosition());
        temp1.rotate(earth.getAngle_tran(),0,1,0);
        temp2.set(spaceShip.getPosition());
        //Gdx.app.log("DATOS: PLANETA:",String.valueOf(temp1)+" NAVE:"+ String.valueOf(temp2));
        // temp2.rot(spaceShip.getMatrix());

        float distance = temp1.dst(temp2);
        temp1.sub(temp2);   // SUB  Ufos position from  SpaceShip position

        if (distance<radar_distance_detect){
            float translateValue = (Radar.size_radar.width/2)/radar_distance_detect;
            temp1.set(temp1.x*translateValue,0,-temp1.z*translateValue);
            temp1.rotate(spaceShip.getAngle_rot().y,0,1,0);

            float puntoYcentralradar = camera2D.viewportHeight-(Radar.size_radar.height/2);
            float puntoXcentralradar = (Radar.size_radar.width/2);
            temp1.z = puntoYcentralradar - temp1.z;
            temp1.x = puntoXcentralradar - temp1.x;

            sprite.draw(LoadAssets.texture_static_earth,temp1.x-4,temp1.z-4,15,15);

        }

/*        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(temp1.x, temp1.z, 5);
        shapeRenderer.end();
*/

    }


    private void drawFieldStars(float delta){

        sprite.disableBlending();

        if (spaceShip.getState()==SpaceShip.Keys.TURN_RIGHT)
            scrollTimer.x += 0.05f*Gdx.graphics.getDeltaTime();
        if (spaceShip.getState()==SpaceShip.Keys.TURN_LEFT)
            scrollTimer.x -= 0.05f*Gdx.graphics.getDeltaTime();
        if (spaceShip.getState()==SpaceShip.Keys.UP)
            scrollTimer.y -= 0.05f*Gdx.graphics.getDeltaTime();
        if (spaceShip.getState()==SpaceShip.Keys.DOWN)
            scrollTimer.y += 0.05f*Gdx.graphics.getDeltaTime();

        scrollTimer.add(0.00001f,0.00001f);

        if(scrollTimer.x>1.0f)
            scrollTimer.x = 0.0f;
        if(scrollTimer.y>1.0f)
            scrollTimer.y = 0.0f;

        fieldStars.setU(scrollTimer.x);
        fieldStars.setU2(scrollTimer.x+1);
        fieldStars.setV(scrollTimer.y);
        fieldStars.setV2(scrollTimer.y+1);

        fieldStars.draw(sprite);

        sprite.enableBlending();


    }

    private void drawText(){

        stringBuffer.setLength(0);
        stringBuffer.append("Speed: ");
        stringBuffer.append(String.valueOf((int)spaceShip.getVelocity()));

        stringBuffer.append("      "+"Aliens remain: " + String.valueOf(World3D.NUMBER_ALIENS_GAME-world3d.getNumAlienDead()));
        bitMapFont.draw(sprite, stringBuffer, 100, 20);





    }

    /**
     * Draw Controls
     */
    private void drawControls(){

        Rectangle control = Controls.size_controls.get(Controls.CONTROLS.UP);
        /*
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.UP),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.DOWN);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.DOWN),control.x,control.y,control.width,control.height);
        */
        control = Controls.size_controls.get(Controls.CONTROLS.LEFT);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.LEFT),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.RIGHT);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.RIGHT),control.x,control.y,control.width,control.height);

        control = Controls.size_controls.get(Controls.CONTROLS.ACCELERATE);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.ACCELERATE),control.x,control.y,control.width,control.height);
        control = Controls.size_controls.get(Controls.CONTROLS.BRAKE);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.BRAKE),control.x,control.y,control.width,control.height);

        control = Controls.size_controls.get(Controls.CONTROLS.FIRE);
        sprite.draw(LoadAssets.textures_controls.get(Controls.CONTROLS.FIRE),control.x,control.y,control.width,control.height);


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
        Radar.changeSizeRadar();

    }

    public static OrthographicCamera getCamera2D() {
        return camera2D;
    }


    private void debugger(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*
        temp1.set(spaceShip.getPosition());

        camera3D.project(temp1,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
        shapeRenderer.circle(temp1.x,temp1.y,20);
        Vector3 temp2 = new Vector3(spaceShip.getPosition().cpy().add(spaceShip.getDirection().cpy().scl(5)));
        camera3D.project(temp2,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
        shapeRenderer.line(temp1.x, temp1.y,temp2.x,temp2.y);
*/

/*        for (Ufo ufo : world3d.getUfos()){

            temp1.set(ufo.getSphere().center);
            camera3D.project(temp1,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
            shapeRenderer.circle(temp1.x,temp1.y,10);

            Gdx.app.log("DATOS:",String.valueOf(temp1));

        }
*/
        for (Planet planet : world3d.getPlanets()){

            temp1.set(planet.getSphere().center);
            camera3D.project(temp1,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
            temp2.set(planet.getSphere().center);
            temp2.add(planet.getSphere().radius,0,0);
            camera3D.project(temp2,0,0,camera2D.viewportWidth,camera2D.viewportHeight);
            shapeRenderer.circle(temp1.x,temp1.y,temp2.sub(temp1).x);

         //   Gdx.app.log("DATOS:",String.valueOf(temp1));

        }

        shapeRenderer.end();
    }


    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        instancesUfos.clear();

    }



}
