package com.digitalchina.app.wifi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @ClassName: GeoPointVo
* @Description: 地理信息点
* @author laosy
* @date 2016年1月11日
*
*/
@JsonInclude(Include.NON_EMPTY)
public class GeoPointVo implements Serializable {

	private static final long serialVersionUID = 6893859661636235136L;

	private String address;
	private Double lat;
	private Double lon;

	public GeoPointVo() {
	}

	public GeoPointVo(String address, Double lat, Double lon) {
		this.address = address;
		this.lat = lat;
		this.lon = lon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}


}
