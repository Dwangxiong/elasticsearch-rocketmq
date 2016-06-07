package com.lankr.vo;

import java.util.List;

import com.lankr.model.Speaker;

public class ResourceVO {
	private int id ;
	private String uuid ;
	private String name ;
	private String code ;
	private String mark ;
	private SpeakerVO speaker ;
	private CategoryVO category ;
	
	public ResourceVO(){}
	
	public ResourceVO(int id, String uuid, String name, String code, String mark, SpeakerVO speaker, CategoryVO category){
		this.setId(id);
		this.setUuid(uuid);
		this.setName(name);
		this.setCode(code);
		this.setMark(mark);
		this.setSpeaker(speaker);
		this.setCategory(category);
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
