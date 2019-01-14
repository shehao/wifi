package com.digitalchina.app.wifi.api;

import java.util.List;
import java.util.Map;

import com.digitalchina.app.wifi.vo.CategoryVo;
import com.digitalchina.app.wifi.vo.FreeWifiVo;

/**
 * 类型描述：<br/>
 * wifi管理
 * 
 * @createTime 2015年12月24日
 * @author maiwj
 * 
 */
public interface IWifiManager {

	/**
	 * 方法描述：<br/>
	 * 新增wifi分类
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param category
	 */
	void doSaveWifiCategory(CategoryVo category);

	/**
	 * 方法描述：<br/>
	 * 查找wifi分类列表
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @return
	 */
	List<Map<String, Object>> findWifiCategories();

	/**
	 * 方法描述：<br/>
	 * 新增一个wifi
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param vo
	 */
	void doSaveWifi(FreeWifiVo vo);

	/**
	 * 方法描述：<br/>
	 * 查找wifi列表
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param categoryId
	 *            分类Id
	 * @param keyword
	 *            关键字
	 * @param longitude
	 *            纬度
	 * @param latitude
	 *            经度
	 * @return
	 */
	List<Map<String, Object>> findWifis(String categoryId, String keyword, Double longitude, Double latitude);
	
	/**
	* @Title: updateWifiDataFromXlsx
	* @Description: 从exle表格读取wifi数据保存（增量、exle格式固定）
	* @author laosy
	* @date 2016年1月11日
	* @param excelName wifi数据文件名   
	*/
	void updateWifiDataFromXlsx(String excelName);
	
}
