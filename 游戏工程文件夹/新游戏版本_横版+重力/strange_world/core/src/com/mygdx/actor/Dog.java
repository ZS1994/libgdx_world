package com.mygdx.actor;

import com.mygdx.tools.NameOfDate;
import com.mygdx.world.World;

public class Dog extends BaseActor{

	public Dog(float x, float y, World world) {
		super(x, y, 64,55, "��", NameOfDate.getNum(),world);
//		super(x, y, 45,94, "��", world);
		setFrameDuration(0.1f);
		initialize("dog/DOG_SMALL.atlas");
//		initialize("man/man.atlas");
	}

	
	
	
}
