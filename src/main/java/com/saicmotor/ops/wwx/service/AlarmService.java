package com.saicmotor.ops.wwx.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/11/20.
 */
public interface AlarmService {

	/**
	 *
	 * @param start
	 * @param length
	 * @param level
	 * @param origin
	 * @param status
	 * @param module_id_one
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmList(String start, String length, String origin, String status, String module_id_one, String level) throws Exception;
	
	/**
	 * 查询告警详情
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAlarmDetail(int id) throws Exception;

}
