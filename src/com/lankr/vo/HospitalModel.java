package com.lankr.vo;

public class HospitalModel {
	private int id ;
	private String uuid ;
	private String name ;
	
	public HospitalModel() {};
	
	public HospitalModel(int id, String uuid, String name) {
		this() ;
		this.setId(id) ;
		this.setUuid(uuid) ;
		this.setName(name) ;
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
