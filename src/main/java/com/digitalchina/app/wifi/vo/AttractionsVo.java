package com.digitalchina.app.wifi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @ClassName: AttractionsVo
* @Description: 景点vo
* @author laosy
* @date 2016年1月11日
*
*/
@JsonInclude(Include.NON_EMPTY)
public class AttractionsVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private GeoPointVo location;
	private Double radius;
	private String attractionsName;//景点名
	
	public AttractionsVo(GeoPointVo location, Double radius,
			String attractionsName) {
		super();
		this.location = location;
		this.radius = radius;
		this.attractionsName = attractionsName;
	}
	public AttractionsVo(String address, Double lat, Double lon, Double radius,
			String attractionsName) {
		super();
		this.location = new GeoPointVo(address, lat, lon);
		this.radius = radius;
		this.attractionsName = attractionsName;
	}
	
	public AttractionsVo() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	public GeoPointVo getLocation() {
		return location;
	}
	public void setLocation(GeoPointVo location) {
		this.location = location;
	}
	public String getAttractionsName() {
		return attractionsName;
	}
	public void setAttractionsName(String attractionsName) {
		this.attractionsName = attractionsName;
	}

}
