package com.mygdx.actor;

import com.mygdx.control.AnimationControl;
import com.mygdx.control.ButtonControl;
import com.mygdx.control.CollisionControl;
import com.mygdx.control.MoveControl;
import com.mygdx.world.World;

public class MainActor extends BaseActor{

	public MainActor(float x, float y,World world) {
		super(x, y, 45,94, "Ö÷½Ç", world);
		initialize("man/man.atlas");
	}

	
	
	
}
