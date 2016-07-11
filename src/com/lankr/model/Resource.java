package com.lankr.model;

import java.util.Date;


public class Resource {
	private int id;
	private Date createDate;
	private Date modifyDate;
	private String name ;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String uuid;
	private String pinyin;
	private int rank;
	private float rate;
	private int viewCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

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

	public String getCoverTaskId() {
		return coverTaskId;
	}

	public void setCoverTaskId(String coverTaskId) {
		this.coverTaskId = coverTaskId;
	}

	public String getQrTaskId() {
		return qrTaskId;
	}

	public void setQrTaskId(String qrTaskId) {
		this.qrTaskId = qrTaskId;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public int getPdfId() {
		return pdfId;
	}

	public void setPdfId(int pdfId) {
		this.pdfId = pdfId;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getThreeScreenId() {
		return threeScreenId;
	}

	public void setThreeScreenId(int threeScreenId) {
		this.threeScreenId = threeScreenId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String coverTaskId;
	private String qrTaskId;
	private int assetId;
	private int pdfId;
	private int newsId;
	private int threeScreenId;
	private Speaker speaker;
	private Category category;
	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	private int status;
	private int isActive;
	private String mark;
	private String code;
	
	public static enum Type {
		PDF, NEWS, VIDEO, THREESCREEN, BROKEN
	}
	
	public Type getType() {
		if (assetId > 0) {
			return Type.VIDEO;
		} else if (pdfId > 0) {
			return Type.PDF;
		} else if (newsId > 0) {
			return Type.NEWS;
		} else if (threeScreenId > 0) {
			return Type.THREESCREEN;
		}
		return Type.BROKEN;
	}

}
