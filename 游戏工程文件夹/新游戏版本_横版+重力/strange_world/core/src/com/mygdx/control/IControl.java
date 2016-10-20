package com.mygdx.control;

import com.mygdx.actor.BaseActor;

public interface IControl {
	
	/**Âß¼­
	 * */
	public void update();

	public void setActor(BaseActor actor);
	
	public void initialize();
}
