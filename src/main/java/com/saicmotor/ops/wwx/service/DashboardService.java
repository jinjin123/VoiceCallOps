package com.saicmotor.ops.wwx.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/12/06.
 */
public interface DashboardService {

	/**
	 * 查询设备信息
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCount() throws Exception;
	
	/**
	 * 查询告警级别日趋势
	 * @return
	 * @throws Exception
	 */
	Map getLineChart() throws Exception;
	
	/**
	 * 服务器区域数量查询
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getServerPie() throws Exception;
	
	/**
	 * 网络设备区域数量查询
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getNetdevicesPie() throws Exception;
	
	/**
	 * 租户服务器数量TOP10查询
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getTenantTopPie() throws Exception;
}

