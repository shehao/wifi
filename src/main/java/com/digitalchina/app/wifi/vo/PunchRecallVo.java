package com.digitalchina.app.wifi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @ClassName: PunchRecall
* @Description: 打卡记录vo
* @author laosy
* @date 2016年1月11日
*
*/
@JsonInclude(Include.NON_EMPTY)
public class PunchRecallVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long attractionsId;//景点id
	private String userId;//用户id
	private GeoPointVo location;//地点
	
	public PunchRecallVo() {
		super();
	}
	
	public PunchRecallVo(Long attractionsId, String userId, GeoPointVo location) {
		super();
		this.attractionsId = attractionsId;
		this.userId = userId;
		this.location = location;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getAttractionsId() {
		return attractionsId;
	}
	public void setAttractionsId(Long attractionsId) {
		this.attractionsId = attractionsId;
	}
	public GeoPointVo getLocation() {
		return location;
	}
	public void setLocation(GeoPointVo location) {
		this.location = location;
	}
}
