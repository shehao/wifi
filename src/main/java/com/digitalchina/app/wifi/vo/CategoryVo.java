package com.digitalchina.app.wifi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 类型描述：<br/>
 * 类别对象
 * 
 * @createTime 2015年12月23日
 * @author maiwj
 * 
 */
@JsonInclude(Include.NON_EMPTY)
public class CategoryVo implements Serializable {

	private static final long serialVersionUID = -3784244423070459754L;

	private String id; // id
	private String categoryName; // 名称

	public CategoryVo() {

	}
	
	public CategoryVo(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryVo(String id, String categoryName) {
		this.id = id;
		this.categoryName = categoryName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


}
