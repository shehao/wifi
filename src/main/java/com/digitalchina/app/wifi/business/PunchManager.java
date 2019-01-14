package com.digitalchina.app.wifi.business;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalchina.app.wifi.api.IPunchManager;
import com.digitalchina.app.wifi.dao.AttractionsDao;
import com.digitalchina.app.wifi.dao.PunchRecallDao;
import com.digitalchina.app.wifi.util.Distance;
import com.digitalchina.app.wifi.util.ExcelReader;
import com.digitalchina.app.wifi.vo.AttractionsVo;
import com.digitalchina.app.wifi.vo.GeoPointVo;
import com.digitalchina.app.wifi.vo.PunchRecallVo;
import com.digitalchina.web.wattle.action.ResponseResultVo;
import com.digitalchina.web.wattle.util.json.JsonHandler;
import com.digitalchina.web.wattle.util.lang.NumberHandler;

@Component
public class PunchManager implements IPunchManager{
	private static final Logger LOG = LogManager.getLogger(PunchManager.class);
	
	@Autowired
	private AttractionsDao attractionsDao;
	@Autowired
	private PunchRecallDao punchRecallDao;

	@Override
	public ResponseResultVo doPunch(GeoPointVo pointVo,String userId) {
		AttractionsVo flag = null;
		List<AttractionsVo> attractionsVos = attractionsDao.selectVos();
		for(AttractionsVo attractionsVo :attractionsVos){
			flag = CompPoint(attractionsVo,pointVo);
		}
		if(flag==null){
			LOG.info("用户打卡失败!userId="+userId);
			return ResponseResultVo.failure().setData("打卡失败，请确定是否在景点内！");
		}else{//打卡成功
			PunchRecallVo recallVo = new PunchRecallVo(flag.getId(), userId, pointVo);//打卡记录
			punchRecallDao.insertVo(recallVo);
			LOG.info("用户打卡成功!recallVo="+JsonHandler.toJson(recallVo));
			return ResponseResultVo.success();
		}
	}
	
	/**
	* @Title: CompPoint
	* @Description: 比对指定地址是否在指定景点内
	* @author laosy
	* @date 2016年1月11日
	* @param attractionsVo
	* @param target
	* @return boolean   
	*/
	private AttractionsVo CompPoint(AttractionsVo attractionsVo,GeoPointVo target){
		Double radius = attractionsVo.getRadius();//范围
		GeoPointVo ponit1 = attractionsVo.getLocation();
		GeoPointVo ponit2 = target;
		//两点距离
		Double distance = Distance.getDistance(ponit1.getLon(), ponit1.getLon(),ponit2.getLon(), ponit2.getLon());
		if(distance<radius){
			return attractionsVo;
		}
		return null;
	}

	@Override
	public void updateAttractionsFromXlsx(String excelName) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(excelName);
		ExcelReader reder = new ExcelReader();
		List<Map<String, String>>  contentsMap = reder.readExcelContent(is);
		Long count = 0l;
		for (Map<String, String> contentMap : contentsMap) {
			AttractionsVo attractionsVo = new AttractionsVo(
					contentMap.get("address").trim(),
					NumberHandler.toDouble(contentMap.get("latitude").trim()),
					NumberHandler.toDouble(contentMap.get("longitude").trim()),
					NumberHandler.toDouble(contentMap.get("radius").trim()),
					contentMap.get("name").trim()
					);
			this.doSaveAttractions(attractionsVo);
			count++;
		}
		LOG.info("excel新增景点数据"+count+"条");
		
	}

	@Override
	public void doSaveAttractions(AttractionsVo vo) {
		attractionsDao.insertVo(vo);
		LOG.info("新增一个打卡景点--"+JsonHandler.Jackson.toJson(vo));
	}
}
