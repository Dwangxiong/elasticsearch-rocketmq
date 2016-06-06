package com.lankr.vo;

import java.util.Date;

public class Reource {
	private int id;
	private Date createDate;
	private Date modifyDate;
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(int speakerId) {
		this.speakerId = speakerId;
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
	private int categoryId;
	private int speakerId;
	private int status;
	private int isActive;
	private String mark;
	private String code;

}
