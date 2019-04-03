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
	
	/**
	 * 重点租户信息查询
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getKeyTenantInfo() throws Exception;
	
	/**
	 * IDC network line
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getIdcLineInfo() throws Exception;
	/**
	 * Dt network line
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtLineInfo() throws Exception;
	
	/**
	 * Dt cpu ten data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtcputenPie() throws Exception;
	
	/**
	 * Dt cpu elv data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtcpuelvPie() throws Exception;
	
	/**
	 * Dt mem ten data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtmemtenPie() throws Exception;
	
	/**
	 * Dt mem elv data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtmemelvPie() throws Exception;
	
	/**
	 * Dt ngx sess data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtngxsesPie() throws Exception;
	
	/**
	 * Dt ngx act data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getDtngxact() throws Exception;
	/**
	 * Bm lvs data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmlvscon() throws Exception;
	/**
	 * Bm tg one data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmtgone() throws Exception;
	/**
	 * Bm tg two data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmtgtwo() throws Exception;
	/**
	 * Bm tg three data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmtgthree() throws Exception;
	/**
	 * Bm tg four data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmtgfour() throws Exception;
	/**
	 * Bm ngx session Line data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmngxLine() throws Exception;
	/**
	 * Bm ngx session Pie data
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBmngxPie() throws Exception;
	
	Map<String,Object> getpyvm() throws Exception;
	
	Map<String,Object> getpyvmdata(String hostname) throws Exception;
}

