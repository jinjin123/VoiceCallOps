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
	 * @param inner_ip
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAlarmList(String start, String length, String origin, String status, String module_id_one, String level) throws Exception;

	/**
	 * select alarm new one
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAlarmListNew(String length) throws Exception;

	/**
	 * 查询告警详情
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getAlarmDetail(int id) throws Exception;
	
	/**
	 * 获取告警级别
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmLevel() throws Exception;

	/**
	 * 获取告警来源
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmOrigin() throws Exception;
	
	/**
	 * 获取告警指标
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmIndicator() throws Exception;
	
	/**
	 * 获取告警状态
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmStatus() throws Exception;
	
	/**
	 * 获取一级业务信息
	 * @return
	 * @throws Exception
	 */
	List<Map> getAlarmModule() throws Exception;
}

