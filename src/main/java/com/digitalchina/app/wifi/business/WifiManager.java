package com.digitalchina.app.wifi.business;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.unit.DistanceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalchina.app.wifi.api.IWifiManager;
import com.digitalchina.app.wifi.util.ExcelReader;
import com.digitalchina.app.wifi.vo.CategoryVo;
import com.digitalchina.app.wifi.vo.FreeWifiVo;
import com.digitalchina.web.azalea.Session;
import com.digitalchina.web.azalea.elastricsearch.ESQuery;
import com.digitalchina.web.azalea.elastricsearch.ESQueryImpl;
import com.digitalchina.web.azalea.elastricsearch.ESQueryImpl.Equal;
import com.digitalchina.web.azalea.elastricsearch.ESQueryImpl.Like;
import com.digitalchina.web.azalea.elastricsearch.ESSession;
import com.digitalchina.web.wattle.api.ResultPageVo;
import com.digitalchina.web.wattle.util.lang.NumberHandler;
import com.digitalchina.web.wattle.util.lang.ObjectHandler;

@Component
public class WifiManager implements IWifiManager {
	private static final Logger LOG = LogManager.getLogger(WifiManager.class);

	private ESSession session;

	@Autowired
	public void setSession(Session session) {
		this.session = (ESSession) session;
	}
	
	public ESSession getSession() {
		return session;
	}

	@Override
	public void doSaveWifiCategory(CategoryVo vo) {
		session.insert(vo);
	}

	@Override
	public List<Map<String, Object>> findWifiCategories() {
		ESQuery query = ESQueryImpl
							.select()
							.from(CategoryVo.class)
							.done();
		ResultPageVo<Map<String, Object>> resultPage = getSession().selectListAsMap(query);
		if (resultPage != null) {
			return resultPage.getResultList();
		}
		return null;
	}

	@Override
	public void doSaveWifi(FreeWifiVo vo) {
		getSession().insert(vo);
	}

	@Override
	public List<Map<String, Object>> findWifis(String categoryId, String keyword, Double longitude,
			Double latitude) {
		ESQuery query = ESQueryImpl
							.select()
							.from(FreeWifiVo.class)
							.where()
								.begin()
									.merge(Equal.as(categoryId, "category.id")
											.and(Equal.as("location.point",latitude,longitude).radius(1, DistanceUnit.KILOMETERS))
											)
									.merge(Like.as(keyword, "name", "category.categoryName", "location.address"))
								.end()
							.limit(1, 30)
							.done();
		ResultPageVo<Map<String, Object>> resultPage = getSession().selectListAsMap(query);
		if (resultPage != null) {
			return resultPage.getResultList();
		}
		return null;
	}

	@Override
	public void updateWifiDataFromXlsx(String excelName) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(excelName);
		ExcelReader reder = new ExcelReader();
		List<Map<String, String>>  contentsMap = reder.readExcelContent(is);
		Long count = 0l;
		for (Map<String, String> contentMap : contentsMap) {
			CategoryVo categ = new CategoryVo(contentMap.get("pace_type").trim());
			categ = findOneOrAdd(categ);
			
			FreeWifiVo wifi = new FreeWifiVo(
					ObjectHandler.toString(contentMap.get("title")),
					ObjectHandler.toString(contentMap.get("ssid"), "SZ-WLAN(free)"), 
					categ.getId(),
					categ.getCategoryName(),
					contentMap.get("address"), 
					NumberHandler.toDouble(contentMap.get("latitude")),
					NumberHandler.toDouble(contentMap.get("longitude")));
			session.insert(wifi);
			count++;
		}
		LOG.info("新增wifi数据："+count+"条");
	}
	
	/**
	* @Title: findOneOrAdd
	* @Description: 根据传进来的分类名，查询是否有此分类，有则返回，没有则新增再返回
	* @author laosy
	* @date 2015年12月29日
	* @param vo
	* @return CategoryVo   
	*/
	private CategoryVo findOneOrAdd(CategoryVo vo) {
		ESQuery query = ESQueryImpl.select()
				.from(CategoryVo.class).where().begin()
				.merge(Equal.as(vo.getCategoryName(), "categoryName")).end().done();
		List<Object> vos_ = session.selectList(query).getResultList();
		if (!vos_.isEmpty()) {// 有此分类，直接拿出来
			vo = (CategoryVo) vos_.get(0);
		} else {// 没有此分类，新增
			session.insert(vo, true);
		}
		return vo;
	}

}
