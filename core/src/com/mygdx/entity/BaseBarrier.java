package com.mygdx.entity;

public class BaseBarrier {
	
	private String id;//唯一
	private String name;//暂时没想到有什么用
	private String type;//类型,比如是树、不可破坏的石头
	private String barrier;//障碍
	
	public static final String BARRIER_PASS_NO="o";
	public static final String BARRIER_PASS_YES="*";
	
	public static final String TYPE_UNDESTROY="不可破坏障碍";
	public static final String TYPE_DESTROY_TREE="可破坏障碍——树";
	
	
	
	
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
