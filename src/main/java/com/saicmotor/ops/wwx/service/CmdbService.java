package com.saicmotor.ops.wwx.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Shen_JM on 2017/11/29.
 */
public interface CmdbService {

	/**
	 * 查询服务器列表
	 * @param page
	 * @param length
	 * @param principal
	 * @param idc
	 * @param module_id
	 * @param logic_area
	 * @param state
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCmdbServerList(String page, String length, String principal, String idc, String module_id, String logic_area, String state) throws Exception;
	
	/**
	 * 获取业务关联选择项API
	 * @param module_id
	 * @return
	 * @throws Exception
	 */
	List<Map> getCmdbModule(String module_id) throws Exception;

	/**
	 * 获取负责人选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getCmdbUser() throws Exception;
	
	/**
	 * 获取服务器IDC选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getCmdbIdc() throws Exception;
	
	/**
	 * 获取服务器逻辑区选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getCmdbLogic() throws Exception;
	
	/**
	 * 获取运营状态选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getCmdbState() throws Exception;
	
	/**
	 * 获取网络设备用途选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getNetdevicesFunc() throws Exception;
	
	/**
	 * 获取网络设备idc选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getNetdevicesIdc() throws Exception;
	
	/**
	 * 获取网络设备型号选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getNetdevicesModel() throws Exception;
	
	/**
	 * 获取网络设备厂家选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getNetdevicesPro() throws Exception;
	
	/**
	 * 获取网络设备类型选择项API
	 * @return
	 * @throws Exception
	 */
	List<Map> getNetdevicesType() throws Exception;
	
	/**
	 * 网络设备列表API
	 * @param page
	 * @param length
	 * @param netdev_idc
	 * @param netdev_type
	 * @param netdev_func
	 * @param netdev_pro
	 * @param netdev_model
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCmdbNetdevicesList(String page, String length, String netdev_idc, String netdev_type, String netdev_func, String netdev_pro, String netdev_model) throws Exception;
}

