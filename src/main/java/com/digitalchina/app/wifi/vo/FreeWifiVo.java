package com.digitalchina.app.wifi.vo;

import java.io.Serializable;

import com.digitalchina.web.azalea.elastricsearch.vo.ESGeoPointVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 类型描述：<br/>
 * 免费Wifi对象
 * 
 * @createTime 2015年12月23日
 * @author maiwj
 * 
 */
@JsonInclude(Include.NON_EMPTY)
public class FreeWifiVo implements Serializable {

	private static final long serialVersionUID = -8857613233751970789L;

	private String id; // id
	private String name; // 名称
	private String ssid; // ssid
	private CategoryVo category; // 分类信息
	private ESGeoPointVo location; // 地理信息

	public FreeWifiVo() {

	}

	public FreeWifiVo(String name, String ssid, String categoryId, String categoryName, String address, Double latitude,
			Double longitude) {
		this.name = name;
		this.ssid = ssid;
		this.category = new CategoryVo(categoryId, categoryName);
		this.location = new ESGeoPointVo(address, latitude, longitude);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public ESGeoPointVo getLocation() {
		return location;
	}

	public void setLocation(ESGeoPointVo location) {
		this.location = location;
	}

	public CategoryVo getCategory() {
		return category;
	}

	public void setCategory(CategoryVo category) {
		this.category = category;
	}

}
