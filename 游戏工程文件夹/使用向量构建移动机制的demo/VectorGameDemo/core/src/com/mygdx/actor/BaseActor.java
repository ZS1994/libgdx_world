package com.mygdx.actor;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.math.Point;
import com.mygdx.math.Vector;

public class BaseActor extends Actor{

	private Animation aniWL,aniWR,aniL,aniR;
	
	private String type;
	
	//宽，高，重量
	private float width;//单位m
	private float height;//单位m
	private float weight;//单位kg
	
	private int state=1;
	private int stateLast=1;//上一个状态
	private Vector speed;//速度，单位m/s
	private Vector power;//受到的力,单位牛
	private float stateTime=0;
	private float frameDuration=0.01f;
	
	private double time=1;//时间间隔，1s
	
	private TextureRegion drawRegion=null;//

	public ImageButton btnL,btnR,btnU,btnD;
	
	public BaseActor(float x,float y) {
		setX(x);
		setY(y);
		width=50.0f;
		height=50.0f;
		weight=1;
		power=new Vector(0, 0);//初始化
		speed=new Vector(0, 0);
		
		drawRegion=new TextureRegion(new Texture("circle.png"));
		
		/*
		Array<TextureRegion> array=new Array<TextureRegion>();
		array.add(region);
		
		aniWL=new Animation(frameDuration, array);
		aniWR=new Animation(frameDuration, array);
		aniL=new Animation(frameDuration, array);
		aniR=new Animation(frameDuration, array);
		
		aniWL.setPlayMode(PlayMode.LOOP);
		aniWR.setPlayMode(PlayMode.LOOP);
		aniL.setPlayMode(PlayMode.LOOP);
		aniR.setPlayMode(PlayMode.LOOP);
		 * */
		
		
		TextureRegion[][] regions1= TextureRegion.split(new Texture("button.png"), 120, 120) ;
		TextureRegion[][] regions2= TextureRegion.split(new Texture("button.png"), 120, 120) ;
		for (int j = 0; j < regions2[0].length; j++) {
			regions2[0][j].flip(true, false);
		}
		for (int j = 0; j < regions2[1].length; j++) {
			regions2[1][j].flip(false, true);
		}
		btnL=new ImageButton(new TextureRegionDrawable(regions1[0][0]), new TextureRegionDrawable(regions1[0][1]));
		btnR=new ImageButton(new TextureRegionDrawable(regions2[0][0]), new TextureRegionDrawable(regions2[0][1]));
		btnD=new ImageButton(new TextureRegionDrawable(regions1[1][2]), new TextureRegionDrawable(regions1[1][3]));
		btnU=new ImageButton(new TextureRegionDrawable(regions2[1][2]), new TextureRegionDrawable(regions2[1][3]));
		
		btnL.setPosition(400, 500);
		btnR.setPosition(600, 500);
		btnD.setPosition(500, 400);
		btnU.setPosition(500, 600);
		
		
		
		//---------监听---------------
		btnL.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Vector p=new Vector(180, 1);
				power=Vector.add(power, p);
				return true;
			}
		});
		btnR.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Vector p=new Vector(0, 1);
				power=Vector.add(power, p);
				return true;
			}
		});
		btnD.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Vector p=new Vector(270, 1);
				power=Vector.add(power, p);
				return true;
			}
		});
		btnU.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Vector p=new Vector(90, 1);
				power=Vector.add(power, p);
				return true;
			}
		});
	}


	public void initialize(String packpath) {
		TextureAtlas atlasL=new TextureAtlas(packpath);
		TextureAtlas atlasR=new TextureAtlas(packpath);
		for (int i = 0; i < atlasR.getRegions().size; i++) {
			atlasR.getRegions().get(i).flip(true, false);
		}
		aniL=new Animation(frameDuration, atlasL.getRegions());
		aniR=new Animation(frameDuration, atlasR.getRegions());
		aniWL=new Animation(frameDuration, atlasL.getRegions().get(0));
		aniWR=new Animation(frameDuration, atlasR.getRegions().get(0));
		
		aniR.setPlayMode(PlayMode.LOOP);
		aniL.setPlayMode(PlayMode.LOOP);
		aniWL.setPlayMode(PlayMode.LOOP);
		aniWR.setPlayMode(PlayMode.LOOP);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime=stateTime+Gdx.graphics.getDeltaTime();
		if (drawRegion!=null) {
			batch.draw(drawRegion, getX(), getY());
		}
		update();
	}
	
	private void update() {
		
		//得到加速度向量
		Vector a=new Vector(power.getDirection(), power.getMagnitude()/weight);
		
		
		
		//得到速度向量
		Vector ax=new Vector(a.getDirection(), a.getMagnitude()*time);
		speed=Vector.add(speed, ax);
		
		
		
		//得到位移向量
		Vector wy=new Vector(speed.getDirection(), speed.getMagnitude()*time);
		
		//得到终点坐标
		Point points[]=wy.getPoints(new Point(0, 0));
		
		//改变演员坐标
		setX((float)points[1].getX());
		setY((float)points[1].getY());
		
		//得到风的阻力
		Vector c=new Vector(speed.getDirection()+180, 0.0001*Math.pow(speed.getMagnitude(), 2));
				
		//力改变
		power=Vector.add(power, c);
		System.out.println(power+";"+speed);
	}
	
	//-------------------------
	public Animation getAniWL() {
		return aniWL;
	}

	public void setAniWL(Animation aniWL) {
		this.aniWL = aniWL;
	}

	public Animation getAniWR() {
		return aniWR;
	}

	public void setAniWR(Animation aniWR) {
		this.aniWR = aniWR;
	}

	public Animation getAniL() {
		return aniL;
	}

	public void setAniL(Animation aniL) {
		this.aniL = aniL;
	}

	public Animation getAniR() {
		return aniR;
	}

	public void setAniR(Animation aniR) {
		this.aniR = aniR;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getStateLast() {
		return stateLast;
	}

	public void setStateLast(int stateLast) {
		this.stateLast = stateLast;
	}

	public Vector getSpeed() {
		return speed;
	}

	public void setSpeed(Vector speed) {
		this.speed = speed;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getFrameDuration() {
		return frameDuration;
	}

	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}

	public TextureRegion getDrawRegion() {
		return drawRegion;
	}

	public void setDrawRegion(TextureRegion drawRegion) {
		this.drawRegion = drawRegion;
	}
	
}
