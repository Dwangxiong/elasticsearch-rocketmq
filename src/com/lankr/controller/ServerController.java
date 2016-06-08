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
			throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String keyword = request.getParameter("keyword");

		QueryBuilder queryBuilder = null;
		List<HospitalVO> hospitalVOs = new ArrayList<HospitalVO>();
		SearchHit[] searchHits = null;
		logger.info("用户查询关键字：" + keyword);

		if (keyword == null || keyword == "") {
			queryBuilder = QueryBuilders.matchAllQuery();
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "hospital");
			if (searchHits.length > 0) {
				HospitalVO hospitalVO = new HospitalVO();
				for (SearchHit hit : searchHits) {
					hospitalVO.set_status((int) hit.getSource().get("_status"));
					String jsonCity = (String) hit.getSource().get("city");
					CityVO cityVO = new CityVO();
					if (jsonCity != null) {
						try {
							JSONObject jOb = new JSONObject(jsonCity);
							cityVO.set_status(jOb.getInt("_status"));
							cityVO.setName(jOb.getString("name"));
							cityVO.setCreateTime(jOb.getLong("createTime"));
							cityVO.setModifyTime(jOb.getLong("modifyTime"));
							cityVO.setUuid(jOb.getString("uuid"));
							String mark = jOb.getString("mark");
							if (mark != null)
								cityVO.setMark(mark);
							else
								cityVO.setMark("");
							String jsonProvince = jOb.getString("province");
							ProvinceVO provinceVO = new ProvinceVO();
							if (jsonProvince != null) {
								JSONObject probj = new JSONObject(jsonProvince);
								provinceVO.set_status(probj.getInt("_status"));
								provinceVO.setName(probj.getString("name"));
								provinceVO.setCreateTime(probj.getLong("createTime"));
								provinceVO.setModifyTime(probj.getLong("modifyTime"));
								provinceVO.setUuid(probj.getString("uuid"));
								String pmark = probj.getString("mark");
								if (pmark != null)
									provinceVO.setMark(pmark);
								else
									provinceVO.setMark("");
							}
							cityVO.setProvince(provinceVO);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					hospitalVO.setCity(cityVO);
					hospitalVO.setAddress((String) hit.getSource().get("address"));
					hospitalVO.setCreateTime((long) hit.getSource().get("createTime"));
					hospitalVO.setModifyTime((long) hit.getSource().get("modifyTime"));
					hospitalVO.setGrade((String) hit.getSource().get("grade"));
					hospitalVO.setUuid((String) hit.getSource().get("uuid"));
					hospitalVO.setName((String) hit.getSource().get("name"));
					String mark = (String) hit.getSource().get("mark");
					if (mark != null)
						hospitalVO.setMark(mark);
					else
						hospitalVO.setMark("");

					hospitalVOs.add(hospitalVO);
				}
			}
		} else {
			queryBuilder = QueryBuilders.matchQuery("name", keyword);
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "hospital");
			if (searchHits.length > 0) {
				HospitalVO hospitalVO = new HospitalVO();
				for (SearchHit hit : searchHits) {
					hospitalVO.set_status((int) hit.getSource().get("_status"));
					String jsonCity = (String) hit.getSource().get("city");
					CityVO cityVO = new CityVO();
					if (jsonCity != null) {
						try {
							JSONObject jOb = new JSONObject(jsonCity);
							cityVO.set_status(jOb.getInt("_status"));
							cityVO.setName(jOb.getString("name"));
							cityVO.setCreateTime(jOb.getLong("createTime"));
							cityVO.setModifyTime(jOb.getLong("modifyTime"));
							cityVO.setUuid(jOb.getString("uuid"));
							String mark = jOb.getString("mark");
							if (mark != null)
								cityVO.setMark(mark);
							else
								cityVO.setMark("");
							String jsonProvince = jOb.getString("province");
							ProvinceVO provinceVO = new ProvinceVO();
							if (jsonProvince != null) {
								JSONObject probj = new JSONObject(jsonProvince);
								provinceVO.set_status(probj.getInt("_status"));
								provinceVO.setName(probj.getString("name"));
								provinceVO.setCreateTime(probj.getLong("createTime"));
								provinceVO.setModifyTime(probj.getLong("modifyTime"));
								provinceVO.setUuid(probj.getString("uuid"));
								String pmark = probj.getString("mark");
								if (pmark != null)
									provinceVO.setMark(pmark);
								else
									provinceVO.setMark("");
							}
							cityVO.setProvince(provinceVO);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					hospitalVO.setCity(cityVO);
					hospitalVO.setAddress((String) hit.getSource().get("address"));
					hospitalVO.setCreateTime((long) hit.getSource().get("createTime"));
					hospitalVO.setModifyTime((long) hit.getSource().get("modifyTime"));
					hospitalVO.setGrade((String) hit.getSource().get("grade"));
					hospitalVO.setUuid((String) hit.getSource().get("uuid"));
					hospitalVO.setName((String) hit.getSource().get("name"));
					String mark = (String) hit.getSource().get("mark");
					if (mark != null)
						hospitalVO.setMark(mark);
					else
						hospitalVO.setMark("");

					hospitalVOs.add(hospitalVO);
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
					resourceVO.setRank((int)hit.getSource().get("rank"));
					resourceVO.setRate((float)hit.getSource().get("rate"));
					resourceVO.setViewCount((int)hit.getSource().get("viewCount"));
					resourceVO.setCover((String)hit.getSource().get("cover"));
					resourceVO.setQr((String)hit.getSource().get("qr"));
					resourceVO.setCreateTime((long)hit.getSource().get("createTime"));
					resourceVO.setModifyTime((long)hit.getSource().get("modifyTime"));
					resourceVO.set_status((int)hit.getSource().get("_status"));
					resourceVO.setUuid((String) hit.getSource().get("uuid"));
					resourceVO.setName((String) hit.getSource().get("name"));
					resourceVO.setCode((String) hit.getSource().get("code"));
					resourceVO.setMark((String) hit.getSource().get("mark"));
					resourceVO.setDescript((String)hit.getSource().get("descipt"));
					resourceVO.setType((String)hit.getSource().get("type"));
					resourceVO.setUpdated_at((long)hit.getSource().get("updated_at"));
					String jsonSpeaker = (String) hit.getSource().get("speaker");
					SpeakerVO speakerVO = new SpeakerVO() ;
					if (jsonSpeaker != null) {
						try {
							JSONObject spJson = new JSONObject(jsonSpeaker) ;
							speakerVO.setAvatar(spJson.getString("avatar"));
							speakerVO.setPosition(spJson.getString("position"));
							speakerVO.setResume(spJson.getString("resume"));
							speakerVO.setUuid(spJson.getString("uuid"));
							speakerVO.setName(spJson.getString("name"));
							speakerVO.setCreateTime(spJson.getLong("createTime"));
							speakerVO.setModifyTime(spJson.getLong("modifyTime"));
							speakerVO.set_status(spJson.getInt("_status"));
							speakerVO.setMark(spJson.getString("mark"));
							String jsonHospital = spJson.getString("hospital") ;
							HospitalVO hospitalVO = new HospitalVO() ;
							if (jsonHospital != null) {
								JSONObject hosJson = new JSONObject(jsonHospital) ;
								hospitalVO.set_status(hosJson.getInt("_status"));
								hospitalVO.setAddress(hosJson.getString("address"));
								hospitalVO.setCreateTime(hosJson.getLong("createTime"));
								hospitalVO.setGrade(hosJson.getString("grade"));
								hospitalVO.setMark(hosJson.getString("mark"));
								hospitalVO.setModifyTime(hosJson.getLong("modifyTime"));
								hospitalVO.setName(hosJson.getString("name"));
								hospitalVO.setUuid(hosJson.getString("uuid"));
								String jsonCity = hosJson.getString("city") ;
								CityVO cityVO = new CityVO() ;
								if (jsonCity != null){
									JSONObject jOb = new JSONObject(jsonCity);
									cityVO.set_status(jOb.getInt("_status"));
									cityVO.setName(jOb.getString("name"));
									cityVO.setCreateTime(jOb.getLong("createTime"));
									cityVO.setModifyTime(jOb.getLong("modifyTime"));
									cityVO.setUuid(jOb.getString("uuid"));
									String mark = jOb.getString("mark");
									if (mark != null)
										cityVO.setMark(mark);
									else
										cityVO.setMark("");
									String jsonProvince = jOb.getString("province");
									ProvinceVO provinceVO = new ProvinceVO();
									if (jsonProvince != null) {
										JSONObject probj = new JSONObject(jsonProvince);
										provinceVO.set_status(probj.getInt("_status"));
										provinceVO.setName(probj.getString("name"));
										provinceVO.setCreateTime(probj.getLong("createTime"));
										provinceVO.setModifyTime(probj.getLong("modifyTime"));
										provinceVO.setUuid(probj.getString("uuid"));
										String pmark = probj.getString("mark");
										if (pmark != null)
											provinceVO.setMark(pmark);
										else
											provinceVO.setMark("");
									}
									cityVO.setProvince(provinceVO);
								}
								hospitalVO.setCity(cityVO);
							}
							speakerVO.setHospital(hospitalVO);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					resourceVO.setSpeaker(speakerVO);
					String jsonCategory = (String) hit.getSource().get("category");
					CategoryVO categoryVO = new CategoryVO() ;
					if (jsonCategory != null) {
						try {
							JSONObject catJson = new JSONObject(jsonCategory);
							categoryVO.set_status(catJson.getInt("_status"));
							categoryVO.setCreateTime(catJson.getLong("createTime"));
							categoryVO.setMark(catJson.getString("mark"));
							categoryVO.setModifyTime(catJson.getLong("modifyTime"));
							categoryVO.setName(catJson.getString("name"));
							categoryVO.setUuid(catJson.getString("uuid"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					resourceVO.setCategory(categoryVO);
					resourceVOs.add(resourceVO);
				}
			}
		} else {
			queryBuilder = QueryBuilders.multiMatchQuery(keyword, "speaker", "category", "name", "mark")
					.operator(Operator.AND);
			searchHits = esHandler.searcher(queryBuilder, "zhiliao", "resource");
			if (searchHits.length > 0) {
				ResourceVO resourceVO = new ResourceVO() ;
				for (SearchHit hit : searchHits) {

					resourceVO.setRank((int)hit.getSource().get("rank"));
					resourceVO.setRate((float)hit.getSource().get("rate"));
					resourceVO.setViewCount((int)hit.getSource().get("viewCount"));
					resourceVO.setCover((String)hit.getSource().get("cover"));
					resourceVO.setQr((String)hit.getSource().get("qr"));
					resourceVO.setCreateTime((long)hit.getSource().get("createTime"));
					resourceVO.setModifyTime((long)hit.getSource().get("modifyTime"));
					resourceVO.set_status((int)hit.getSource().get("_status"));
					resourceVO.setUuid((String) hit.getSource().get("uuid"));
					resourceVO.setCode((String) hit.getSource().get("code"));
					resourceVO.setDescript((String)hit.getSource().get("descipt"));
					resourceVO.setType((String)hit.getSource().get("type"));
					resourceVO.setUpdated_at((long)hit.getSource().get("updated_at"));
					String mark = (java.lang.String) hit.getSource().get("mark") ;
					String name = (String) hit.getSource().get("name");
					String jsonSpeaker = (String) hit.getSource().get("speaker");
					String jsonCategory = (String) hit.getSource().get("category");
					Map<String, HighlightField> result = hit.highlightFields();
					// System.out.println(result);
					if (result != null) {
						HighlightField fieldName = result.get("name");
						if (fieldName != null) {
							name = "";
							Text[] texts = fieldName.fragments();
							for (Text text : texts) {
								name += text.toString();
							}
						}
						HighlightField fieldMark = result.get("mark");
						if (fieldMark != null) {
							mark = "";
							Text[] texts = fieldMark.fragments();
							for (Text text : texts) {
								// System.out.println("------------text----------------"
								// + text);
								mark += text.toString();
							}
						}
						HighlightField fieldSpeaker = result.get("speaker");
						if (fieldSpeaker != null) {
							jsonSpeaker = "";
							Text[] texts = fieldSpeaker.fragments();
							for (Text text : texts) {
								System.out.println("------------text----------------" + text);
								jsonSpeaker += text.toString();
							}
						}
						HighlightField fieldCategory = result.get("category");
						if (fieldCategory != null) {
							jsonCategory = "";
							Text[] texts = fieldCategory.fragments();
							for (Text text : texts) {

								jsonCategory += text.toString();
							}
						}
					}
					resourceVO.setName(name);
					resourceVO.setMark(mark);
					SpeakerVO speakerVO = new SpeakerVO() ;
					if (jsonSpeaker != null && jsonSpeaker != "") {
						try {
							JSONObject spJson = new JSONObject(jsonSpeaker) ;
							speakerVO.setAvatar(spJson.getString("avatar"));
							speakerVO.setPosition(spJson.getString("position"));
							speakerVO.setResume(spJson.getString("resume"));
							speakerVO.setUuid(spJson.getString("uuid"));
							speakerVO.setName(spJson.getString("name"));
							speakerVO.setCreateTime(spJson.getLong("createTime"));
							speakerVO.setModifyTime(spJson.getLong("modifyTime"));
							speakerVO.set_status(spJson.getInt("_status"));
							speakerVO.setMark(spJson.getString("mark"));
							String jsonHospital = spJson.getString("hospital") ;
							HospitalVO hospitalVO = new HospitalVO() ;
							if (jsonHospital != null) {
								JSONObject hosJson = new JSONObject(jsonHospital) ;
								hospitalVO.set_status(hosJson.getInt("_status"));
								hospitalVO.setAddress(hosJson.getString("address"));
								hospitalVO.setCreateTime(hosJson.getLong("createTime"));
								hospitalVO.setGrade(hosJson.getString("grade"));
								hospitalVO.setMark(hosJson.getString("mark"));
								hospitalVO.setModifyTime(hosJson.getLong("modifyTime"));
								hospitalVO.setName(hosJson.getString("name"));
								hospitalVO.setUuid(hosJson.getString("uuid"));
								String jsonCity = hosJson.getString("city") ;
								CityVO cityVO = new CityVO() ;
								if (jsonCity != null){
									JSONObject jOb = new JSONObject(jsonCity);
									cityVO.set_status(jOb.getInt("_status"));
									cityVO.setName(jOb.getString("name"));
									cityVO.setCreateTime(jOb.getLong("createTime"));
									cityVO.setModifyTime(jOb.getLong("modifyTime"));
									cityVO.setUuid(jOb.getString("uuid"));
									cityVO.setMark(jOb.getString("mark"));
									String jsonProvince = jOb.getString("province");
									ProvinceVO provinceVO = new ProvinceVO();
									if (jsonProvince != null) {
										JSONObject probj = new JSONObject(jsonProvince);
										provinceVO.set_status(probj.getInt("_status"));
										provinceVO.setName(probj.getString("name"));
										provinceVO.setCreateTime(probj.getLong("createTime"));
										provinceVO.setModifyTime(probj.getLong("modifyTime"));
										provinceVO.setUuid(probj.getString("uuid"));
										provinceVO.setMark(probj.getString("mark"));
									}
									cityVO.setProvince(provinceVO);
								}
								hospitalVO.setCity(cityVO);
							}
							speakerVO.setHospital(hospitalVO);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					resourceVO.setSpeaker(speakerVO);
					CategoryVO categoryVO = new CategoryVO() ;
					if (jsonCategory != null && jsonCategory != "") {
						try {
							JSONObject catJson = new JSONObject(jsonCategory);
							categoryVO.set_status(catJson.getInt("_status"));
							categoryVO.setCreateTime(catJson.getLong("createTime"));
							categoryVO.setMark(catJson.getString("mark"));
							categoryVO.setModifyTime(catJson.getLong("modifyTime"));
							categoryVO.setName(catJson.getString("name"));
							categoryVO.setUuid(catJson.getString("uuid"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					resourceVO.setCategory(categoryVO);
					resourceVOs.add(resourceVO);
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
			throws UnsupportedEncodingException {

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
