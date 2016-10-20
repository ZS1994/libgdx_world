package com.mygdx.actor;

import com.mygdx.world.World;

public class Dog extends BaseActor{

	public Dog(float x, float y, World world) {
		super(x, y, 64,55, "นท", world);
//		super(x, y, 45,94, "นท", world);
		setFrameDuration(0.1f);
		initialize("dog/DOG_SMALL.atlas");
//		initialize("man/man.atlas");
	}

	
	
	
}
