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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.lankr.dennisit.entity.process.ElasticSearchHandler;
import com.lankr.dennisit.util.JsonUtil;
import com.lankr.interceptor.MyInterceptor;
import com.lankr.producer.Producer;
import com.lankr.vo.Hospital;
import com.lankr.model.HospitalModel;

@Controller
public class ServerController extends BaseController{
	
	private ElasticSearchHandler esHandler = ElasticSearchHandler.instance() ;
	protected static Log logger = LogFactory.getLog(ServerController.class);
	
	@ResponseBody
	@RequestMapping("/api/search")
	public List<HospitalModel> search(HttpServletRequest request,  
            HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*") ;
		request.setCharacterEncoding("UTF-8") ;
		response.setCharacterEncoding("UTF-8") ;
		
		String keyword = request.getParameter("keyword") ;
		
		QueryBuilder queryBuilder = null ;
		List<HospitalModel> hospitalModels = null ;
		logger.info("用户查询关键字：" + keyword);
		
		if (keyword == null || keyword == "") {
			queryBuilder = QueryBuilders.matchAllQuery() ;
			hospitalModels = esHandler.searcher(queryBuilder, "zhiliao", "hospital") ;
		} else { 
			queryBuilder = QueryBuilders.matchQuery( "name", keyword ) ;
			hospitalModels = esHandler.searcher(queryBuilder, "zhiliao", "hospital") ;
		}
		System.out.println("search-------------------"+hospitalModels.size()) ;
		return hospitalModels ;
	}
	
	@ResponseBody
	@RequestMapping("/api/rebuild/hospital")
	public String rebuildHospital(HttpServletRequest request,  
            HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("UTF-8") ;
		response.setCharacterEncoding("UTF-8") ;
		
		List<Hospital> hospitals = this.getAllHospital(0, 50) ;
		esHandler.deleteIndex("zhiliao") ;
		int counts = 0 ;
		while (hospitals.size() != 0) {
			counts += hospitals.size() ;
			int id = hospitals.get(hospitals.size()-1).getId() ;
			System.out.println(id);
			Iterator iterator = hospitals.iterator() ;
			while (iterator.hasNext()) {
				Hospital hospital = (Hospital) iterator.next() ;
				String uuid = hospital.getUuid() ;
				esHandler.createIndexResponse("zhiliao", "hospital", uuid, JsonUtil.obj2JsonData(hospital)) ;
			}
			hospitals = this.getAllHospital(id, 50) ;
		}
		
		logger.info("rebuild 时间 ：" + new SimpleDateFormat("YYYY-MM-dd HH:ss:mm").format(new Date())) ;
		return "hospital rebuild OK count = " + counts ;
	}
	
	
	public static void main(String[] args) throws IOException {
//		while (true) {
//	        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx682e0d50713af910&secret=12b2858d8754520941ba6f95bfd7f958" ;
//		    URL urlTest = new URL(url) ;
//		    HttpURLConnection conn = (HttpURLConnection) urlTest.openConnection() ;
//		    conn.setRequestMethod("GET");
//		    int responseCode = conn.getResponseCode();
//		   // String responseBody = readResponseBody(conn.getInputStream());
//		}
		System.out.println(new SimpleDateFormat("YYYY-MM-dd HH:ss:mm").format(new Date())) ;
	}
	
}
