package com.digitalchina.app.wifi.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.app.wifi.api.IWifiManager;
import com.digitalchina.app.wifi.vo.CategoryVo;
import com.digitalchina.app.wifi.vo.FreeWifiVo;
import com.digitalchina.frame.message.MessageObject;
import com.digitalchina.frame.message.ServiceContext;
import com.digitalchina.web.hybridization.dfh.AbstractCommonService;
import com.digitalchina.web.wattle.util.lang.NumberHandler;
import com.digitalchina.web.wattle.util.lang.ObjectHandler;

/**
 * 类型描述：<br/>
 * wifi服务
 * 	
 * @createTime 2015年12月23日
 * @author maiwj
 * 
 */
@Service
public class WifiService extends AbstractCommonService {

	private final static Logger LOG = Logger.getLogger(WifiService.class);

	@Autowired
	private IWifiManager wifiManager;
	
	/**
	 * 方法描述：<br/>
	 * 新增wifi分类
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param serviceContext
	 */
	public void saveWifiCategory(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();
			
			String name = ObjectHandler.toString(getValue(mo, "name")); // 分类名称
			
			if (StringUtils.isBlank(name)) {
				setFailResult(serviceContext, "wifi分类名称不能为空");
				return;
			}
			
			wifiManager.doSaveWifiCategory(new CategoryVo(name));

			setSuccessResult(serviceContext);
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}

	/**
	 * 方法描述：<br/>
	 * 查询wifi分类
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param serviceContext
	 */
	public void findWifiCategories(ServiceContext serviceContext) {
		try {
			List<Map<String, Object>> result = wifiManager.findWifiCategories();

			setSuccessResult(serviceContext, CollectionUtils.isEmpty(result) ? new Object[0] : result);
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}

	/**
	 * 方法描述：<br/>
	 * 新增一个wifi
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param serviceContext
	 */
	public void saveWifi(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();

			String name = ObjectHandler.toString(getValue(mo, "name")); // wifi名称
			String ssid = ObjectHandler.toString(getValue(mo, "ssid"), "SZ-WLAN(free)"); // ssid
			String address = ObjectHandler.toString(getValue(mo, "address")); // 路径
			Double longitude = NumberHandler.toDouble(getValue(mo, "longitude")); // 纬度
			Double latitude = NumberHandler.toDouble(getValue(mo, "latitude")); // 经度
			String categoryId = ObjectHandler.toString(getValue(mo, "categoryId")); // 分类Id
			String categoryName = ObjectHandler.toString(getValue(mo, "categoryName")); // 分类名称

			if (name == null) {
				setFailResult(serviceContext, "wifi名称不能为空");
				return;
			} else if (address == null) {
				setFailResult(serviceContext, "wifi地址不能为空");
				return;
			} else if (categoryId == null) {
				setFailResult(serviceContext, "wifi分类Id不能为空");
				return;
			} else if (categoryName == null) {
				setFailResult(serviceContext, "wifi分类名称不能为空");
				return;
			} else if (longitude == null) {
				setFailResult(serviceContext, "wifi纬度不能为空");
				return;
			} else if (latitude == null) {
				setFailResult(serviceContext, "wifi经度不能为空");
				return;
			}

			wifiManager.doSaveWifi(new FreeWifiVo(name, ssid, categoryId, categoryName, address, latitude, longitude));

			setSuccessResult(serviceContext);
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}

	/**
	 * 方法描述：<br/>
	 * 查询wifi<br/>
	 * 查询条件：<br/>
	 * longitude和latitude表示的是地理信息位置（基于百度地图）<br/>
	 * keyword表示查询关键字，比对wifi分类名称、wifi名称和wifi的所在地址<br/>
	 * categoryId表示查询分类Id<br/>
	 * <br/>
	 * 条件组合：<br/>
	 * 1、longitude和latitude必须成对组成地理信息位置，当只有地理信息的时候，查询该地理信息周边半径1km圆形区域的wifi点；<br/>
	 * 2、当只有关键字信息的时候，查询符合该关键字的wifi点；<br/>
	 * 3、当只有分类Id的时候，查询符合该分类Id的wifi点；<br/>
	 * 4、以上三个条件可以自由组合，形成与条件查询；<br/>
	 * 5、当什么都没有的时候，列出所有的wifi查询点
	 * 
	 * @createTime 2015年12月24日
	 * @author maiwj
	 *
	 * @param serviceContext
	 */
	public void findWifis(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();

			String keyword = ObjectHandler.toString(getValue(mo, "keyword")); // 关键词
			String categoryId = ObjectHandler.toString(getValue(mo, "categoryId")); // 分类Id
			Double longitude = NumberHandler.toDouble(getValue(mo, "longitude")); // 纬度
			Double latitude = NumberHandler.toDouble(getValue(mo, "latitude")); // 经度

			List<Map<String, Object>> result = wifiManager.findWifis(categoryId, keyword, longitude, latitude);

			setSuccessResult(serviceContext, CollectionUtils.isEmpty(result) ? new Object[0] : result);
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}
	
	/**
	* @Title: updateWifiDataFromXlsx
	* @Description: 从exle表格读取wifi数据保存（增量、exle格式固定）
	* @author laosy
	* @date 2015年12月29日
	* @param serviceContext void   
	*/
	public void updateWifiDataFromXlsx(ServiceContext serviceContext){
		try {
			MessageObject mo = serviceContext.getRequestData();
			String key = ObjectHandler.toString(getValue(mo, "key")); // 口令
			String excelName = ObjectHandler.toString(getValue(mo, "excelName"),"wifi-data.xlsx");
			
			if(key!=null && "rehuhu".equals(key)){
				URL url = getClass().getClassLoader().getResource(excelName);
				if(url!=null){
					wifiManager.updateWifiDataFromXlsx(excelName);
					setSuccessResult(serviceContext, "更新成功！");
				}else{
					setFailResult(serviceContext,"找不到对应文件");
				}
			}else{
				setFailResult(serviceContext);
			}
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}
	
}
