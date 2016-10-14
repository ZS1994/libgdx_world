package com.mygdx.actor;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.entity.Barrier;
import com.mygdx.game.MyGdxGame;
import com.mygdx.tools.Transtion;

public class Hero extends Actor{

	Animation aniW_L,aniW_R,aniR,aniL;
	
	TextureRegion currentFrame;
	
	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int WAIT_LEFT=3;
	public static final int UP=4;
	public static final int DOWN=5;
	public static final int WAIT_RIGHT=6;
	int state=WAIT_LEFT;
	int state_last=WAIT_LEFT;
	
	public ImageButton btnL;
	public ImageButton btnR;
	public ImageButton btnU;
	public ImageButton btnD;
	public ImageButton btn_A;
	public ImageButton btn_B;
	
	float statetime=0;
	float frequency=0.016f;
	public static final int SPEED_0=0;
	public static final int SPEED_1=4;
	float speed=SPEED_1;
	//--------------------------------------------------------------------
	float width=45f;
	float height=94f;
	//----------------------------------
	Logger logger=new Logger("调试");	
		
	public Hero(float x,float y) {
		this.setX(x);
		this.setY(y);
		
		TextureAtlas atlas1=new TextureAtlas(Gdx.files.internal("renwu/man.atlas"));
		TextureAtlas atlas2=new TextureAtlas(Gdx.files.internal("renwu/man.atlas"));
		
		TextureRegion regions2[]=new TextureRegion[60];
		Array<AtlasRegion> atlasRegion=atlas2.getRegions();
		for (int i = 0; i < atlasRegion.size; i++) {
			atlasRegion.get(i).flip(true, false);
		}
		
		
		aniW_L=new Animation(frequency, atlas1.findRegions("run0001"));
		aniW_R=new Animation(frequency, atlas2.findRegions("run0001"));
		aniL=new Animation(frequency, atlas1.getRegions());
		aniR=new Animation(frequency, atlas2.getRegions());
		
		
		aniR.setPlayMode(PlayMode.LOOP);
		aniL.setPlayMode(PlayMode.LOOP);
		aniW_L.setPlayMode(PlayMode.LOOP);
		aniW_R.setPlayMode(PlayMode.LOOP);
		
		//------------------------------------------------------------------
		
		TextureRegion tmp11[][]=TextureRegion.split(new Texture(Gdx.files.internal("button.png")), 120, 120); 
		TextureRegion tmp21[][]=TextureRegion.split(new Texture(Gdx.files.internal("button.png")), 120, 120); 
		TextureRegion tmp31[][]=TextureRegion.split(new Texture(Gdx.files.internal("button.png")), 120, 120); 
		TextureRegion tmp41[][]=TextureRegion.split(new Texture(Gdx.files.internal("button.png")), 120, 120); 
		
		TextureRegionDrawable imageUp1=new TextureRegionDrawable(tmp11[0][0]);
		TextureRegionDrawable imageDown1=new TextureRegionDrawable(tmp11[0][1]);
		btnL=new ImageButton(imageUp1, imageDown1);
		
		tmp21[0][0].flip(true, false);
		tmp21[0][1].flip(true, false);
		TextureRegionDrawable imageUp2=new TextureRegionDrawable(tmp21[0][0]);
		TextureRegionDrawable imageDown2=new TextureRegionDrawable(tmp21[0][1]);
		btnR=new ImageButton(imageUp2, imageDown2);
		
		TextureRegionDrawable imageUp3=new TextureRegionDrawable(tmp11[1][2]);
		TextureRegionDrawable imageDown3=new TextureRegionDrawable(tmp11[1][3]);
		btnD=new ImageButton(imageUp3, imageDown3);
		
		tmp21[1][2].flip(false, true);
		tmp21[1][3].flip(false, true);
		TextureRegionDrawable imageUp4=new TextureRegionDrawable(tmp21[1][2]);
		TextureRegionDrawable imageDown4=new TextureRegionDrawable(tmp21[1][3]);
		btnU=new ImageButton(imageUp4, imageDown4);
		
		TextureRegionDrawable imageUp5=new TextureRegionDrawable(tmp11[0][2]);
		TextureRegionDrawable imageDown5=new TextureRegionDrawable(tmp11[0][3]);
		btn_A=new ImageButton(imageUp5, imageDown5);
		
		TextureRegionDrawable imageUp6=new TextureRegionDrawable(tmp11[1][0]);
		TextureRegionDrawable imageDown6=new TextureRegionDrawable(tmp11[1][1]);
		btn_B=new ImageButton(imageUp6, imageDown6);
		
		
		btnL.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed=SPEED_0;
				switch (state_last) {
				case LEFT:
					state=WAIT_LEFT;
					break;
				case RIGHT:
					state=WAIT_RIGHT;
					break;
				default:
					break;
				}
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=LEFT;
				state_last=state;
				speed=SPEED_1;
				return true;
			}
		});
		btnR.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed=SPEED_0;
				switch (state_last) {
				case LEFT:
					state=WAIT_LEFT;
					break;
				case RIGHT:
					state=WAIT_RIGHT;
					break;
				default:
					break;
				}
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=RIGHT;
				state_last=state;
				speed=SPEED_1;
				return true;
			}
		});
		btnU.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed=SPEED_0;
				switch (state_last) {
				case LEFT:
					state=WAIT_LEFT;
					break;
				case RIGHT:
					state=WAIT_RIGHT;
					break;
				default:
					break;
				}
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=UP;
				speed=SPEED_1;
				return true;
			}
		});
		btnD.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed=SPEED_0;
				switch (state_last) {
				case LEFT:
					state=WAIT_LEFT;
					break;
				case RIGHT:
					state=WAIT_RIGHT;
					break;
				default:
					break;
				}
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=DOWN;
				speed=SPEED_1;
				return true;
			}
		});
		btn_A.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				if (speed==SPEED_0) {
					switch (state) {
					case UP:
						destroyTree(getX(), getY()+height+SPEED_1, UP);
						break;
					case DOWN:
						destroyTree(getX(), getY()-SPEED_1, DOWN);
						break;
					default:
						break;
					}
				}
				return true;
			}
		});
		
		btn_B.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				int xIndex=Transtion.xToxIndex(getX());
				int yIndex=Transtion.xToxIndex(getY());
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 3; j++) {
						MyGdxGame.barriers[yIndex+i][xIndex+j].setBarrier(Barrier.BARRIER_PASS_NO);
						MyGdxGame.barriers[yIndex+i][xIndex+j].setType(Barrier.TYPE_DESTROY_TREE);
						MyGdxGame.mapLayers.get("obstacle_destroy_tree").setCell(xIndex+j, yIndex+i, MyGdxGame.mapCells.get("树"+(j+1)+"-"+(i+1)));
					}
				}
				return true;
			}
		});
		//------------------------------------------------------------------------------
	}
	
	
	/**砍树
	 * 张顺 2016年10月9日17:28:38
	 * @param x  左右：接触边左起 ； 上下：下起第一个点的X坐标
	 * @param y  左右：接触边左起 ； 上下：下起第一个点的Y坐标
	 * @param state 方向
	 */
	@SuppressWarnings("unused")
	private void destroyTree(float x,float y,int state) {
		int xIndex = 0;
		int yIndex = 0;
		//先找到碰撞的点
		if (state==UP || state==DOWN) {
			if (!MyGdxGame.passEnble(x, y) && MyGdxGame.queryTypeFromBarrier(x, y).equals(Barrier.TYPE_DESTROY_TREE)) {
				xIndex=(int)x/MyGdxGame.MAP_TILE_WIDTH;
				yIndex=(int)y/MyGdxGame.MAP_TILE_HEIGHT;
			}else if (!MyGdxGame.passEnble(x+MyGdxGame.MAP_TILE_WIDTH, y) && MyGdxGame.queryTypeFromBarrier(x+MyGdxGame.MAP_TILE_WIDTH, y).equals(Barrier.TYPE_DESTROY_TREE)) {
				xIndex=(int)(x+MyGdxGame.MAP_TILE_WIDTH)/MyGdxGame.MAP_TILE_WIDTH;
				yIndex=(int)(y)/MyGdxGame.MAP_TILE_HEIGHT;
			}else if (!MyGdxGame.passEnble(x+MyGdxGame.MAP_TILE_WIDTH*2, getY()+height+SPEED_1) && MyGdxGame.queryTypeFromBarrier(x+MyGdxGame.MAP_TILE_WIDTH*2, y).equals(Barrier.TYPE_DESTROY_TREE)) {
				xIndex=(int)(x+MyGdxGame.MAP_TILE_WIDTH*2)/MyGdxGame.MAP_TILE_WIDTH;
				yIndex=(int)(getY()+height+SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT;
			}else if (!MyGdxGame.passEnble(x+MyGdxGame.MAP_TILE_WIDTH*3, getY()+height+SPEED_1) && MyGdxGame.queryTypeFromBarrier(x+MyGdxGame.MAP_TILE_WIDTH*3, y).equals(Barrier.TYPE_DESTROY_TREE)) {
				xIndex=(int)(x+MyGdxGame.MAP_TILE_WIDTH*3)/MyGdxGame.MAP_TILE_WIDTH;
				yIndex=(int)(getY()+height+SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT;
			}
		}else if (state==LEFT || state==RIGHT) {
			 
			
		}
		//找到树的起点（左下角）
		switch (state) {
		case UP:
			if (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex].getType())) {
				while (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex-1].getType()) &&
						MyGdxGame.barriers[yIndex][xIndex-1].getBarrier()==Barrier.BARRIER_PASS_NO) {
					xIndex=xIndex-1;
				}
			}
			break;
		case DOWN:
			if (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex].getType())) {
				while (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex-1].getType()) &&
						MyGdxGame.barriers[yIndex][xIndex-1].getBarrier()==Barrier.BARRIER_PASS_NO) {
					xIndex=xIndex-1;
				}
				yIndex=yIndex-3;
			}
			break;
		case LEFT:
			if (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex].getType())) {
				while (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex-1][xIndex].getType()) &&
						MyGdxGame.barriers[yIndex-1][xIndex].getBarrier()==Barrier.BARRIER_PASS_NO) {
					yIndex=yIndex-1;
				}
				xIndex=xIndex-2;
			}
			break;
		case RIGHT:
			if (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex][xIndex].getType())) {
				while (Barrier.TYPE_DESTROY_TREE.equals(MyGdxGame.barriers[yIndex-1][xIndex].getType()) &&
						MyGdxGame.barriers[yIndex-1][xIndex].getBarrier()==Barrier.BARRIER_PASS_NO) {
					yIndex=yIndex-1;
				}
			}
			break;
		default:
			break;
		}
		//当接触的面不是可摧毁的场景，那么xindex和yindex必定是初始值0
		if (xIndex!=0 && yIndex!=0) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					MyGdxGame.barriers[yIndex+i][xIndex+j].setBarrier(Barrier.BARRIER_PASS_YES);
					MyGdxGame.mapLayers.get("obstacle_destroy_tree").setCell(xIndex+j, yIndex+i, MyGdxGame.mapCells.get("土地"));
				}
			}
		}
	}
	
	
	/**
	 * 更换动画
	 */
	private void checkState() {
		switch (state) {
		case RIGHT:
			currentFrame=aniR.getKeyFrame(statetime, false);
			break;
		case LEFT:
			currentFrame=aniL.getKeyFrame(statetime, false);
			break;
		case UP:
			switch (state_last) {
			case LEFT:
				currentFrame=aniL.getKeyFrame(statetime, false);
				break;
			case RIGHT:
				currentFrame=aniR.getKeyFrame(statetime, false);
				break;
			default:
				break;
			}
			break;
		case DOWN:
			switch (state_last) {
			case LEFT:
				currentFrame=aniL.getKeyFrame(statetime, false);
				break;
			case RIGHT:
				currentFrame=aniR.getKeyFrame(statetime, false);
				break;
			default:
				break;
			}
			break;
		case WAIT_LEFT:
			currentFrame=aniW_L.getKeyFrame(statetime, false);
			break;
		case WAIT_RIGHT:
			currentFrame=aniW_R.getKeyFrame(statetime, false);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 位移
	 */
	private void move() {
		switch (state) {
		case LEFT:
			setX(getX()-speed);
			break;
		case RIGHT:
			setX(getX()+speed);
			break;
		case UP:
			setY(getY()+speed);
			break;
		case DOWN:
			setY(getY()-speed);
			break;
		case WAIT_LEFT:
			break;
		case WAIT_RIGHT:
			break;
		default:
			break;
		}
//		System.out.println("位移——当前Y："+getY()+"    当前speed："+speed);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		statetime=statetime+Gdx.graphics.getDeltaTime();
		checkCollision(this);
		checkState();
		move();
		batch.draw(currentFrame, this.getX(), this.getY());
		
	}
	
	/**
	 * 碰撞检测
	 */
	private void checkCollision(Hero hero) {
		switch (hero.state) {
		case Hero.UP://测3个位移后的点
			if (   MyGdxGame.passEnble(hero.getX(), hero.getY()+hero.height+Hero.SPEED_1)
				&& MyGdxGame.passEnble(hero.getX()+hero.width/2, hero.getY()+hero.height+Hero.SPEED_1)
				&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()+hero.height+Hero.SPEED_1)
					) {
				if (hero.speed!=Hero.SPEED_0) {
					hero.speed=Hero.SPEED_1;
				}
			}
			else {
				hero.speed=SPEED_0;
				//当其不能移动时，使其紧挨障碍物边缘
				if (hero.speed==SPEED_0) {
					float y=((int) ((hero.getY()+hero.height+Hero.SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT))*MyGdxGame.MAP_TILE_HEIGHT-hero.height-1;
					hero.setY(y);
				}
			}
			break;
		case Hero.DOWN:
			if (   MyGdxGame.passEnble(hero.getX(), hero.getY()-Hero.SPEED_1)
				&& MyGdxGame.passEnble(hero.getX()+hero.width/2, hero.getY()-Hero.SPEED_1)
				&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()-Hero.SPEED_1)
					) {
				if (hero.speed!=Hero.SPEED_0) {
					hero.speed=Hero.SPEED_1;
				}
			}
			else {
				hero.speed=SPEED_0;
				//当其不能移动时，使其紧挨障碍物边缘
				if (hero.speed==SPEED_0) {
					int y=((int) ((hero.getY()-Hero.SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT)+1)*MyGdxGame.MAP_TILE_HEIGHT;
					hero.setY(y);
				}
			}
			break;
		case Hero.LEFT://4个位移后的点
			if (   MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY())
				&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+hero.height/3)
				&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+hero.height/3*2)
				&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+hero.height)
					) {
				if (hero.speed!=Hero.SPEED_0) {
					hero.speed=Hero.SPEED_1;
				}
			}else {
				hero.speed=SPEED_0;
				//使其紧挨障碍物边缘
				int x=((int) ((hero.getX()-Hero.SPEED_1)/MyGdxGame.MAP_TILE_WIDTH)+1)*MyGdxGame.MAP_TILE_WIDTH;
				hero.setX(x);
			}
			break;
		case Hero.RIGHT:
			if (   MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY())
				&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+hero.height/3)
				&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+hero.height/3*2)
				&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+hero.height)
					) {
				if (hero.speed!=Hero.SPEED_0) {
					hero.speed=Hero.SPEED_1;
				}
			}else {
				hero.speed=SPEED_0;
				//当其不能移动时，使其紧挨障碍物边缘
				float x=((int) ((hero.getX()+hero.width+Hero.SPEED_1)/MyGdxGame.MAP_TILE_WIDTH))*MyGdxGame.MAP_TILE_WIDTH-1-hero.width;
				hero.setX(x);
			}
			break;
		default:
			break;
		}
	}
}
