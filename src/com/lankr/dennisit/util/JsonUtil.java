package com.lankr.dennisit.util;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.lankr.model.Hospital;
import com.lankr.model.Resource;

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
    
    public static String obj2JsonData(Resource resource){
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
            .field("id", resource.getId())
            .field("uuid",resource.getUuid())
            .field("name", resource.getName())
            .field("coverTaskId",resource.getCoverTaskId())
            .field("speakerId", resource.getSpeakerId())
            .endObject();
            jsonData = jsonBuild.string();
            System.out.println(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
