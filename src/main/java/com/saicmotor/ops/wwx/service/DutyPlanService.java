package com.saicmotor.ops.wwx.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/10/30.
 */
public interface DutyPlanService {

	/**
	 * 取当天值班
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getTodayPlan() throws Exception;

	/**
	 * 取日期所在周的计划
	 * @param st
	 * @return
	 * @throws Exception
	 */
	List<Map> getWeekPlan(Date st) throws Exception;

	/**
	 * 取日期所在月排班
	 * @param st
	 * @return
	 * @throws Exception
	 */
	List<Map> getMonthPlan(Date st) throws Exception;


	/**
	 *
	 * @param st
	 * @param ed
	 * @return
	 * @throws Exception
	 */
	List<Map> getRangePlan(Date st, Date ed) throws Exception;

}
