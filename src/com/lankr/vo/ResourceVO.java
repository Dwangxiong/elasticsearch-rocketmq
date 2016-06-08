package com.lankr.vo;

import java.util.List;

import com.lankr.model.Speaker;

public class ResourceVO {
	
	private int rank ;
	
	private float rate;
	
	private int viewCount;
	
	private String cover;
	
	private String qr;
	
	private long createTime;

	private long modifyTime;
	// 状态码
	private int _status;
	
	private String uuid ;
	
	private String name ;
	
	private String code ;
	
	private String mark ;
	
	private String descript;
	
	private SpeakerVO speaker ;
	
	private CategoryVO category ;
	
	private String type;

	public long updated_at; // ms

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
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

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(long updated_at) {
		this.updated_at = updated_at;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public SpeakerVO getSpeaker() {
		return speaker;
	}

	public void setSpeaker(SpeakerVO speaker) {
		this.speaker = speaker;
	}

	public CategoryVO getCategory() {
		return category;
	}

	public void setCategory(CategoryVO category) {
		this.category = category;
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
