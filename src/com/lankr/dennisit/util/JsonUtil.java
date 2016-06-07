package com.lankr.dennisit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.lankr.model.Hospital;
import com.lankr.model.Resource;
import com.lankr.model.Speaker;
import com.lankr.vo.CategoryVO;
import com.lankr.vo.ResourceVO;
import com.lankr.vo.SpeakerVO;

public class JsonUtil {

	 /**
     * 实现将实体对象转换成json对象
     * @param medicine    Medicine对象
     * @return
     */
    public static String obj2JsonData(Hospital hospital){
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
            .field("id", hospital.getId())
            .field("uuid",hospital.getUuid())
            .field("name", hospital.getName())
            .field("address",hospital.getAddress())
            .endObject();
            jsonData = jsonBuild.string();
            System.out.println(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
    
    public static String obj2JsonData(ResourceVO resourceVO){
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            
            jsonBuild.startObject()
            .field("id", resourceVO.getId())
            .field("uuid",resourceVO.getUuid())
            .field("name", resourceVO.getName())
            .field("code",resourceVO.getCode())
            .field("mark", resourceVO.getMark())
            .field("speaker", JsonUtil.parseSpeaker(resourceVO.getSpeaker()))
            .field("category", JsonUtil.parseCategory(resourceVO.getCategory()))
            .endObject();
            jsonData = jsonBuild.string();
            System.out.println(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

	private static String parseSpeaker(SpeakerVO speaker) {
		if (speaker != null) {
			 XContentBuilder jsonBuild;
			 String jsonData = null ;
			try {
				jsonBuild = XContentFactory.jsonBuilder();
				jsonBuild.startObject()
	            .field("id", speaker.getId())
	            .field("uuid",speaker.getUuid())
	            .field("name", speaker.getName())
	            .endObject();
	            jsonData = jsonBuild.string();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonData ;
		}
		
		return null ;
	}
	
	private static String parseCategory(CategoryVO category) {
		if (category != null) {
			 XContentBuilder jsonBuild ;
			 String jsonData = null ;
			try {
				jsonBuild = XContentFactory.jsonBuilder();
				jsonBuild.startObject()
	            .field("id", category.getId())
	            .field("uuid",category.getUuid())
	            .field("name", category.getName())
	            .endObject();
	            jsonData = jsonBuild.string();
			} catch (IOException e) {
				e.printStackTrace();
			}
		            
			return jsonData ;
		}
		return null ;
	}
}
