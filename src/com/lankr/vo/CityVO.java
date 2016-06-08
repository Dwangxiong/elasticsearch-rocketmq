package com.lankr.vo;

public class CityVO {
	
	private ProvinceVO province ;
	
	private String uuid;

	private String name;

	private long createTime;

	private long modifyTime;
	// 状态码
	private int _status;

	private String mark;

	public ProvinceVO getProvince() {
		return province;
	}

	public void setProvince(ProvinceVO province) {
		this.province = province;
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
}
