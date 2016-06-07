package com.lankr.mapping;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.lankr.dennisit.entity.process.ElasticSearchHandler;

public class CreateMapping {
	/**
	 * 建立索引Mapping
	 * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
	 * @param indexType  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param jsondata     json格式的数据集合
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static void createMappingOnHospital(String indexname, String type) throws IOException{
		//设置分词器
		 XContentBuilder mapping = XContentFactory.jsonBuilder()
				 .startObject()  
	                .startObject("settings")
	                	.startObject("_source")  
	                		.field("enabled", false)
	                	.endObject()  
	                    .startObject("properties")
		                    .startObject("id")
		                		.field("type", "integer")
		                		.field("store", "no")
		                		.field("indexAnalyzer","ik")
		                		.field("analyzer", "ik")
		                		.field("searchAnalyzer", "ik")
		                	.endObject()  
	                    	.startObject("uuid")
	                    		.field("type", "String")
	                    		.field("store", "no")
	                    		.field("indexAnalyzer","ik")
	                    		.field("analyzer", "ik")
	                    		.field("searchAnalyzer", "ik")
	                    	.endObject()  
		                    .startObject("name")
		                    	.field("type", "string")
		                    	.field("store", "no")
		                    	.field("indexAnalyzer","ik")
		                    	.field("analyzer", "ik")
		                    	.field("searchAnalyzer", "ik")
		                    .endObject()  
		                    .startObject("address")
		                    	.field("type", "string")
		                    	.field("store", "no")
		                    	.field("indexAnalyzer","ik")
		                    	.field("analyzer", "ik")
		                    	.field("searchAnalyzer", "ik")
		                    .endObject()
	                    .endObject()
	                .endObject()  
	            .endObject()  ;  
	    
		    
	  //建立mapping
	    PutMappingRequest putMappingRequest = Requests.putMappingRequest(indexname) ;
	    putMappingRequest.type(type) ;
	    putMappingRequest.source(mapping) ;
	    System.out.println("开始mapping");
		
	}
	
	public static void createMappingOnResource(String indexname, String type) throws IOException{
	   
		//设置分词器
		 XContentBuilder mapping = XContentFactory.jsonBuilder()
				 .startObject()  
	                .startObject("settings")
	                	.startObject("_source")  
	                		.field("enabled", false)
	                	.endObject()  
	                    .startObject("properties")
	                    	.startObject("id")
                    			.field("type", "integer")
	                    		.field("store", "no")
	                    		.field("indexAnalyzer","ik")
	                    		.field("analyzer", "ik")
	                    		.field("searchAnalyzer", "ik")
	                    	.endObject()
	                    	.startObject("uuid")
	                    		.field("type", "String")
	                    		.field("store", "no")
	                    		.field("indexAnalyzer","ik")
	                    		.field("analyzer", "ik")
	                    		.field("searchAnalyzer", "ik")
	                    	.endObject()  
		                    .startObject("name")
		                    	.field("type", "string")
		                    	.field("store", "no")
		                    	.field("indexAnalyzer","ik")
		                    	.field("analyzer", "ik")
		                    	.field("searchAnalyzer", "ik")
		                    .endObject()  
		                    .startObject("code")
		                    	.field("type", "string")
		                    	.field("store", "no")
		                    	.field("indexAnalyzer","ik")
		                    	.field("analyzer", "ik")
		                    	.field("searchAnalyzer", "ik")
		                    .endObject()
		                    .startObject("mark")
		                    	.field("type", "string")
		                    	.field("store", "no")
		                    	.field("indexAnalyzer","ik")
		                    	.field("analyzer", "ik")
		                    	.field("searchAnalyzer", "ik")
		                    .endObject()
		                    .startObject("speaker")
		                    	.field("type", "nested")
		                    		.startObject("properties")
		                    			.startObject("id")
		                    				.field("type", "integer")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                    .startObject("uuid")
		                    				.field("type", "string")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                    .startObject("name")
		                    				.field("type", "string")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                .endObject()
					        .endObject()
					        .startObject("category")
		                    	.field("type", "nested")
		                    		.startObject("properties")
		                    			.startObject("id")
		                    				.field("type", "integer")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                    .startObject("uuid")
		                    				.field("type", "string")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                    .startObject("name")
		                    				.field("type", "string")
					                    	.field("store", "no")
					                    	.field("indexAnalyzer","ik")
					                    	.field("analyzer", "ik")
					                    	.field("searchAnalyzer", "ik")
					                    .endObject()
					                .endObject()
					        .endObject()
	                    .endObject()
	                .endObject()  
	            .endObject() ;  
	    
	    
	  //建立mapping
	    PutMappingRequest putMappingRequest = Requests.putMappingRequest(indexname) ;
	    putMappingRequest.type(type) ;
	    putMappingRequest.source(mapping) ;
	    System.out.println("开始mapping");
	
	}
}
