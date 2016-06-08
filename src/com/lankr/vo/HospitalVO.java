package com.lankr.vo;

public class HospitalVO {
	
	private String grade;

	private CityVO city;
	
	private String address ;
	
	private long createTime;

	private long modifyTime;
	// 状态码
	private int _status;

	private String mark;

	private String uuid ;
	
	private String name ;
	
	public HospitalVO() {};
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public CityVO getCity() {
		return city;
	}

	public void setCity(CityVO city) {
		this.city = city;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int get_status() {
		return _status;
	}

	public void set_status(int _status) {
		this._status = _status;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getAdress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getAddress() {
		return address;
	}
}
