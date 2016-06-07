package com.lankr.vo;

public class CategoryVO {
	private int id ;
	private String uuid ;
	private String name ;
	
	public CategoryVO() {}
	
	public CategoryVO(int id, String uuid, String name) {
		this.setId(id);
		this.setUuid(uuid);
		this.setName(name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	
}
