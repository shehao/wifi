package com.digitalchina.app.wifi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.app.wifi.vo.PunchRecallVo;
import com.digitalchina.web.wattle.dao.IDao;

/**
 * 类型描述：<br/>
 * 打卡点
 * 
 * @createTime 2017年4月10日
 * @author maiwj
 *
 */
public interface PunchRecallDao extends IDao {

	void insertVo(PunchRecallVo vo);

	List<PunchRecallVo> selectVosByAttractionsId(@Param("attractionsId") Long attractionsId);

	List<PunchRecallVo> selectVosByUserId(@Param("userId") Long userId);
}
