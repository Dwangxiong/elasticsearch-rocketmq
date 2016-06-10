package com.lankr.dennisit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.lankr.model.Hospital;
import com.lankr.model.Resource;
import com.lankr.model.Speaker;
import com.lankr.vo.CategoryVO;
import com.lankr.vo.CityVO;
import com.lankr.vo.HospitalVO;
import com.lankr.vo.ProvinceVO;
import com.lankr.vo.ResourceVO;
import com.lankr.vo.SpeakerVO;

public class JsonUtil {

	 /**
     * 实现将实体对象转换成json对象
     * @param medicine    Medicine对象
     * @return
     */
    public static String obj2JsonData(Hospital hospital){
       HospitalVO hospitalVO = model2VO(hospital) ;
       Gson gson = new Gson() ;
   		String jsonData = gson.toJson(hospitalVO) ;
        return jsonData;
    }
   
	public static String obj2JsonData(Resource resource){
		ResourceVO resourceVO = model2VO(resource) ;
    	Gson gson = new Gson() ;
    	String jsonData = gson.toJson(resourceVO) ;
    	return jsonData ;
	}

	 private static HospitalVO model2VO(Hospital hospital) {
	    	HospitalVO hospitalVO = new HospitalVO() ;
	    	if (hospital != null) {
	    		hospitalVO.set_status(-1);
	    		hospitalVO.setAddress(hospital.getAddress());
	    		Date hct = hospital.getCreateDate() ;
	    		if (hct == null)
	    			hospitalVO.setCreateTime(0);
	    		else
	    			hospitalVO.setCreateTime(hospital.getCreateDate().getTime());
	    		String grade = hospital.getGrade() ;
	    		if (grade == null)
	    			grade = "" ;
	    		hospitalVO.setGrade(grade);
	    		hospitalVO.setMark("");
	    		Date hdt = hospital.getModifyDate() ;
	    		if (hdt == null)
	    			hospitalVO.setModifyTime(0);
	    		else
	    			hospitalVO.setModifyTime(hospital.getModifyDate().getTime());
	    		hospitalVO.setName(hospital.getName());
	    		hospitalVO.setUuid(hospital.getUuid());
	    		CityVO city = new CityVO();
	    		if (hospital.getCity() != null) {
		    		city.set_status(-1);
		    		Date cct = hospital.getCity().getCreateDate() ;
		    		if (cct == null)
		    			city.setCreateTime(0);
		    		else
		    			city.setCreateTime(hospital.getCity().getCreateDate().getTime());
		    		city.setMark("");
		    		Date cdt = hospital.getCity().getModifyDate() ;
		    		if (cdt == null)
		    			city.setModifyTime(0);
		    		else
		    			city.setModifyTime(hospital.getCity().getModifyDate().getTime());
		    		city.setName(hospital.getCity().getName());
		    		city.setUuid(hospital.getCity().getUuid());
		    		ProvinceVO province = new ProvinceVO();
		    		if (hospital.getCity().getProvince() != null) {
			    		province.set_status(-1);
			    		Date pct = hospital.getCity().getProvince().getCreateDate() ;
			    		if (pct == null)
			    			province.setCreateTime(0);
			    		else
			    			province.setCreateTime(hospital.getCity().getProvince().getCreateDate().getTime());
			    		province.setMark("");
			    		Date pdt = hospital.getCity().getProvince().getModifyDate() ;
			    		if (pdt == null)
			    			province.setModifyTime(0);
			    		else
			    			province.setModifyTime(hospital.getCity().getProvince().getModifyDate().getTime());
			    		province.setName(hospital.getCity().getProvince().getName());
			    		province.setUuid(hospital.getCity().getProvince().getUuid());
		    		}
		    		city.setProvince(province);
	    		}
	    		hospitalVO.setCity(city);
	    	}
	    	
			return hospitalVO;
		}
	 
