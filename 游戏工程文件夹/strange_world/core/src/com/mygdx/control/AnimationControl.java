package com.mygdx.control;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.mygdx.actor.BaseActor;

public class AnimationControl implements IControl{

	private BaseActor actor;//��Ҫһ����ɫ
	
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
	
	/**
	 * ��˳ 2016��10��15��14:41:33
	 * ����������
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
