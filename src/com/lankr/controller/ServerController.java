package com.lankr.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MatchQueryBuilder.ZeroTermsQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.google.gson.Gson;
import com.lankr.dennisit.entity.process.ElasticSearchHandler;
import com.lankr.dennisit.util.JsonUtil;
import com.lankr.interceptor.MyInterceptor;
import com.lankr.model.Hospital;
import com.lankr.model.Resource;
import com.lankr.producer.Producer;
import com.lankr.vo.CategoryVO;
import com.lankr.vo.CityVO;
import com.lankr.vo.HospitalVO;
import com.lankr.vo.ProvinceVO;
import com.lankr.vo.ResourceVO;
import com.lankr.vo.SpeakerVO;

@Controller
public class ServerController extends BaseController {

	private ElasticSearchHandler esHandler = ElasticSearchHandler.instance();
	protected static Log logger = LogFactory.getLog(ServerController.class);

	@ResponseBody
	@RequestMapping("/api/search/hospital")
	public List<HospitalVO> search(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			{
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		String keyword = request.getParameter("keyword");
		Gson gson = new Gson() ;
		QueryBuilder queryBuilder = null;
		List<HospitalVO> hospitalVOs = new ArrayList<HospitalVO>();
		SearchHit[] searchHits = null;
		logger.info("用户查询关键字：" + keyword);

		if (keyword == null || keyword == "") {
			queryBuilder = QueryBuilders.matchAllQuery();
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "hospital");
			if (searchHits.length > 0) {
				for (SearchHit hit : searchHits) {
					String data = hit.getSourceAsString() ;
					HospitalVO hv = gson.fromJson(data, HospitalVO.class) ;
					hospitalVOs.add(hv) ;
				}
			}
		} else {
			queryBuilder = QueryBuilders.matchQuery("name", keyword);
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "hospital");
			if (searchHits.length > 0) {
				HospitalVO hospitalVO = new HospitalVO();
				for (SearchHit hit : searchHits) {
					String data = hit.getSourceAsString() ;
					HospitalVO hv = gson.fromJson(data, HospitalVO.class) ;
					Map<String, HighlightField> result = hit.highlightFields();
					// System.out.println(result);
					if (result != null) {
						HighlightField fieldName = result.get("name");
						if (fieldName != null) {
							Text[] texts = fieldName.fragments();
							for (Text text : texts) {
								System.out.println(text);
								hv.setName(text.toString());
							}
						}
					}
					hospitalVOs.add(hv) ;
				}
			}
		}
		System.out.println("search-------------------" + hospitalVOs.size());
		return hospitalVOs;
	}

	@ResponseBody
	@RequestMapping("/api/search/resource")
	public List<ResourceVO> searchResource(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Object String) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		String keyword = request.getParameter("keyword");

		Gson gson = new Gson() ;
		QueryBuilder queryBuilder = null;
		List<ResourceVO> resourceVOs = new ArrayList<ResourceVO>();
		SearchHit[] searchHits = null;
		logger.info("用户查询关键字：" + keyword);
		if (keyword == null || keyword == "") {
			queryBuilder = QueryBuilders.matchAllQuery();
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "resource");
			if (searchHits.length > 0) {
				ResourceVO resourceVO = new ResourceVO() ;
				for (SearchHit hit : searchHits) {
					String data = hit.getSourceAsString() ;
					ResourceVO rv = gson.fromJson(data, ResourceVO.class) ;
					resourceVOs.add(rv) ;
				}
			}
		} else {
			queryBuilder = QueryBuilders.multiMatchQuery(keyword, "speaker.name", "category.name", "name", "mark")
					.operator(Operator.AND);
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "resource");
			if (searchHits.length > 0) {
				ResourceVO resourceVO = new ResourceVO() ;
				for (SearchHit hit : searchHits) {
					String data = hit.getSourceAsString() ;
					
					ResourceVO rv = gson.fromJson(data, ResourceVO.class) ;
					Map<String, HighlightField> result = hit.highlightFields();
					// System.out.println(result);
					if (result != null) {
						HighlightField fieldName = result.get("name");
						if (fieldName != null) {
							Text[] texts = fieldName.fragments();
							for (Text text : texts) {
								rv.setName(text.toString());
							}
						}
						HighlightField fieldMark = result.get("mark");
						if (fieldMark != null) {
							Text[] texts = fieldMark.fragments();
							for (Text text : texts) {
								rv.setMark(text.toString());
							}
						}
						HighlightField fieldSpeaker = result.get("speaker.name");
						if (fieldSpeaker != null) {
							Text[] texts = fieldSpeaker.fragments();
							for (Text text : texts) {
								System.out.println(text.toString());
								rv.getSpeaker().setName(text.toString());
							}
						}
						HighlightField fieldCategory = result.get("category.name");
						if (fieldCategory != null) {
							Text[] texts = fieldCategory.fragments();
							for (Text text : texts) {
								rv.getCategory().setName(text.toString());
							}
						}
					}
					
					resourceVOs.add(rv) ;
				}
			}
		}
		System.out.println("search-------------------" + resourceVOs.size());
		return resourceVOs;
	}

	@ResponseBody
	@RequestMapping("/api/rebuild/hospital")
	public String rebuildHospital(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		List<Hospital> hospitals = this.selectAllHospital(0, 50);
		int counts = 0;
		while (hospitals.size() != 0) {
			counts += hospitals.size();
			int id = hospitals.get(hospitals.size() - 1).getId();
			System.out.println(id);
			Iterator iterator = hospitals.iterator();
			while (iterator.hasNext()) {
				Hospital hospital = (Hospital) iterator.next();
				String uuid = hospital.getUuid();
				esHandler.createIndexResponse("zhiliao", "hospital", uuid, JsonUtil.obj2JsonData(hospital));
			}
			hospitals = this.selectAllHospital(id, 50);
		}

		logger.info("hospital rebuild 时间 ：" + new SimpleDateFormat("YYYY-MM-dd HH:ss:mm").format(new Date()));
		return "hospital rebuild OK count = " + counts;
	}

	@ResponseBody
	@RequestMapping("/api/rebuild/resource")
	public String rebuildResource(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		List<Resource> resources = resourceMgrFacade.selectAllResource(0, 50);
		int counts = 0;
		while (resources.size() != 0) {
			counts += resources.size();
			int id = resources.get(resources.size() - 1).getId();
			System.out.println(id);
			Iterator iterator = resources.iterator();
			while (iterator.hasNext()) {
				Resource resource = (Resource) iterator.next();
				String uuid = resource.getUuid() ;
				esHandler.createIndexResponse("zhiliao", "resource", uuid, JsonUtil.obj2JsonData(resource));
			}
			resources = resourceMgrFacade.selectAllResource(id, 50);
		}

		logger.info("resource rebuild 时间 ：" + new SimpleDateFormat("YYYY-MM-dd HH:ss:mm").format(new Date()));
		return "resource rebuild OK count = " + counts;
	}

	@ResponseBody
	@RequestMapping("/api/rebuild/all")
	public String rebuildAll(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			{

		esHandler.deleteIndex("zhiliao");
		logger.info("all rebuild 时间 ：" + new SimpleDateFormat("YYYY-MM-dd HH:ss:mm").format(new Date()));
		return "all is deleted ";
	}

	public static void main(String[] args) throws IOException {
		String str = "{\"speaker\":\"{\"name\":\"zhangsan\",\"age\":123}}\"" ;
		try {
			JSONObject ob = new JSONObject(str);
			String oo = ob.getString("speaker") ;
			System.out.println("--------------" + oo + "-------------------");
			JSONObject jj = new JSONObject(oo) ;
			String name = jj.getString("name") ;
			System.out.println("--------------" + name + "-------------------");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