    private static ResourceVO model2VO(Resource resource) {
    	ResourceVO resourceVO = new ResourceVO() ;
    	if (resource != null) {
    		resourceVO.set_status(resource.getStatus());
    		String code = resource.getCode() ;
    		if (code == null)
    			code="" ;
    		resourceVO.setCode(code);
    		String cover = resource.getCoverTaskId() ;
    		if (cover == null)
    			cover = "" ;
    		resourceVO.setCover(resource.getCoverTaskId());
    		Date createTime = resource.getCreateDate() ;
    		if (createTime == null)
    			resourceVO.setCreateTime(0);
    		else
    			resourceVO.setCreateTime(resource.getCreateDate().getTime());
    		resourceVO.setDescript("");
    		String mark = resource.getMark() ;
    		if (mark == null)
    			mark = "" ;
    		resourceVO.setMark(mark);
    		Date modifyTime = resource.getModifyDate() ;
    		if (modifyTime == null)
    			resourceVO.setModifyTime(0);
    		else
    			resourceVO.setModifyTime(resource.getModifyDate().getTime());
    		resourceVO.setName(resource.getName());
    		String qr = resource.getQrTaskId() ;
    		if (qr == null)
    			qr = "" ;
    		resourceVO.setQr(qr);
    		resourceVO.setRank(resource.getRank());
    		resourceVO.setRate(resource.getRate());
    		resourceVO.setType("");
    		resourceVO.setUpdated_at(-1);
    		resourceVO.setUuid(resource.getUuid());
    		resourceVO.setViewCount(resource.getViewCount());
    		SpeakerVO speaker = new SpeakerVO() ;
    		if (resource.getSpeaker() != null) {
	    		speaker.set_status(resource.getSpeaker().getStatus());
	    		String avatar = resource.getSpeaker().getAvatar() ;
	    		if (avatar == null)
	    			avatar = "";
	    		speaker.setAvatar(avatar);
	    		Date sct = resource.getSpeaker().getCreateDate() ;
	    		if (sct == null)
	    			speaker.setCreateTime(0);
	    		else
	    			speaker.setCreateTime(resource.getSpeaker().getCreateDate().getTime());
	    		speaker.setMark("");
	    		Date sdt = resource.getSpeaker().getModifyDate() ;
	    		if (sdt == null)
	    			speaker.setModifyTime(0);
	    		else
	    			speaker.setModifyTime(resource.getSpeaker().getModifyDate().getTime());
	    		speaker.setName(resource.getSpeaker().getName());
	    		String position = resource.getSpeaker().getPosition() ;
	    		if (position == null)
	    			position ="";
	    		speaker.setPosition(position);
	    		String resume = resource.getSpeaker().getResume() ;
	    		if (resume == null)
	    			resume="";
	    		speaker.setResume(resume);
	    		speaker.setUuid(resource.getSpeaker().getUuid());
	    		HospitalVO hospital = new HospitalVO() ;
	    		if (resource.getSpeaker().getHospital() != null) {
	    			hospital.set_status(-1);
	    			hospital.setAddress(resource.getSpeaker().getHospital().getAddress());
	    			Date hct = resource.getSpeaker().getHospital().getCreateDate() ;
	    			if (hct == null)
	    				hospital.setCreateTime(0);
	    			else
	    				hospital.setCreateTime(resource.getSpeaker().getHospital().getCreateDate().getTime());
		    		String grade = resource.getSpeaker().getHospital().getGrade() ;
		    		if (grade == null)
		    			grade = "" ;
		    		hospital.setGrade(grade);
		    		hospital.setMark("");
		    		Date hdt = resource.getSpeaker().getHospital().getModifyDate() ;
		    		if (hdt == null)
		    			hospital.setModifyTime(0);
		    		else
		    			hospital.setModifyTime(resource.getSpeaker().getHospital().getModifyDate().getTime());
		    		hospital.setName(resource.getSpeaker().getHospital().getName());
		    		hospital.setUuid(resource.getSpeaker().getHospital().getUuid());
		    		CityVO city = new CityVO();
		    		if (resource.getSpeaker().getHospital().getCity() != null) {
				    		city.set_status(-1);
				    		Date cct = resource.getSpeaker().getHospital().getCity().getCreateDate() ;
				    		if (cct == null)
				    			city.setCreateTime(0);
				    		else
				    			city.setCreateTime(resource.getSpeaker().getHospital().getCity().getCreateDate().getTime());
				    		city.setMark("");
				    		Date cdt = resource.getSpeaker().getHospital().getCity().getModifyDate();
				    		if (cdt == null)
				    			city.setModifyTime(0);
				    		else
				    			city.setModifyTime(resource.getSpeaker().getHospital().getCity().getModifyDate().getTime());
				    		city.setName(resource.getSpeaker().getHospital().getCity().getName());
				    		city.setUuid(resource.getSpeaker().getHospital().getCity().getUuid());
				    		ProvinceVO province = new ProvinceVO();
				    		if (resource.getSpeaker().getHospital().getCity().getProvince() != null) {
					    		province.set_status(-1);
					    		Date pct = resource.getSpeaker().getHospital().getCity().getProvince().getCreateDate() ;
					    		if (pct == null)
					    			province.setCreateTime(0);
					    		else
					    			province.setCreateTime(resource.getSpeaker().getHospital().getCity().getProvince().getCreateDate().getTime());
					    		province.setMark("");
					    		Date pdt = resource.getSpeaker().getHospital().getCity().getProvince().getModifyDate() ;
					    		if (pdt == null)
					    			province.setModifyTime(0);
					    		else
					    			province.setModifyTime(resource.getSpeaker().getHospital().getCity().getProvince().getModifyDate().getTime());
					    		province.setName(resource.getSpeaker().getHospital().getCity().getProvince().getName());
					    		province.setUuid(resource.getSpeaker().getHospital().getCity().getProvince().getUuid());
				    		}
				    		city.setProvince(province);
		    		}
		    		hospital.setCity(city);
		    	}
	    		speaker.setHospital(hospital);
    		}
    		resourceVO.setSpeaker(speaker);
    		CategoryVO category = new CategoryVO() ;
    		if (resource.getCategory() != null) {
	    		category.set_status(-1);
	    		Date cct = resource.getCategory().getCreateDate() ;
	    		if (cct == null)
	    			category.setCreateTime(0);
	    		else
	    			category.setCreateTime(resource.getCategory().getCreateDate().getTime());
	    		String cmark = resource.getCategory().getMark() ;
	    		if (cmark == null)
	    			cmark ="" ;
	    		category.setMark(cmark);
	    		Date cdt = resource.getCategory().getModifyDate() ;
	    		if (cdt == null)
	    			category.setModifyTime(0);
	    		else
	    			category.setModifyTime(resource.getCategory().getModifyDate().getTime());
	    		category.setName(resource.getCategory().getName());
	    		category.setUuid(resource.getCategory().getUuid());
    		}
    		resourceVO.setCategory(category);
    	}
		return resourceVO;
	}

