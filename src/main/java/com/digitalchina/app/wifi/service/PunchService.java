package com.digitalchina.app.wifi.service;

import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.app.wifi.api.IPunchManager;
import com.digitalchina.app.wifi.vo.AttractionsVo;
import com.digitalchina.app.wifi.vo.GeoPointVo;
import com.digitalchina.frame.message.MessageObject;
import com.digitalchina.frame.message.ServiceContext;
import com.digitalchina.frame.security.AuthUser;
import com.digitalchina.web.hybridization.dfh.AbstractCommonService;
import com.digitalchina.web.wattle.action.ResponseResultVo;
import com.digitalchina.web.wattle.util.lang.NumberHandler;
import com.digitalchina.web.wattle.util.lang.ObjectHandler;

@Service
public class PunchService extends AbstractCommonService {
	private final static Logger LOG = Logger.getLogger(PunchService.class);

	@Autowired
	private IPunchManager punchManager;

	/**
	 * @Title: punch
	 * @Description: wifi打卡接口
	 * @author laosy
	 * @date 2016年1月11日
	 * @param serviceContext
	 *            void
	 */
	public void punch(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();
			Double longitude = NumberHandler.toDouble(getValue(mo, "longitude")); // 纬度
			Double latitude = NumberHandler.toDouble(getValue(mo, "latitude")); // 经度
			String address = ObjectHandler.toString(getValue(mo, "address")); // 经度

			if (longitude == null || latitude == null) {
				setFailResult(serviceContext, "经纬度不能为空");
				return;
			}

			GeoPointVo pointVo = new GeoPointVo(address, latitude, longitude);
			
			AuthUser user = getAuthUser(serviceContext);
			Long userId = user.getUserid();
						
			ResponseResultVo resp = punchManager.doPunch(pointVo, userId.toString());
			if (resp.getStatus()) {
				setSuccessResult(serviceContext);
			} else {
				setFailResult(serviceContext, resp.getMsg());
			}
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}

	/**
	 * @Title: saveAttrctions
	 * @Description: 新增一个景点
	 * @author laosy
	 * @date 2016年1月13日
	 * @param serviceContext
	 *            void
	 */
	public void saveAttrctions(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();
			Double longitude = NumberHandler.toDouble(getValue(mo, "longitude")); // 纬度
			Double latitude = NumberHandler.toDouble(getValue(mo, "latitude")); // 经度
			String address = ObjectHandler.toString(getValue(mo, "address")); // 地址
			Double radius = NumberHandler.toDouble(getValue(mo, "radius"), 1000.0);// 范围--单位（米）
			String attractionsName = ObjectHandler.toString(getValue(mo, "attractionsName"));

			if (longitude == null) {
				setFailResult(serviceContext, "景点纬度不能为空");
				return;
			} else if (latitude == null) {
				setFailResult(serviceContext, "景点经度不能为空");
				return;
			} else if (address == null) {
				setFailResult(serviceContext, "景点地址不能为空");
				return;
			} else if (radius == null) {
				setFailResult(serviceContext, "景点范围不能为空");
				return;
			} else if (attractionsName == null) {
				setFailResult(serviceContext, "景点名称不能为空");
				return;
			}

			punchManager.doSaveAttractions(new AttractionsVo(address, latitude, longitude, radius, attractionsName));

			setSuccessResult(serviceContext);
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}

	/**
	 * @Title: updateAttractionsFromXlsx
	 * @Description: 从exle表格读取景点数据保存（增量、exle格式固定）
	 * @author laosy
	 * @date 2016年1月13日
	 * @param serviceContext
	 *            void
	 */
	public void updateAttractionsFromXlsx(ServiceContext serviceContext) {
		try {
			MessageObject mo = serviceContext.getRequestData();
			String key = ObjectHandler.toString(getValue(mo, "key")); // 口令
			String excelName = ObjectHandler.toString(getValue(mo, "excelName"), "attractions-data.xlsx");

			if (key != null && "rehuhu".equals(key)) {
				URL url = getClass().getClassLoader().getResource(excelName);
				if (url != null) {
					punchManager.updateAttractionsFromXlsx(excelName);
					setSuccessResult(serviceContext, "更新成功！");
				} else {
					setFailResult(serviceContext, "找不到对应文件");
				}
			} else {
				setFailResult(serviceContext);
			}
		} catch (Exception e) {
			LOG.error("", e);
			setFailResult(serviceContext);
		}
	}
}
