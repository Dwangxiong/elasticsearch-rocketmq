//package com.lankr.rebuild;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.lankr.controller.BaseController;
//import com.lankr.dennisit.entity.process.ElasticSearchHandler;
//import com.lankr.dennisit.util.JsonUtil;
//import com.lankr.vo.Hospital;
//
//public class Rebuild extends BaseController implements Runnable{
//	
//	private ElasticSearchHandler esHandler = ElasticSearchHandler.instance() ;
//	
//	@Override
//	public void run() {
////		while (true) {
////			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
////			String str = null ;
////			try {
////				str = br.readLine() ;
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////			if ("rebuild hospital".equals(str)) {
////				WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
////				BaseController baseController = wac.getBean("baseController", BaseController.class) ;
////				List<Hospital> hospitals = baseController.getAllHospital() ;
////				esHandler.deleteIndex("zhiliao") ;
////				for (int i = 0; i<hospitals.size(); i++) {
////					Hospital hospital = hospitals.get(i) ;
////					String uuid = hospital.getUuid() ;
////					
////					esHandler.createIndexResponse("zhiliao", "hospital", uuid, JsonUtil.obj2JsonData(hospital)) ;
////				}
////			}
////			System.out.println(str) ;
////			String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx682e0d50713af910&secret=12b2858d8754520941ba6f95bfd7f958" ;
////		    URL urlTest;
////			try {
////				urlTest = new URL(url);
////			
////		    HttpURLConnection conn;
////			
////				conn = (HttpURLConnection) urlTest.openConnection();
////		
////				conn.setRequestMethod("GET");
////				conn.setConnectTimeout(999999999);
////		 
////				int responseCode = conn.getResponseCode();
////			}catch(Exception e) {
////				e.printStackTrace();
////			}
////		    String responseBody = readResponseBody(conn.getInputStream());
////		}
//	}
//
//}
