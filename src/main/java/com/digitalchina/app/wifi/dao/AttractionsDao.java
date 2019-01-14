package com.digitalchina.app.wifi.dao;

import java.util.List;

import com.digitalchina.app.wifi.vo.AttractionsVo;
import com.digitalchina.web.wattle.dao.IDao;

/**
 * 类型描述：<br/>
 * 景点
 * 
 * @createTime 2017年4月10日
 * @author maiwj
 *
 */
public interface AttractionsDao extends IDao {

	void insertVo(AttractionsVo vo);

	List<AttractionsVo> selectVos();

}
