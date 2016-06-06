package com.lankr.dennisit.entity.process;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.lankr.dennisit.util.JsonUtil;
import com.lankr.mapping.CreateMapping;
import com.lankr.vo.Hospital;
import com.lankr.vo.HospitalModel;

public class ElasticSearchHandler {

	 private Client client;
	 
	 public Client getClient() {
		return client;
	}

	private static ElasticSearchHandler esHanlder ;

	    private ElasticSearchHandler(){    
	        //使用本机做为节点
	        this("127.0.0.1");
	    }
	    
	    private ElasticSearchHandler(String ipAddress){
	        //集群连接超时设置
	        /*  
	              Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout", "10s").build();
	            client = new TransportClient(settings);
	            	节点间默认用9300进行交互
	         */
	        try {
				client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9300));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 建立单例模式
	     */
	    
	    public static ElasticSearchHandler instance() {
	    	if (esHanlder == null) {
	    		esHanlder = new ElasticSearchHandler() ;
	    	}
	    	return esHanlder ;
	    }
	    
	    /**
	     * 建立索引,索引建立好之后,会在elasticsearch-2.3.2\data\elasticsearch\nodes\0创建所以你看
	     * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
	     * @param type  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	     * @param jsondata     json格式的数据集合
	     * 
	     * @return
	     * @throws IOException 
	     */
	    
	    public void createIndexResponse(String indexname, String type,String uuid, List<String> jsondata){
	        //创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
	    	
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				try {
					CreateMapping.createMappingOnHospital(indexname,type) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	
	        IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);
	        for(int i=0; i<jsondata.size(); i++){
	            requestBuilder.setSource(jsondata.get(i)).setId(uuid).execute().actionGet();
	        }     
	         
	    }
	    
	    
	    /**
	     * 创建索引
	     * @param client
	     * @param jsondata
	     * @return
	     */
	    public IndexResponse createIndexResponse(String indexname, String type,String uuid, String jsondata){
	    	
	    	//增加数据前确保索引已经创建
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				try {
					CreateMapping.createMappingOnHospital(indexname,type) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
	        IndexResponse response = client.prepareIndex(indexname, type, uuid).setRefresh(true)
	            .setSource(jsondata)
	            .execute()
	            .actionGet();
	        return response;
	    }
	    
	    /**
	     * 执行搜索
	     * @param queryBuilder
	     * @param indexname
	     * @param type
	     * @return
	     */
	    public List<HospitalModel>  searcher(QueryBuilder queryBuilder, String indexname, String type){
	    	//查询前，没有索引则创建
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				try {
					CreateMapping.createMappingOnHospital(indexname,type) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	
	        List<HospitalModel> list = new ArrayList<HospitalModel>();
	        SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type)
	        //此处可以设置返回数据的数量
	        .setQuery(queryBuilder).setSize(50)
	        .execute()
	        .actionGet();
	        SearchHits hits = searchResponse.getHits();
	        System.out.println("查询到记录数=" + hits.getTotalHits());
	        SearchHit[] searchHits = hits.getHits() ;
	        System.out.println("实际的数量" + searchHits.length);
	        if(searchHits.length>0){
	            for(SearchHit hit:searchHits){
	                Integer id = (Integer)hit.getSource().get("id");
	                String uuid =  (String) hit.getSource().get("uuid");
	                String name =  (String) hit.getSource().get("name");
	                list.add(new HospitalModel(id, uuid, name));
	            }
	        }
	        return list;
	    }
	    
	    /**
	     * 执行删除
	     * @param id
	     * @param indexname
	     * @param type
	     * @return
	     */
	    public boolean delete (String indexname, String type, Hospital hospital) {
	    	
	    	//删除前要确保存在,否则会报错
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				return false ;
	    	
	    	DeleteResponse response = client.prepareDelete(indexname, type, hospital.getUuid()).get() ;
	    	//client.admin().indices().prepareDelete(indexname).execute().actionGet() ;
	    	if (response.isFound() ) 
	    		return true ;
	    	else
	    		return false ;
	    }
	    
	
	    /**
	     * 执行删除索引
	     * @param id
	     * @param indexname
	     * @param type
	     */
	    public void deleteIndex (String indexname) {
	    	
	    	//删除前要确保索引存在,否则会报错
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				return ;
	    	
	    	//DeleteResponse response = client.prepareDelete(indexname, type, hospital.getUuid()).get() ;
	    	client.admin().indices().prepareDelete(indexname).execute().actionGet() ;
	    	
	    }
	    
	    /**
	     * 执行更新数据
	     * @param hospital
	     * @param indexname
	     * @param type
	     */
	    public void update (String indexname, String type, Hospital hospital) {
	    	
	    	//查询前，没有索引则创建
	    	Map map = client.admin().cluster()  
                    .health(new ClusterHealthRequest(indexname)).actionGet()  
                    .getIndices() ;  
	    	boolean exists = map.containsKey(indexname) ;
	    	if (!exists)
				try {
					CreateMapping.createMappingOnHospital(indexname,type) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	int id = hospital.getId() ;
	    	String uuid = hospital.getUuid() ;
	    	String name = hospital.getName() ;
	    	
	    	UpdateRequest updateRequest = new UpdateRequest(indexname, type, uuid) ;
	    	QueryBuilder queryBuilder = QueryBuilders.termQuery("uuid", uuid) ;
	    	List<HospitalModel> hospitals = searcher(queryBuilder, indexname, type) ;
	    	if (hospitals.size() < 1) {
	    		createIndexResponse(indexname, type, uuid, JsonUtil.obj2JsonData(hospital)) ;
	    		return ;
	    	}
	    	try {
		    	if (id !=0) {
					updateRequest.doc(XContentFactory.jsonBuilder()
							.startObject()
								.field("id",id)
							.endObject()
							);
					
		    	}
		    	if (uuid != null){
			    	updateRequest.doc(XContentFactory.jsonBuilder()
			    			.startObject()
			    				.field("uuid",uuid)
			    			.endObject()
			    			);
		    	}
		    	if (name != null){
			    	updateRequest.doc(XContentFactory.jsonBuilder()
			    			.startObject()
			    				.field("name",name)
			    			.endObject()
			    			);
		    	}
	    	} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    	try {
				client.update(updateRequest).get() ;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    public static void main(String[] args) throws IOException {
//	        ElasticSearchHandler esHandler = new ElasticSearchHandler();
//	        List<String> jsondata = DataFactory.getInitJsonData();
//	        String indexname = "indexdemo";
//	        String type = "typedemo";
//	        esHandler.createIndexResponse(indexname, type, jsondata);
//	        //查询条件
//	        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("hello");
//	        /*QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//	          .must(QueryBuilders.termQuery("id", 1));*/
//	        List<Hospital> result = esHandler.searcher(queryBuilder, indexname, type);
//	        for(int i=0; i<result.size(); i++){
//	            Hospital medicine = result.get(i);
//	            //System.out.println("(" + medicine.getId() + ")药品名称:" +medicine.getName() + "\t\t" + medicine.getFunction());
//	        }
//	        boolean flag = esHandler.delete(indexname, type, "abc10") ;
//	        if (flag) 
//	        	System.out.println("删除成功") ;
//	        else
//	        	System.out.println("删除失败") ;
	      
	    }
}
