package com.digitalchina.app.wifi.api;

import com.digitalchina.app.wifi.vo.AttractionsVo;
import com.digitalchina.app.wifi.vo.GeoPointVo;
import com.digitalchina.web.wattle.action.ResponseResultVo;

/**
* @ClassName: IPunchManager
* @Description: 景点打卡
* @author laosy
* @date 2016年1月11日
*
*/
public interface IPunchManager {
	/**
	* @Title: punch
	* @Description: 记录wifi打卡
	* @author laosy
	* @date 2016年1月11日
	* @param longitude 纬度
	* @param latitude 经度   
	* @return 是否打卡成功 
	*/
	ResponseResultVo doPunch(GeoPointVo pointVo,String userId);

	/**
	* @Title: updateAttractionsFromXlsx
	* @Description: 从excle表格获取景点数据保存
	* @author laosy
	* @date 2016年1月13日
	* @param excelName void   
	*/
	void updateAttractionsFromXlsx(String excelName);
	
	/**
	* @Title: doSaveAttractions
	* @Description: 保存一条景点数据
	* @author laosy
	* @date 2016年1月13日
	* @param vo void   
	*/
	void doSaveAttractions(AttractionsVo vo);
}
