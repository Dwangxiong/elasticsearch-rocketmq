package com.lankr.vo;

import java.util.ArrayList;
import java.util.List;

import com.lankr.dennisit.util.Tools;
import com.lankr.model.Resource;
import com.lankr.service.facade.Status;


public class ResourceVOList extends BaseAPIModel{

	private List<ResourceVO> resourceVoItems ;

	public List<ResourceVO> getResourceVoItems() {
		return resourceVoItems;
	}

	public void setResourceVoItems(List<ResourceVO> resourceVoItems) {
		this.resourceVoItems = resourceVoItems;
	}
	
	public void buildResource(List<ResourceVO> list){
		this.setStatus(Status.SUCCESS);
		if(Tools.isEmpty(list)){
			return ;
		}
		resourceVoItems=new ArrayList<ResourceVO>();
		resourceVoItems.addAll(list) ;
	}
}
