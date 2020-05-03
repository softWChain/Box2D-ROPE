package com.veys.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.veys.game.Game;
import com.veys.game.managers.GSM;

public class PlayState extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera camb2dr;
	private Body platform,target;
	private Body player,circle1,player2,circle2;
	private TmxMapLoader tmx;
	private TiledMap map;
	private OrthogonalTiledMapRenderer tiledRenderer;
	
	private RevoluteJoint rightWhell,leftWhell;
	private DistanceJoint df;
	private float value = 3;
	
	public static final float PPM = 100;
	public static final short  BIT_GROUND = 2;
	public static final short  BIT_BOX = 4;
	public static final short BIT_PLAYER = 8;
	public static Viewport view;
	
	public PlayState(GSM gsm) {
		super(gsm);
		

		
		
		camb2dr = new OrthographicCamera();
		camb2dr.setToOrtho(false,(Game.WIDTH)/PPM,(Game.HEIGHT)/PPM);

		view = new FitViewport(Game.WIDTH/PPM, Game.HEIGHT/PPM);
		
		world = new World(new Vector2(0,-9.81f),true);

		b2dr = new Box2DDebugRenderer();
		
		target = createBox(300, 500, 32, 32, false, true);
		target.setLinearDamping(2000000f);
		
		DefinitionMyWorldMethod();
		
		

		/*
		tmx = new TmxMapLoader();
		map = tmx.load("level1.tmx");
		tiledRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
		*/
		
		/*
		for(MapObject object : map.getLayers().get(2)
				.getObjects()
				.getByType(RectangleMapObject.class)) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyType.StaticBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/PPM,
					(rect.getY()+rect.getHeight()/2)/PPM);
			
			body = world.createBody(bdef);
			
			shape.setAsBox((rect.getWidth()/2)/PPM, 
					(rect.getHeight()/2)/PPM);
			
			fdef.shape = shape;
			body.createFixture(fdef);
			
		}
		
		*/
	
	}

	
	public void DefinitionMyWorldMethod() {
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		CircleShape shapeCircle = new CircleShape();
		FixtureDef fdef = new FixtureDef();
		
		
		
		//create platform
		//createBox(100, 100, 700, 10, true, false);

		////create object

		Array<Body> bodies = new Array<Body>();
		bodies.add(createBox(400, 500, 16, 16, true, true));
		
		for(int i=1;i<10;i++) {
			bodies.add(createBox(400, -i*32, 4, 32, false, false));
			
			RopeJointDef rDef = new RopeJointDef();
			rDef.bodyA = bodies.get(i - 1);
			rDef.bodyB = bodies.get(i);
			rDef.collideConnected = true;
			rDef.maxLength = 40f/PPM;
			rDef.localAnchorA.set(0,-0.5f/PPM);
			rDef.localAnchorB.set(0,0.5f/PPM);
			
			world.createJoint(rDef);
		}
	
	}
	

	public void HandleInput(float dt) {
	
        float x = 0, y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 1;
        }

        // Dampening check
        if(x != 0) {
            target.setLinearVelocity(x * 2800000 * dt, target.getLinearVelocity().y);
        }
        if(y != 0) {
            target.setLinearVelocity(target.getLinearVelocity().x, y * 2800000 * dt);
        }
		
	}

	public void tick(float dt) {
		
		HandleInput(dt);

		world.step(1/60f, 6,2);
		camb2dr.update();
		
	}

	public void render(SpriteBatch sb) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		b2dr.render(world, camb2dr.combined);
		
		
		
	}
	
	
	public Body createBox(int x , int y ,int width , int height,boolean isStatic,boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        pBody.createFixture(fd);
        shape.dispose();
        return pBody;
	}
	
	public Body createCircle(int x,int y,int radius,boolean fixedRotation) {
		Body pBody;
		BodyDef def = new BodyDef();

		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = fixedRotation;
		pBody = world.createBody(def);

		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius/2/PPM);

		pBody.createFixture(shape,1.0f);
		shape.dispose();
		return pBody;
		
		
	}


	@Override
	public void dispose() {
		b2dr.dispose();
		world.dispose();
		map.dispose();
		
	}

	
	

}
