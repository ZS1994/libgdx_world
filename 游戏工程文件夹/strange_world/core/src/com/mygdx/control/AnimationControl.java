package com.mygdx.control;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.mygdx.actor.BaseActor;

public class AnimationControl implements IControl{

	private BaseActor actor;//需要一个角色
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	
	/**
	 * 张顺 2016年10月15日14:41:33
	 * 动画控制器
	 */
	public AnimationControl(BaseActor actor) {
		setActor(actor);
		initialize();
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void update() {
		switch (actor.getState()) {
		case MoveControl.STATE_LEFT:
			actor.setDrawRegion(actor.getAniL().getKeyFrame(actor.getStateTime()));
			break;
		case MoveControl.STATE_RIGHT:
			actor.setDrawRegion(actor.getAniR().getKeyFrame(actor.getStateTime()));
			break;
		case MoveControl.STATE_WAIT:
			if (actor.getStateLast()==MoveControl.STATE_LEFT) {
				actor.setDrawRegion(actor.getAniWL().getKeyFrame(actor.getStateTime()));
			}else if (actor.getStateLast()==MoveControl.STATE_RIGHT) {
				actor.setDrawRegion(actor.getAniWR().getKeyFrame(actor.getStateTime()));
			}
			break;
		default:
			if (actor.getStateLast()==MoveControl.STATE_LEFT) {
				actor.setDrawRegion(actor.getAniL().getKeyFrame(actor.getStateTime()));
			}else if (actor.getStateLast()==MoveControl.STATE_RIGHT) {
				actor.setDrawRegion(actor.getAniR().getKeyFrame(actor.getStateTime()));
			}
			break;
		}
	}
	

	
}
