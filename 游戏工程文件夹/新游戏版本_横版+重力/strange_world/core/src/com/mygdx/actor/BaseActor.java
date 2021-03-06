package com.mygdx.actor;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.control.IControl;
import com.mygdx.control.MoveControl;
import com.mygdx.world.World;

public class BaseActor extends Actor{

	private Animation aniWL,aniWR,aniL,aniR;
	private String type;
	private String id;//每个actor的唯一标识
	private int state=MoveControl.STATE_WAIT;
	private int stateLast=MoveControl.STATE_LEFT;//上一个状态只有左右
	private float speed=MoveControl.SPEED_1;
	private float speedy=0f;
	private float stateTime=0;
	private float frameDuration=0.01f;
	private TextureRegion drawRegion=null;//要绘制的图形
	private List<IControl> controls=new ArrayList<IControl>();//控制器们;
	private World world;//世界
	//------------跳跃标志----------
	private boolean jump=false;
	//----------矩形,用于判断baseactor之间的碰撞------------
	private Rectangle rectangle;
	
	
	
	//-------------------------
	public World getWorld() {
		return world;
	}
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isJump() {
		return jump;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public float getSpeedy() {
		return speedy;
	}
	public void setSpeedy(float speedy) {
		this.speedy = speedy;
	}
	public void setWorld(World world) {
		this.world = world;
	}
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
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public List<IControl> getControls() {
		return controls;
	}
	public void setControls(List<IControl> controls) {
		this.controls = controls;
	}
	public TextureRegion getDrawRegion() {
		return drawRegion;
	}
	public void setDrawRegion(TextureRegion drawRegion) {
		this.drawRegion = drawRegion;
	}
	public float getStateTime() {
		return stateTime;
	}
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getFrameDuration() {
		return frameDuration;
	}
	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}
	//-------------------------
	
	
	
	
	
	/**主角  
	 * <br>张顺  2016年10月15日11:47:25
	 */
	public BaseActor(float x,float y,float w,float h,String type,String id,World world) {
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		setType(type);
		setId(id);
		setWorld(world);
		rectangle=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	
	/**
	 * 初始化动画、控制器
	 * @param atlas
	 */
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
		if (controls!=null) {
			for (int i = 0; i < controls.size(); i++) {
				controls.get(i).update();
			}
		}
	}
	
	/**
	 * 张顺
	 * 2016-10-22 16:51:18
	 * 比较两个baseactor是否一样（通过唯一标示id来确定）
	 * @param baseActor
	 * @return
	 */
	public boolean equals(BaseActor baseActor) {
		return this.getId()==baseActor.getId();
	}
}
