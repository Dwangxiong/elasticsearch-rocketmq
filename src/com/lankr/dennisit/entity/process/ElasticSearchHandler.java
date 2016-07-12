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
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteRequest;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.lankr.dennisit.util.JsonUtil;
import com.lankr.mapping.CreateMapping;
import com.lankr.model.Category;
import com.lankr.model.Hospital;
import com.lankr.model.Speaker;
import com.lankr.vo.CategoryVO;
import com.lankr.vo.HospitalVO;
import com.lankr.vo.ResourceVO;
import com.lankr.vo.SpeakerVO;
/**
 * 
 * @author Lankr
 * 如需要支持全文查询 可做一个column将所有的文档归入，然后查询该列，
 * query的operator设置为OR 能实现智能查询
 *
 */
public class ElasticSearchHandler {

	private Client client;

	public Client getClient() {
		return client;
	}

	private static ElasticSearchHandler esHanlder;

	private ElasticSearchHandler() {
		// 使用本机做为节点
		this("127.0.0.1");
	}

	private ElasticSearchHandler(String ipAddress) {
		// 集群连接超时设置
		/*
		 * Settings settings = ImmutableSettings.settingsBuilder().put(
		 * "client.transport.ping_timeout", "10s").build(); client = new
		 * TransportClient(settings); 节点间默认用9300进行交互
		 */
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立单例模式
	 */

	public static ElasticSearchHandler instance() {
		if (esHanlder == null) {
			esHanlder = new ElasticSearchHandler();
		}
		return esHanlder;
	}

	/**
	 * 建立索引,索引建立好之后,会在elasticsearch-2.3.2\data\elasticsearch\nodes\0创建
	 * 
	 * @param indexName
	 *            为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
	 * @param type
	 *            Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param jsondata
	 *            json格式的数据集合
	 * 
	 * @return
	 * @throws IOException
	 */

	public void createIndexResponse(String indexname, String type, String uuid, List<String> jsondata) {
		// 创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
		checkIndex(indexname);
		boolean exists = client.admin().indices().prepareTypesExists().setTypes(type).execute().actionGet().isExists();
		if (!exists) {
			if ("hospital".equals(type)) {
				try {
					CreateMapping.createMappingOnHospital(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if ("resource".equals(type)) {
				try {
					CreateMapping.createMappingOnResource(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);
		for (int i = 0; i < jsondata.size(); i++) {
			requestBuilder.setSource(jsondata.get(i)).setId(uuid).execute().actionGet();
		}

	}

	/**
	 * 创建索引
	 * 
	 * @param client
	 * @param jsondata
	 * @return
	 */
	public IndexResponse createIndexResponse(String indexname, String type, String uuid, String jsondata) {

		// 增加数据前确保索引已经创建
		checkIndex(indexname);
		boolean exists = client.admin().indices().prepareTypesExists().setTypes(type).execute().actionGet().isExists();
		if (!exists) {
			if ("hospital".equals(type)) {
				try {
					CreateMapping.createMappingOnHospital(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if ("resource".equals(type)) {
				try {
					CreateMapping.createMappingOnResource(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		IndexResponse response = client.prepareIndex(indexname, type, uuid).setRefresh(true).setSource(jsondata)
				.execute().actionGet();
		return response;
	}

	/**
	 * 执行搜索
	 * 
	 * @param queryBuilder
	 * @param indexname
	 * @param type
	 * @return
	 */
	public SearchHit[] searcher(QueryBuilder queryBuilder, String indexname, String type) {
		// 查询前，没有索引则创建
		checkIndex(indexname);
		boolean exists = client.admin().indices().prepareTypesExists().setTypes(type).execute().actionGet().isExists();
		if (!exists) {
			if ("hospital".equals(type)) {
				try {
					CreateMapping.createMappingOnHospital(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if ("resource".equals(type)) {
				try {
					CreateMapping.createMappingOnResource(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		List<HospitalVO> list = new ArrayList<HospitalVO>();
		
		SearchResponse searchResponse = null ;
		if ("hospital".equals(type))
			searchResponse = client.prepareSearch(indexname).setTypes(type).addHighlightedField("name").setHighlighterPreTags("<i class='result-mark'>")
				// 此处可以设置返回数据的数量
				.setHighlighterPostTags("</i>").setQuery(queryBuilder).setSize(50).execute().actionGet();
		else if ("resource".equals(type)) 
			searchResponse = client.prepareSearch(indexname).setTypes(type).addHighlightedField("name").addHighlightedField("mark")
				// 此处可以设置返回数据的数量
				.addHighlightedField("speaker.name").addHighlightedField("category.name").setHighlighterPreTags("<i class='result-mark'>")
				.setHighlighterPostTags("</i>").setQuery(queryBuilder).setSize(50).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHits = hits.getHits();
		System.out.println("实际的数量" + searchHits.length);
		
		return searchHits;
	}

	/**
	 * 执行删除
	 * 
	 * @param id
	 * @param indexname
	 * @param type
	 * @return
	 */
	public boolean delete(String indexname, String type, String content) {

		// 删除前要确保存在,否则会报错
		Map map = client.admin().cluster().health(new ClusterHealthRequest(indexname)).actionGet().getIndices();
		boolean exists = map.containsKey(indexname);
		if (!exists)
			return false;
		JSONObject jsonObject = null ;
		String uuid = "" ;
		try {
			jsonObject = new JSONObject(content);
			uuid = jsonObject.getString("uuid") ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DeleteResponse response = client.prepareDelete(indexname, type, uuid).get();
		// client.admin().indices().prepareDelete(indexname).execute().actionGet()
		// ;
		if (response.isFound())
			return true;
		else
			return false;
	}

	/**
	 * 执行删除索引
	 * 
	 * @param id
	 * @param indexname
	 * @param type
	 */
	public void deleteIndex(String indexname) {

		// 删除前要确保索引存在,否则会报错
		Map map = client.admin().cluster().health(new ClusterHealthRequest(indexname)).actionGet().getIndices();
		boolean exists = map.containsKey(indexname);
		if (!exists)
			return;

		// DeleteResponse response = client.prepareDelete(indexname, type,
		// hospital.getUuid()).get() ;
		client.admin().indices().prepareDelete(indexname).execute().actionGet();

	}

	/**
	 * 执行更新数据
	 * 
	 * @param hospital
	 * @param indexname
	 * @param type
	 */
	public void update(String indexname, String type, String source) {

		// 查询前，没有索引则创建
		checkIndex(indexname);
		String uuid  = null ;
		try {
			JSONObject jsonObject = new JSONObject(source);
			if (jsonObject == null)
				return ;
			uuid = jsonObject.getString("uuid") ;
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		boolean exists = client.admin().indices().prepareTypesExists().setTypes(type).execute().actionGet().isExists();
		if (!exists) {
			if ("hospital".equals(type)) {
				try {
					CreateMapping.createMappingOnHospital(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if ("resource".equals(type)) {
				try {
					CreateMapping.createMappingOnResource(indexname, type);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		UpdateRequest updateRequest = new UpdateRequest(indexname, type, uuid);
		QueryBuilder queryBuilder = QueryBuilders.termQuery("uuid", uuid);
		SearchHit[] searchHits = searcher(queryBuilder, indexname, type);
		if (searchHits.length < 1) {
			createIndexResponse(indexname, type, uuid, source);
			return;
		}
		updateRequest.doc(source) ;
		try {
			client.update(updateRequest).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void checkIndex(String indexname) {
		// 查询前，没有索引则创建
		Map map = client.admin().cluster().health(new ClusterHealthRequest(indexname)).actionGet().getIndices();
		boolean exists = map.containsKey(indexname);
		if (!exists)
			client.admin().indices().prepareCreate(indexname).execute().actionGet();
	}

	public static void main(String[] args) throws IOException {
		ElasticSearchHandler es = new ElasticSearchHandler() ;
		es.checkIndex("zhiliao");
		CreateMapping.createMappingOnResource("zhiliao", "resource");
	}
}
