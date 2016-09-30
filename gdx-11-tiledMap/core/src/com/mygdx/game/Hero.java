package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Hero extends Actor{

	Texture t1,t2,t3;
	Animation aniW,aniR,aniL,aniU,aniD;
	
	TextureRegion currentFrame;
	
	int state=3;
	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int WAIT=3;
	public static final int UP=4;
	public static final int DOWN=5;
	
	ImageButton btnL;
	ImageButton btnR;
	ImageButton btnU;
	ImageButton btnD;
	
	float statetime=0;
	float frequency=0.3f;
	public static final int SPEED_1=8;
	float speed=SPEED_1;
	//--------------------------------------------------------------------
	float width=96f;
	float height=128f;
	//----------------------------------
	
	public Hero(float x,float y) {
		this.setX(x);
		this.setY(y);
		
		t1=new Texture(Gdx.files.internal("renwu/yinXiong.png"));
		t2=new Texture(Gdx.files.internal("renwu/yinXiong2.png"));
		t3=new Texture(Gdx.files.internal("renwu/yinXiong3.png"));
		
		TextureRegion region[]=new TextureRegion[2];
		region[0]=new TextureRegion(t1);
		region[1]=new TextureRegion(t1);
		aniW=new Animation(frequency, region);
		
		
		TextureRegion region2[]=new TextureRegion[2];
		region2[0]=new TextureRegion(t1);
		region2[1]=new TextureRegion(t3);
		aniL=new Animation(frequency, region2);
		
		TextureRegion region3[]=new TextureRegion[2];
		region3[0]=new TextureRegion(t1);
		TextureRegion rtmp=new TextureRegion(new Texture(Gdx.files.internal("renwu/yinXiong3.png")));
		rtmp.flip(true, false);
		region3[1]=rtmp;
		aniR=new Animation(frequency, region3);
		
		TextureRegion region4[]=new TextureRegion[2];
		region4[0]=new TextureRegion(t1);
		TextureRegion rtmp2=new TextureRegion(new Texture(Gdx.files.internal("renwu/yinXiong2.png")));
		rtmp2.flip(false,true);
		region4[1]=rtmp2;
		aniU=new Animation(frequency, region4);

		TextureRegion region5[]=new TextureRegion[2];
		region5[0]=new TextureRegion(t1);
		region5[1]=new TextureRegion(t2);
		aniD=new Animation(frequency, region5);
		
		
		
		aniR.setPlayMode(PlayMode.NORMAL);
		aniL.setPlayMode(PlayMode.NORMAL);
		aniW.setPlayMode(PlayMode.NORMAL);
		aniD.setPlayMode(PlayMode.NORMAL);
		aniU.setPlayMode(PlayMode.NORMAL);
		
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
		
		btnL.setPosition(50, 200);
		btnR.setPosition(350, 200);
		btnU.setPosition(200, 350);
		btnD.setPosition(200, 50);
		
		
		
		btnL.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				state=WAIT;
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=LEFT;
				return true;
			}
		});
		btnR.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				state=WAIT;
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=RIGHT;
				return true;
			}
		});
		btnU.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				state=WAIT;
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=UP;
				return true;
			}
		});
		btnD.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				state=WAIT;
				super.touchUp(event, x, y, pointer, button);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state=DOWN;
				return true;
			}
		});
		//------------------------------------------------------------------------------
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
			currentFrame=aniU.getKeyFrame(statetime, false);
			break;
		case DOWN:
			currentFrame=aniD.getKeyFrame(statetime, false);
			break;
		case WAIT:
			currentFrame=aniW.getKeyFrame(statetime, false);
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
		case WAIT:
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
		case Hero.UP://测4个基本点+4个位移后的点
			if (	   MyGdxGame.passEnble(hero.getX(), hero.getY()+hero.height)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH, hero.getY()+hero.height)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*2, hero.getY()+hero.height)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*3, hero.getY()+hero.height)
					
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()+hero.height+Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH, hero.getY()+hero.height+Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*2, hero.getY()+hero.height+Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*3, hero.getY()+hero.height+Hero.SPEED_1)
						) {
					hero.speed=Hero.SPEED_1;
				}
				else {
					hero.speed=0;
					//当其不能移动时，使其紧挨障碍物边缘
					int y=((int) ((hero.getY()+Hero.SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT))*MyGdxGame.MAP_TILE_HEIGHT-1;
					hero.setY(y);
				}
			break;
		case Hero.DOWN:
			if (	   MyGdxGame.passEnble(hero.getX(), hero.getY())
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH, hero.getY())
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*2, hero.getY())
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*3, hero.getY())
					
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()-Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH, hero.getY()-Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*2, hero.getY()-Hero.SPEED_1)
					&& MyGdxGame.passEnble(hero.getX()+MyGdxGame.MAP_TILE_WIDTH*3, hero.getY()-Hero.SPEED_1)
						) {
					hero.speed=Hero.SPEED_1;
				}
				else {
					hero.speed=0;
					//当其不能移动时，使其紧挨障碍物边缘
					int y=((int) ((hero.getY()-Hero.SPEED_1)/MyGdxGame.MAP_TILE_HEIGHT)+1)*MyGdxGame.MAP_TILE_HEIGHT;
					hero.setY(y);
				}
			break;
		case Hero.LEFT://5个基本点+5个位移后的点
			if (	   MyGdxGame.passEnble(hero.getX(), hero.getY())
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()+MyGdxGame.MAP_TILE_HEIGHT)
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*2)
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*3)
					&& MyGdxGame.passEnble(hero.getX(), hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*4)
					
					&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY())
					&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT)
					&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*2)
					&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*3)
					&& MyGdxGame.passEnble(hero.getX()-Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*4)
						) {
					hero.speed=Hero.SPEED_1;
				}
				else {
					hero.speed=0;
					//当其不能移动时，使其紧挨障碍物边缘
					int x=((int) ((hero.getX()-Hero.SPEED_1)/MyGdxGame.MAP_TILE_WIDTH)+1)*MyGdxGame.MAP_TILE_WIDTH;
					hero.setX(x);
				}		
			break;
		case Hero.RIGHT:
			if (	   MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY())
					&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT)
					&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*2)
					&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*3)
					&& MyGdxGame.passEnble(hero.getX()+hero.width, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*4)
					
					&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY())
					&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT)
					&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*2)
					&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*3)
					&& MyGdxGame.passEnble(hero.getX()+hero.width+Hero.SPEED_1, hero.getY()+MyGdxGame.MAP_TILE_HEIGHT*4)
						) {
					hero.speed=Hero.SPEED_1;
				}
				else {
					hero.speed=0;
					//当其不能移动时，使其紧挨障碍物边缘
					int x=((int) ((hero.getX()+Hero.SPEED_1)/MyGdxGame.MAP_TILE_WIDTH))*MyGdxGame.MAP_TILE_WIDTH-1;
					hero.setX(x);
				}		
			break;
		default:
			break;
		}
//		System.out.println("碰撞检测——当前Y："+getY()+"    当前speed："+speed);
	}
}
