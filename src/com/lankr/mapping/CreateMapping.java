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
	 * 建立索引Mapping 类似于数据库的建表 但是不建立mapping也是可以的，ES会根据JSON文件中的类型来默认建立（最好建立，以便于管理）
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
	                .startObject(type)
	                    .startObject("properties")
		                    .startObject("grade").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
		                	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
		                	.startObject("city")
		                		.field("type", "object")
		                		.startObject("properties")
			                		.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
		                			.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
		                			.startObject("createTime").field("type", "long").field("store", "no").endObject()
			                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
			                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
			                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
			                    	.startObject("province")
			                    		.field("type", "object")
			                    		.startObject("properties")
				                    		.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
			                    			.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
			                    			.startObject("createTime").field("type", "long").field("store", "no").endObject()
					                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
					                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
					                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
			                    		.endObject()
			                    	.endObject()
		                		.endObject()
		                	.endObject()
	                    	.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
	                    	.startObject("createTime").field("type", "long").field("store", "no").endObject()
	                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
	                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
		                    .startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()  
		                    .startObject("address").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
	                    .endObject()
	                .endObject()
	            .endObject()  ;  
	    
		    
	  //建立mapping
	    PutMappingRequest putMappingRequest = Requests.putMappingRequest(indexname) ;
	    putMappingRequest.type(type) ;
	    putMappingRequest.source(mapping) ;
	    ElasticSearchHandler.instance().getClient().admin().indices().putMapping(putMappingRequest).actionGet(); 
	    System.out.println("开始mapping");
		
	}
	
	public static void createMappingOnResource(String indexname, String type) throws IOException{
	   
		//设置分词器
		 XContentBuilder mapping = XContentFactory.jsonBuilder()
				 .startObject()  
	                .startObject(type)
	                    .startObject("properties")
	                    	.startObject("rank").field("type", "integer").field("store", "no").endObject()
	                    	.startObject("rate").field("type", "float").field("store", "no").endObject()
	                    	.startObject("viewCount").field("type", "integer").field("store", "no").endObject()
	                    	.startObject("cover").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
	                    	.startObject("qr").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
	                    	.startObject("createTime").field("type", "long").field("store", "no").endObject()
	                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
	                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
	                    	.startObject("uuid").field("type", "String").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()  
		                    .startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()  
		                    .startObject("code").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
		                    .startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
			                .startObject("descript").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
		                    .startObject("type").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
		                    .startObject("updated_at").field("type", "long").field("store", "no").endObject()
		                    .startObject("speaker")
		                    	.field("type", "object")
	                    		.startObject("properties")
	                    			.startObject("_status").field("type", "integer").field("store", "no").endObject()
				                    .startObject("avatar").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
				                    .startObject("position").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
				                    .startObject("resume").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
				                    .startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
				                    .startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
				                    .startObject("createTime").field("type", "long").field("store", "no").endObject()
			                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
			                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
			                    	.startObject("hospital")
				                    	.field("type", "object")
			                    		.startObject("properties")
			                    			.startObject("grade").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
			                    			.startObject("address").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
			                    			.startObject("createTime").field("type", "long").field("store", "no").endObject()
					                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
					                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
					                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
					                    	.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
					                    	.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
					                    	.startObject("city")
					                    		.field("type", "object")
					                    		.startObject("properties")
					                    			.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
					                    			.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
					                    			.startObject("createTime").field("type", "long").field("store", "no").endObject()
							                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
							                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
							                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
							                    	.startObject("province")
							                    		.field("type", "object")
							                    		.startObject("properties")
								                    		.startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
							                    			.startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
							                    			.startObject("createTime").field("type", "long").field("store", "no").endObject()
									                    	.startObject("modifyTime").field("type", "long").field("store", "no").endObject()
									                    	.startObject("_status").field("type", "integer").field("store", "no").endObject()
									                    	.startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
							                    		.endObject()
							                    	.endObject()
					                    		.endObject()
					                    	.endObject()
			                    		.endObject()
			                    	.endObject()
				                .endObject()
					        .endObject()
					        .startObject("category")
					        	.field("type", "object")
	                    		.startObject("properties")
	                    			.startObject("_status").field("type", "integer").field("store", "no").endObject()
				                    .startObject("uuid").field("type", "string").field("store", "no").field("analyzer", "ik").field("searchAnalyzer", "ik").endObject()
				                    .startObject("name").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
				                    .startObject("createTime").field("type", "long").field("store", "no").endObject()
				                    .startObject("modifyTime").field("type", "long").field("store", "no").endObject()
				                    .startObject("mark").field("type", "string").field("store", "yes").field("analyzer", "ik").field("searchAnalyzer", "ik").field("index_options", "offsets").endObject()
				                .endObject()
					        .endObject()
	                    .endObject()
	                .endObject()  
	            .endObject() ;  
	    
	    
	  //建立mapping
	    PutMappingRequest putMappingRequest = Requests.putMappingRequest(indexname) ;
	    putMappingRequest.type(type) ;
	    putMappingRequest.source(mapping) ;
	    ElasticSearchHandler.instance().getClient().admin().indices().putMapping(putMappingRequest).actionGet();
	    System.out.println("开始mapping");
	
	}
}
