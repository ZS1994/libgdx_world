package com.mygdx.actor;

import com.mygdx.world.World;

public class Dog extends BaseActor{

	public Dog(float x, float y, World world) {
		super(x, y, 190,160, "��", world);
//		super(x, y, 45,94, "��", world);
		setFrameDuration(0.08f);
		initialize("dog/dog.atlas");
//		initialize("man/man.atlas");
	}

	
	
	
}