	public static void main(String args[]){
    	ResourceVO resource = new ResourceVO() ;
    	resource.set_status(22);
    	resource.setCode("fdfdf");
    	resource.setCover("gfgfgffgffg");
    	resource.setCreateTime(89465887);
    	resource.setDescript("fdfdfdfd");
    	resource.setMark("jmhjhkj");
    	resource.setModifyTime(54545);
    	resource.setName("womefdf");
    	resource.setQr("aaaaaa");
    	resource.setRank(11);
    	resource.setRate(34.2f);
    	resource.setType("fff");
    	resource.setUpdated_at(987788);
    	resource.setUuid("rrrrrrrrrrrrrrrrrrrrr");
    	resource.setViewCount(22);
    	SpeakerVO speaker = new SpeakerVO() ;
    	speaker.set_status(1);
    	speaker.setAvatar("ff");
    	speaker.setCreateTime(23234);
    	speaker.setMark("ss");
    	speaker.setModifyTime(6757);
    	speaker.setName("waf");
    	speaker.setPosition("fffff");
    	speaker.setResume("fdfd");
    	speaker.setUuid("bbbbbbbbbbbbb");
    	HospitalVO hospital = new HospitalVO();
    	hospital.set_status(12);
    	hospital.setAddress("hh");
    	hospital.setCreateTime(3333);
    	hospital.setGrade("gg");
    	hospital.setMark("ggg");
    	hospital.setModifyTime(6666);
    	hospital.setName("fdfdfdf");
    	hospital.setUuid("pppp");
    	CityVO city = new CityVO() ;
    	city.set_status(33);
    	city.setCreateTime(34343);
    	city.setMark("fdf");
    	city.setModifyTime(4343);
    	city.setName("fdfg");
    	city.setUuid("uuuuu");
    	ProvinceVO provinceVO = new ProvinceVO() ;
    	provinceVO.set_status(22);
    	provinceVO.setCreateTime(8888);
    	provinceVO.setMark("fdf");
    	provinceVO.setModifyTime(545454);
    	provinceVO.setName("fdfdf");
    	provinceVO.setUuid("fdfdfdf");
    	city.setProvince(provinceVO);
    	hospital.setCity(city);
    	speaker.setHospital(hospital);
    	resource.setSpeaker(speaker);
    	CategoryVO category = new CategoryVO() ;
    	category.set_status(33);
    	category.setCreateTime(5454);
    	category.setMark("yyy");
    	category.setModifyTime(877676);
    	category.setName("gfg");
    	category.setUuid("gfhghghg");
    	resource.setCategory(category);
    	Gson gson = new Gson() ;
    	String aa = gson.toJson(resource) ;
    	System.out.println(aa);
		ResourceVO rr= gson.fromJson(aa, ResourceVO.class);
		System.out.println("-----------------" + rr);
    }
}
