package com.mygdx.world.collision;

public class Barrier {
	
	private String id;//Ψһ
	private String name;//��ʱû�뵽��ʲô��
	private String type;//����,���������������ƻ���ʯͷ
	private String barrier;//�ϰ�
	
	public static final String BARRIER_PASS_NO="#";
	public static final String BARRIER_PASS_YES="o";
	
	public static final String TYPE_WATER="ˮ";
	public static final String TYPE_MOUNTAIN="ɽ";
	
	
	
	//-------------------------------------
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBarrier() {
		return barrier;
	}
	public void setBarrier(String barrier) {
		this.barrier = barrier;
	}
	//-------------------------------------	
	
	
	
}
