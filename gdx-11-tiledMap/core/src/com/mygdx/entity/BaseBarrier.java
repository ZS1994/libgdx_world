package com.mygdx.entity;

public class BaseBarrier {
	
	private String id;//Ψһ
	private String name;//��ʱû�뵽��ʲô��
	private String type;//����,���������������ƻ���ʯͷ
	private String barrier;//�ϰ�
	
	public static final String BARRIER_PASS_NO="o";
	public static final String BARRIER_PASS_YES="*";
	
	public static final String TYPE_UNDESTROY="�����ƻ��ϰ�";
	public static final String TYPE_DESTROY_TREE="���ƻ��ϰ�������";
	
	
	
	
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
